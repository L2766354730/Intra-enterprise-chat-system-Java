package com.nuc.ljx.page;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.alibaba.fastjson.JSONObject;
import com.nuc.ljx.util.PageCacheUtil;
import com.nuc.ljx.util.SendMessageUtil;
/**
 * ����˽�Ľ���
 * @author 11018
 *
 */
public class SingelChatPage extends JFrame{
	public JScrollPane jsp; 
	public JTextArea jta; //��ʷ�����¼������ı���
	public void init(String currentUserId,String friendId,String friendNickname,String currentNickname) {
		/**
		 * 1�������˷���һ����Ϣ
		 * 6=userId=friendId ��ѯ�û��ͺ���֮�����ʷ�����¼
		 */
		String msg = "6="+currentUserId+"="+friendId;
		try {
			SendMessageUtil.sendMessage(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//1������ҳ��Ĳ��� GridLayout  null--���Բ���
		this.setLayout(null);
		/**
		 * ��һ������
		 */
		jsp = new JScrollPane();
		jsp.setBounds(0, 0, 500, 550);
		this.add(jsp);
		/**
		 * �ڶ�������  ���ı��򡢷����ļ���ť  ������Ϣ��ť���������
		 */
		JPanel jp = new JPanel();
		jp.setLayout(null);
		//1���ı���
		JTextArea jta1 = new JTextArea();
		jta1.setBounds(0, 0, 500, 150);
		
		jp.add(jta1);
		
		//2�������ļ���ť
		JButton jb = new JButton("�����ļ�");
		jb.setBounds(300, 150, 100, 30);
		//˫��jl7����һ���ļ�ѡ���� ѡ���ļ�
		jb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/**
				 * ����һ���ļ�ѡ����
				 */
				JFileChooser jfc = new JFileChooser();
				//����һ���ļ�ѡ��������ѡ��ʲô����
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = jfc.showOpenDialog(null);
				//����û����ļ�ѡ����ѡ���˶��� result!=1  
				if (result != 1) {
					//file���������ļ�ѡ������ѡ����ļ� 
					File file = jfc.getSelectedFile();
					String fileName = file.getName();
					String[] array = fileName.split("\\.");
					String suffixName = array[array.length-1];
					//�ϴ������ص��ļ�·��  chatfiles/12312312313.png
					String name = array[array.length-2]+"."+suffixName;
					if (JOptionPane.showConfirmDialog(null, "ȷ�����͸��ļ���\n"+name)==0) {
						String path = "chatfiles/"+name;
						//IO���Ĵ���  �ֽ���  �ļ�����
						try {
							FileInputStream fis = new FileInputStream(file);
							FileOutputStream fos = new FileOutputStream(new File(path));
							int read =0;
							while((read = fis.read()) != -1) {
								fos.write(read);
							}
							//2����������Ϣ�����ȷŵ���ʷ�����¼����  ��ǰ���û��ǳ�+message
							//���õ���ǰ����ʷ�����¼�ı�
							String historyMessage = PageCacheUtil.singelChatPage.jta.getText();
							//����һ���µ��ı��� ����ʷ�����¼�͵�ǰ���͵����������Ϣȫ��ƴ�ӵ��´�����jta����
							JTextArea jta = new JTextArea();
							jta.setFont(new Font("΢���ź�", Font.BOLD, 16));
							jta.setEnabled(false);//�����ı���
							jta.setLineWrap(true);//�����ı����Զ�����
							jta.append(historyMessage);
							jta.append(currentNickname+":");
							jta.append("\r\n");
							jta.append("������һ���ļ���·��Ϊ��"+path);
							jta.append("\r\n");
							PageCacheUtil.singelChatPage.jta = jta;
							PageCacheUtil.singelChatPage.jsp.getViewport().add(jta);
							String msg = "7="+currentUserId+"="+friendId+"="+"������һ���ļ���·��Ϊ��"+path+"="+currentNickname;
							try {
								SendMessageUtil.sendMessage(msg);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "û��ѡ���ļ�");
					
				}
			}
		});
		jp.add(jb);
		//3��������Ϣ��ť
		JButton jb1 = new JButton("������Ϣ");
		jb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//1����ȡ���û��������Ϣ����
				String message = jta1.getText();
				if (message.equals("")||message==null) {
					JOptionPane.showMessageDialog(null, "���ܷ��Ϳ���Ϣ��");
				}
				else {
					//2����������Ϣ�����ȷŵ���ʷ�����¼����  ��ǰ���û��ǳ�+message
					//���õ���ǰ����ʷ�����¼�ı�
					String historyMessage = PageCacheUtil.singelChatPage.jta.getText();
					//����һ���µ��ı��� ����ʷ�����¼�͵�ǰ���͵����������Ϣȫ��ƴ�ӵ��´�����jta����
					JTextArea jta = new JTextArea();
					jta.setFont(new Font("΢���ź�", Font.BOLD, 16));
					jta.setEnabled(false);//�����ı���
					jta.setLineWrap(true);//�����ı����Զ�����
					jta.append(historyMessage);
					jta.append(currentNickname+":");
					jta.append("\r\n");
					jta.append(message);
					jta.append("\r\n");
					PageCacheUtil.singelChatPage.jta = jta;
					PageCacheUtil.singelChatPage.jsp.getViewport().add(jta);
					//3�����û���д����Ϣ���
					jta1.setText("");
					//4���ͻ���׼��һ����Ϣ 7=userId=friendId=message=currentNickname
					String msg = "7="+currentUserId+"="+friendId+"="+message+"="+currentNickname;
					try {
						SendMessageUtil.sendMessage(msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		jb1.setBounds(410, 150, 90, 30);
		jp.add(jb1);
		jp.setBounds(0,560,500,180);
		this.add(jp);
		/**
		 * ����������
		 */
		ImageIcon image = new ImageIcon("images/back.jpg");
		JLabel jl = new JLabel(image);
		jl.setBounds(500, 0, 300, 750);
		this.add(jl);
		
		
		//0������ҳ������������
		this.setVisible(true);
		this.setSize(800,800);
		this.setResizable(false);//���ý��治������ק����
		this.setTitle("��"+friendNickname+"��˽�Ľ���");
		//����ҳ���LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
	}
}
