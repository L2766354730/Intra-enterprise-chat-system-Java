package com.nuc.ljx.page;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.nuc.ljx.util.PageCacheUtil;
import com.nuc.ljx.util.SendMessageUtil;

public class GroupChatPage extends JFrame{
	public JScrollPane jsp; 
	public JTextArea jta; //��ʷ�����¼������ı���
	public void init(String groupId,String groupName,String currentNickname) {
		/**
		 * 1�������˷���һ����Ϣ
		 * 11=groupId ��ѯȺ�ĵ���ʷ�����¼
		 */
		String msg = "11="+groupId;
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
		 * Ⱥ��Ա��Ϣ��ť
		 * �˳�Ⱥ��ť
		 */
		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		//Ⱥ��Ա
		ImageIcon g = new ImageIcon("images/group.png");
		JLabel groupmenber = new JLabel(g);
		groupmenber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				if (clickCount == 2) {
					GroupMemberPage groupMemberPage = new GroupMemberPage();
					PageCacheUtil.groupMemberPage = groupMemberPage;
					groupMemberPage.init(groupId);
				}
			}
		});
		groupmenber.setBounds(2, 2, 40, 40);
		JLabel member = new JLabel("Ⱥ��Ա");
		member.setBounds(2, 42, 40, 18);
		jPanel.add(member);
		jPanel.add(groupmenber);
		//�˳�Ⱥ
		ImageIcon ge = new ImageIcon("images/groupexit.png");
		JLabel groupexit = new JLabel(ge);
		groupexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				if (clickCount == 2) {
					if (JOptionPane.showConfirmDialog(null, "ȷ���˳���Ⱥ��")==0) {
						String msg = "16="+PageCacheUtil.userId+"="+groupId;
						try {
							SendMessageUtil.sendMessage(msg);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		groupexit.setBounds(450, 2, 40, 40);
		JLabel exit = new JLabel("�˳�Ⱥ");
		exit.setBounds(450, 42, 40, 18);
		jPanel.add(exit);
		jPanel.add(groupexit);
		jPanel.setBounds(0, 0, 500, 70);
		this.add(jPanel);
		/**
		 * �ڶ�������
		 */
		jsp = new JScrollPane();
		jsp.setBounds(0, 72, 500, 475);
		this.add(jsp);
		/**
		 * ����������  ���ı��򡢷����ļ���ť  ������Ϣ��ť���������
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
							String historyMessage = PageCacheUtil.groupChatPage.jta.getText();
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
							PageCacheUtil.groupChatPage.jta = jta;
							PageCacheUtil.groupChatPage.jsp.getViewport().add(jta);
							String msg = "12="+PageCacheUtil.userId+"="+groupId+"="+"������һ���ļ���·��Ϊ��"+path;
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
					String historyMessage = PageCacheUtil.groupChatPage.jta.getText();
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
					PageCacheUtil.groupChatPage.jta = jta;
					PageCacheUtil.groupChatPage.jsp.getViewport().add(jta);
					//3�����û���д����Ϣ���
					jta1.setText("");
					//4���ͻ���׼��һ����Ϣ 12=userId=groupId=message
					String msg = "12="+PageCacheUtil.userId+"="+groupId+"="+message;
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
		this.setSize(800,900);
		this.setResizable(false);//���ý��治������ק����
		this.setTitle("Ⱥ�ģ�"+groupName+"������");
		//����ҳ���LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
	}
}
