package com.nuc.ljx.page;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.nuc.ljx.util.PageCacheUtil;
import com.nuc.ljx.util.SendMessageUtil;

public class GroupChatListPage extends JFrame{
	private static final long serialVersionUID = 1L;
	public JScrollPane jsp;
	public String searchKey;
	/**
	 * ����Ⱥ���б� �������userId
	 * 1.��һ��������ý�Ⱥ��ť
	 * 2����ȡ��ǰ�û���Ⱥ�б���Ⱦ����������
	 * @param userId
	 */
	public void init(String userId) {
		/**
		 * 1�������˷���һ����Ϣ ��ȡ��ǰ�û�id��Ӧ��Ⱥ�б���Ϣ
		 *    10=userid---����userId��ȡ��ǰ�û���Ⱥ�б���Ϣ
		 */
		String msg = "10="+userId;
		try {
			SendMessageUtil.sendMessage(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/**
		 * ��һ������һ��Ϊ2
		 */
		this.setLayout(null);
		
		/**
		 * ��һ������  �����������Ⱥ��ť���
		 */
		JPanel jp1 = new JPanel();
		jp1.setLayout(null);
		//������
		JTextField jtf = new JTextField("������Ⱥ���ƻ�Ⱥ��");
		jtf.setBounds(3, 0, 350, 55);
		jp1.add(jtf);
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
					String msg = "";
					if (searchKey ==null || searchKey.equals("")) {
						//�����˷���һ����Ϣ����  10=userId
						msg = "10="+userId;
					}
					else {
						//�������ؼ��ֻ��������������Ŀ����Ϊ�����û����Ⱥ�ɹ�֮��ˢ���������
						PageCacheUtil.groupChatListPage.searchKey = searchKey;
						//�����˷���һ����Ϣ����  13=userId=searchKey
						msg = "13="+userId+"="+searchKey;
					}
					System.out.println(msg);
					try {
						SendMessageUtil.sendMessage(msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		ImageIcon entry = new ImageIcon("images/entrygroup.png");
		JLabel jentry = new JLabel(entry);
		jentry.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				if (clickCount == 2) {
					/**
					 * 1����Ҫ����һ������Ⱥ�������
					 */
					EntryGroupPage entryGroupPage = new EntryGroupPage();
					//����Ⱥ���滺������---һ��ͻ����յ�����˻�Ӧ֮����Ҫ���ٽ���
					PageCacheUtil.entryGroupPage = entryGroupPage;
					//����userId������Ⱥҳ��
					entryGroupPage.init(userId);
				}
			}
		});
		jentry.setBounds(370, 0, 40, 40);
		jp1.add(jentry);
		JLabel jentryword = new JLabel("����Ⱥ");
		jentryword.setBounds(370, 45, 40, 15);
		jp1.add(jentryword);
		
		
		ImageIcon add = new ImageIcon("images/addGroup.png");
		JLabel jadd = new JLabel(add);
		jadd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				if (clickCount == 2) {
					/**
					 * 1����Ҫ����һ����Ⱥ�������
					 */
					AddGroupPage addGroupPage = new AddGroupPage();
					//����Ⱥ���滺������---һ��ͻ����յ�����˻�Ӧ֮����Ҫ���ٽ���
					PageCacheUtil.agpAddGroupPage = addGroupPage;
					//����userId������Ⱥҳ��
					addGroupPage.init(userId);
				}
			}
		});
		jadd.setBounds(432, 0, 40, 40);
		jp1.add(jadd);
		JLabel jaddword = new JLabel("����Ⱥ");
		jaddword.setBounds(432, 45, 40, 15);
		jp1.add(jaddword);
		
		
		jp1.setBounds(0, 10, 500, 60);
		this.add(jp1);
		
		/**
		 * �ڶ�������---Ⱥ���б����
		 *   JScrollPaneֻ�����һ�����������������������JScrollPane�Ĺ����������
		 *   JScrollPane�����ӵ���JPanel��壬��������JPanel���ĵ�setPreferredSize�ڲ��ߴ� �������ڲ��ߴ�
		 *   �޷����ֹ�����
		 */
		jsp = new JScrollPane();
		//���ù������ĺ����������ʧ
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(2, 75, 480, 700);
		this.add(jsp);
		//0������ҳ������������
		this.setVisible(true);
		this.setSize(500,850);
		this.setResizable(false);//���ý��治������ק����
		this.setTitle("���Ⱥ���б�");
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocation(500, 150);
	}

}
