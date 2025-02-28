package com.nuc.ljx.page;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.nuc.ljx.util.PageCacheUtil;
import com.nuc.ljx.util.SendMessageUtil;

public class EntryGroupPage extends JFrame{
	public JScrollPane jsp;
	public JTable jt;
	public String userId;
	public String searchKey;
	/**
	 * ���ŵ�ǰ�û�id�������Ӻ��ѽ���
	 * @param userId
	 */
	public void init(String userId) {
		this.userId = userId;
		
		//1������ҳ��Ĳ��� GridLayout  null--���Բ���
		this.setLayout(null);
		/**
		 * ��һ������  ������
		 */
		JTextField jtf = new JTextField();
		jtf.setFont(new Font("����",Font.BOLD,20));
		jtf.setText("������Ⱥ������Ⱥ��");
		jtf.setBounds(100, 60, 600, 50);
		jtf.setBorder(BorderFactory.createLineBorder(Color.green));
		jtf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf.setText("");
			}
		});
		//��������������Ϣ֮�� ���س����ǲ����ӷ���� ��ѯ���� ��Ⱦ���
		jtf.addKeyListener(new KeyAdapter() {
			//����ĳ������ ���µĲ����߼�
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					/**
					 * ��ȡ���û������������Ϣ
					 */
					String searchKey = jtf.getText();
					if (searchKey ==null || searchKey.equals("")) {
						JOptionPane.showMessageDialog(null, "δ��д�����ؼ���");
						return;
					}
					//�������ؼ��ֻ��������������Ŀ����Ϊ�����û�����Ⱥ�ɹ�֮��ˢ���������
					PageCacheUtil.entryGroupPage.searchKey = searchKey;
					//�����˷���һ����Ϣ����  14=userId=searchKey
					String msg = "14="+userId+"="+searchKey;
					try {
						SendMessageUtil.sendMessage(msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	
		this.add(jtf);
		/**
		 * �ڶ������� ���Թ����ı��
		 */
		Object[] columnNames = {"Ⱥ��id","Ⱥ������","Ⱥ���˺�","Ⱥ������","Ⱥ�ļ��"};
		jt = new JTable(new Object[0][0], columnNames);
		jt.setFont(new Font("����",Font.BOLD,14));
		
		//���ñ��Ԫ���޷��޸�
		jt.setEnabled(false);
		jsp = new JScrollPane(jt);
		jsp.setBounds(20, 120, 760, 600);
		this.add(jsp);
		
		
		
		//0������ҳ������������
		this.setVisible(true);
		this.setSize(800,800);
		this.setResizable(false);//���ý��治������ק����
		this.setTitle("����Ⱥ����");
		//����ҳ���LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * ����Ⱥ
	 */
	public void entryGroup() {
		/**
		 * 1��˫������ĳһ��������ʾ�Ƿ�Ҫ��Ӽ����Ⱥ
		 */
		//���ñ�񲻿�˫������
		PageCacheUtil.entryGroupPage.jt.setEnabled(false);
		//˫����񵯴���ʾ�Ƿ�Ҫ���
		PageCacheUtil.entryGroupPage.jt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					//������ʾ
					//��ȡ����ı���ĳһ��
					int row = PageCacheUtil.entryGroupPage.jt.rowAtPoint(e.getPoint());
					//��ȡ����ı���ĳһ�еĵڶ���
					Object groupName = PageCacheUtil.entryGroupPage.jt.getValueAt(row, 1);
					Object groupId = PageCacheUtil.entryGroupPage.jt.getValueAt(row, 0);
					//������ʾ�Ƿ�Ҫ����----����ȷ��ȡ���ĵ�����
					int result = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ�������Ⱥ��"+groupName);
					//�û������ȷ��
					if (result == 0) {
						//�����˷���һ����Ϣ  15=userId=groupId
						String msg = "15="+PageCacheUtil.entryGroupPage.userId+"="+groupId;
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
	}
}