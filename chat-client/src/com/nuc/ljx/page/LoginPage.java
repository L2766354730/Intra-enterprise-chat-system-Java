package com.nuc.ljx.page;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.nuc.ljx.util.PageCacheUtil;
import com.nuc.ljx.util.SendMessageUtil;

/**
 * ��¼���棺
 *    1���û������û��������
 *    2����������������
 *    3��ע�ᰴť�͵�¼��ť
 * @author 11018
 */
public class LoginPage extends JFrame{
	private static final long serialVersionUID = 1L;

	public void init(String userId) {
		LoginPage loginPage = this;
		//1������ҳ��Ĳ��� GridLayout  null--���Բ���
		this.setLayout(new GridLayout(3, 2));
		
		JLabel jl1 = new JLabel("�û���");
		jl1.setFont(new Font("����", Font.BOLD, 20));
		System.out.println(userId);
		String s = null;
		if (userId.equals("")||userId==null) {
			s = "�������û���";
		}
		else {
			s = userId;
		}
		JTextField jtf = new JTextField(s);
		jtf.setFont(new Font("����", Font.BOLD, 20));
		jtf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf.setText("");
			}
		});
		JLabel jl2 = new JLabel("����");
		jl2.setFont(new Font("����", Font.BOLD, 20));
		JPasswordField jpf = new JPasswordField("");
		
		JButton jb1 = new JButton("ȥע��");
		jb1.setFont(new Font("����", Font.BOLD, 20));
		jb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterPage rp = new RegisterPage();
				rp.init();
				PageCacheUtil.rp = rp;
				PageCacheUtil.loginPage.dispose();
			}
		});
		
		JButton jb2 = new JButton("��¼");
		jb2.setFont(new Font("����", Font.BOLD, 20));
		jb2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/**
				 * 1�����ܵ��û�������û�������������
				 */
				String username = jtf.getText();
				String password = jpf.getText();
				/**
				 * 2������Ϣ���͸������---��¼
				 *   ������͵ĵ�¼�Ĺ������� �������ݸ�ʽ����
				 *   1=username=password
				 */
				String msg = "1="+username+"="+password;
				try {
					PageCacheUtil.userId = username;
					SendMessageUtil.sendMessage(msg);
					System.out.println("�ͻ��˷��͵�¼����ɹ�");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		this.add(jl1);this.add(jtf);
		this.add(jl2);this.add(jpf);
		this.add(jb1);this.add(jb2);
		//0������ҳ������������
		this.setVisible(true);
		this.setSize(400,200);
		this.setResizable(false);//���ý��治������ק����
		this.setTitle("��¼����");
		//����ҳ���LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
