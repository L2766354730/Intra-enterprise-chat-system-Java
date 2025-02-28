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

public class AddGroupPage extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(String userId) {
		AddGroupPage addGroupPage = this;
		// 1������ҳ��Ĳ��� GridLayout null--���Բ���
		this.setLayout(new GridLayout(3, 2));
		// Ⱥ�������
		JLabel jl1 = new JLabel("Ⱥ��",JLabel.CENTER);
		jl1.setFont(new Font("����", Font.BOLD, 20));
		JTextField jtf = new JTextField("������Ⱥ��");
		jtf.setFont(new Font("����", Font.BOLD, 20));
		this.add(jl1);this.add(jtf);
		jtf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf.setText("");
			}
		});
		// Ⱥ��������
		JLabel jl4 = new JLabel("���",JLabel.CENTER);
		jl4.setFont(new Font("����", Font.BOLD, 20));
		JTextField jtf1 = new JTextField("������Ⱥ���");
		jtf1.setFont(new Font("����", Font.BOLD, 20));
		this.add(jl4);this.add(jtf1);
		jtf1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf1.setText("");
			}
		});
		
		JButton jb1 = new JButton("ȡ��");
		jb1.setFont(new Font("����", Font.BOLD, 20));
		jb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addGroupPage.dispose();
			}
		});
		JButton jb2 = new JButton("����");
		jb2.setFont(new Font("����", Font.BOLD, 20));
		jb2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//1����ȡ���û������Ⱥ��Ϣ
				String groupName = jtf.getText();
				String groupIntro = jtf1.getText();
				//2��У���Ƿ�������Ⱥ��
				if (groupName == null || groupName.equals("")) {
					JOptionPane.showMessageDialog(null, "Ⱥ���Ʊ������룡");
					return;
				}
				//3��׼��һ���ַ������ݸ���� 
				//9=userId=groupName=groupIntro
				String msg = "9="+userId+"="+groupName+"="+groupIntro;
				//4���������ݸ��������ɽ�Ⱥ�߼�
				try {
					SendMessageUtil.sendMessage(msg);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		this.add(jb1);this.add(jb2);
		//1������ҳ��Ĳ��� GridLayout  null--���Բ���
		//0������ҳ������������
		this.setVisible(true);
		this.setSize(400,300);
		this.setResizable(false);//���ý��治������ק����
		this.setTitle("���Ⱥ����");
		//����ҳ���LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
	}
}
