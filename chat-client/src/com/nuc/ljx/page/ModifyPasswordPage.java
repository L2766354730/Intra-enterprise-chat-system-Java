package com.nuc.ljx.page;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.nuc.ljx.util.PageCacheUtil;
import com.nuc.ljx.util.SendMessageUtil;

/**
 * ��¼���棺
 *    1���û������û��������
 *    2����������������
 *    3��ע�ᰴť�͵�¼��ť
 * @author 11018
 */
public class ModifyPasswordPage extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(String userId) {
		//1������ҳ��Ĳ��� GridLayout  null--���Բ���
		this.setLayout(new GridLayout(4, 2));
		
		JLabel jl1 = new JLabel("������");
		jl1.setFont(new Font("����", Font.BOLD, 20));
		JPasswordField jpf = new JPasswordField("");
		
		JLabel jl2 = new JLabel("������");
		jl2.setFont(new Font("����", Font.BOLD, 20));
		JPasswordField jpf1 = new JPasswordField("");
		
		JLabel jl3 = new JLabel("���ٴ�����������");
		jl3.setFont(new Font("����", Font.BOLD, 20));
		JPasswordField jpf2 = new JPasswordField("");
		
		/**
		 * ������ȡ�����������޸������ˣ���Ҫ���޸������������
		 */
		JButton jb1 = new JButton("ȡ��");
		jb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PageCacheUtil.mpp.dispose();
			}
		});
		jb1.setFont(new Font("����", Font.BOLD, 20));
		
		/**
		 * �޸�����Ļ�
		 *   1��У��һ������������������Ƿ�һ��
		 *   2�����һ�������˷�����Ϣ
		 *      3=userId=oldpassword=newpassword
		 */
		JButton jb2 = new JButton("�޸�");
		jb2.setFont(new Font("����", Font.BOLD, 20));
		jb2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//1���Ƚ��û�����ľ���������ε��������ȡ��
				String oldPassword = jpf.getText();
				String newPassword = jpf1.getText();
				String renewPassword = jpf2.getText();
				//2���ж��û��Ƿ�������������ظ������������
				if (newPassword == null || newPassword.equals("") || renewPassword == null || renewPassword.equals("")) {
					JOptionPane.showMessageDialog(null, "������δ���룡");
					return;
				}
				//3���ж�һ������������������Ƿ�һ��
				if (newPassword.equals(renewPassword)) {
					//��������������һ��  �����˷���һ��3=userId=oldpassword=newpassword
					//3=1=123456=654321
					String msg = "3="+userId+"="+oldPassword+"="+newPassword;
					try {
						//����Ϣ���͸������
						SendMessageUtil.sendMessage(msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					//��������������벻һ��  ������ʾ�������벻һ��
					JOptionPane.showMessageDialog(null, "�������������벻һ�£�");
				}
				
			}
		});
		
		this.add(jl1);this.add(jpf);
		this.add(jl2);this.add(jpf1);
		this.add(jl3);this.add(jpf2);
		this.add(jb1);this.add(jb2);
		
		
		//0������ҳ������������
		this.setVisible(true);
		this.setSize(400,250);
		this.setResizable(false);//���ý��治������ק����
		this.setTitle("�޸��������");
		//����ҳ���LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
	}
}
