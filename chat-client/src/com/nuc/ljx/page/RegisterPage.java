package com.nuc.ljx.page;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.nuc.ljx.util.SendMessageUtil;

/**
 * ע����� ע���û���ʱ�� userid addtime������Ҫ�û����� ����Ǻ�����ɵ� username �˺ţ�ע���ʱ��ϵͳ������ɵ� 
 * ��д �ǳ� ���� �ظ����� ǩ�� �Ա� ͷ��
 * 
 * @author 11018
 *
 */
public class RegisterPage extends JFrame {
	private static final long serialVersionUID = 1L;

	public void init() {
		RegisterPage registerPage = this;
		// 1������ҳ��Ĳ��� GridLayout null--���Բ���
		this.setLayout(new GridLayout(7, 2));
		// �ǳƺ��ı������
		JLabel jl1 = new JLabel("�ǳ�",JLabel.CENTER);
		jl1.setFont(new Font("����", Font.BOLD, 20));
		JTextField jtf = new JTextField("����������ǳ�");
		jtf.setFont(new Font("����", Font.BOLD, 14));
		this.add(jl1);this.add(jtf);
		jtf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf.setText("");
			}
		});
		// ��������������
		JLabel jl2 = new JLabel("����",JLabel.CENTER);
		jl2.setFont(new Font("����", Font.BOLD, 20));
		JPasswordField jpf = new JPasswordField("");
		this.add(jl2);this.add(jpf);
		
		// �ٴ�������������������
		JLabel jl3 = new JLabel("��ȷ������",JLabel.CENTER);
		jl3.setFont(new Font("����", Font.BOLD, 20));
		JPasswordField jpf1 = new JPasswordField("");
		this.add(jl3);this.add(jpf1);
		
		// ����ǩ�����ı������
		JLabel jl4 = new JLabel("����ǩ��",JLabel.CENTER);
		jl4.setFont(new Font("����", Font.BOLD, 20));
		JTextField jtf1 = new JTextField("��������ĸ���ǩ��");
		jtf1.setFont(new Font("����", Font.BOLD, 14));
		this.add(jl4);this.add(jtf1);
		jtf1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf1.setText("");
			}
		});
		//�Ա�-----��ѡ��ť ���뽫��ѡ��ť�ӵ�һ����ť���в���ʵ�ֵ�ѡЧ��
		JLabel jl5 = new JLabel("�Ա�",JLabel.CENTER);
		jl5.setFont(new Font("����", Font.BOLD, 20));
		ButtonGroup bg = new ButtonGroup();//�������
		JRadioButton  jrb = new JRadioButton("��");
		jrb.setSelected(true);
		JRadioButton  jrb1 = new JRadioButton("Ů");
		bg.add(jrb);
		bg.add(jrb1);
	
		JPanel jp = new JPanel();
		jp.add(jrb);
		jp.add(jrb1);
		jp.setBorder(BorderFactory.createEtchedBorder());
		this.add(jl5);this.add(jp);;
		
		//ͷ��
		JLabel jl6 = new JLabel("ͷ��",JLabel.CENTER);
		jl6.setFont(new Font("����", Font.BOLD, 20));
		JLabel jl7 = new JLabel("��ѡ�����ͷ��");//�ı������
		//˫��jl7����һ���ļ�ѡ���� ѡ���ļ�
		jl7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				if (clickCount == 2) {
					/**
					 * ����һ���ļ�ѡ����
					 */
					JFileChooser jfc = new JFileChooser();
					//����һ���ļ�ѡ��������ѡ��ʲô����
					jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int result = jfc.showOpenDialog(null);
					//����û����ļ�ѡ����ѡ���˶��� result!=1  
					if (result != 1) {
						//file���������ļ�ѡ������ѡ����ļ� a.png
						File file = jfc.getSelectedFile();
						String fileName = file.getName();
						//1���ж�һ���ļ��ǲ���ͼƬ�ļ� jpg JPEG png gif
						String[] array = fileName.split("\\.");
						String suffixName = array[array.length-1];
						List<String> names = new ArrayList<String>();
						names.add("jpg");names.add("JPEG");names.add("png");names.add("gif");
						//�ж�һ�����ϴ����ǲ���ͼƬ
						if (names.contains(suffixName)) {
							//�ϴ������ص��ļ�·��  images/12312312313.png
							String path = "images/"+System.currentTimeMillis()+"."+suffixName;
							//IO���Ĵ���  �ֽ���  �ļ�����
							try {
								FileInputStream fis = new FileInputStream(file);
								FileOutputStream fos = new FileOutputStream(new File(path));
								int read =0;
								while((read = fis.read()) != -1) {
									fos.write(read);
								}
								//���ϴ��ɹ����ļ�·��path��ֵ��jl7���JLbel�ı����
								jl7.setText(path);
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else {
							JOptionPane.showMessageDialog(null, "ѡ��Ĳ���ͼƬ�ļ�");
						}
					}else {
						JOptionPane.showMessageDialog(null, "û��ѡ��ͷ��");
						
					}
				}
			}
		});
		jl7.setBorder(BorderFactory.createEtchedBorder());
		 
		jl7.setFont(new Font("����", Font.BOLD, 15));
		this.add(jl6);this.add(jl7);

		
		JButton jb1 = new JButton("ȥ��¼");
		jb1.setFont(new Font("����", Font.BOLD, 20));
		jb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new LoginPage().init("");
				registerPage.dispose();
			}
		});
		JButton jb2 = new JButton("ע��");
		jb2.setFont(new Font("����", Font.BOLD, 20));
		jb2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//1����ȡ���û����������ע����Ϣ ����ͷ����Ϣ
				String nickname = jtf.getText();
				String pass = jpf.getText();
				String repass = jpf1.getText();
				String sign = jtf1.getText();
				String sex="";
				if (jrb.isSelected()) {
					sex = "��";
				}else {
					sex = "Ů";
				}
				//ͷ��·���������  images/xxxxx.jpg   ��ѡ�����ͷ��
				String headerPath = jl7.getText();
				//����û�û��ѡ��ͷ����ô�����Ա�ֵһ���Ա��Ĭ��ͷ��
				if (headerPath.equals("��ѡ�����ͷ��")) {
					if (sex.equals("��")) {
						headerPath = "images/man.png";
					}else {
						headerPath = "images/woman.png";
					}
				}
				//2��У����������������Ƿ�һ��
				if (pass == null || pass.equals("") || repass == null || repass.equals("")) {
					JOptionPane.showMessageDialog(null, "����δ���룡");
					return;
				}
				if (!pass.equals(repass)) {
					JOptionPane.showMessageDialog(null, "�����������벻һ�£�");
					return;
				}
				//3��׼��һ���ַ������ݸ���� 
				//0=nickname=password=sign=sex=headerPath
				String msg = "0="+nickname+"="+pass+"="+sign+"="+sex+"="+headerPath;
				//4���������ݸ���������ע���߼�
				try {
					SendMessageUtil.sendMessage(msg);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		this.add(jb1);this.add(jb2);
		// 0������ҳ������������
		this.setVisible(true);
		this.setSize(500, 400);
		this.setResizable(false);// ���ý��治������ק����
		this.setTitle("ע�����");
		// ����ҳ���LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
