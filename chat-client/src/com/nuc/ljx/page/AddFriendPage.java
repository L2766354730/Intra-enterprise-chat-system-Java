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
/**
 * ��Ӻ��ѽ���
 * @author 11018
 */
public class AddFriendPage extends JFrame{
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
		jtf.setText("�������û��������˺�");
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
					//�������ؼ��ֻ��������������Ŀ����Ϊ�����û����ĳ���û�Ϊ���ѳɹ�֮��ˢ���������
					PageCacheUtil.addFriendPage.searchKey = searchKey;
					//�����˷���һ����Ϣ����  4=userId=searchKey
					String msg = "4="+userId+"="+searchKey;
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
		Object[] columnNames = {"�û�id","�û��ǳ�","�û��˺�","�û��Ա�","�û�����ǩ��"};
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
		this.setTitle("��Ӻ��ѽ���");
		//����ҳ���LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * ��Ӻ���
	 */
	public void addFriend() {
		/**
		 * 1��˫������ĳһ��������ʾ�Ƿ�Ҫ��������û�Ϊ����
		 */
		//���ñ�񲻿�˫������
		PageCacheUtil.addFriendPage.jt.setEnabled(false);
		//˫����񵯴���ʾ�Ƿ�Ҫ��ӱ������ݵ��û��ǳ�Ϊ����
		PageCacheUtil.addFriendPage.jt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					//������ʾ�Ƿ�Ҫ���xxxΪ����
					//��ȡ����ı���ĳһ��
					int row = PageCacheUtil.addFriendPage.jt.rowAtPoint(e.getPoint());
					//��ȡ����ı���ĳһ�еĵڶ���
					Object nickname = PageCacheUtil.addFriendPage.jt.getValueAt(row, 1);
					Object friendId = PageCacheUtil.addFriendPage.jt.getValueAt(row, 0);
					//������ʾ�Ƿ�Ҫ���nicknameΪ����----����ȷ��ȡ���ĵ�����
					int result = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ�����"+nickname+"Ϊ����");
					//�û������ȷ��
					if (result == 0) {
						//�����˷���һ����Ϣ  5=userId=friendId
						String msg = "5="+PageCacheUtil.addFriendPage.userId+"="+friendId;
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















