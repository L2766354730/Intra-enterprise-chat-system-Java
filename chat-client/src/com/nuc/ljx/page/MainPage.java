package com.nuc.ljx.page;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.nuc.ljx.util.PageCacheUtil;
import com.nuc.ljx.util.SendMessageUtil;

public class MainPage extends JFrame{
	private static final long serialVersionUID = 1L;
	public JLabel header,nn,sign;
	public JScrollPane jsp;
	public String searchKey;
	/**
	 * ������ҳ �������userId
	 * 1����ȡ��ǰ�û���Ϣ ��Ⱦ��һ������
	 * 2����ȡ��ǰ�û��ĺ����б���Ⱦ�ڶ�������
	 * @param userId
	 */
	public void init(String userId) {
		/**
		 * 1�������˷���һ����Ϣ ��ȡ��ǰ�û�id��Ӧ���û���Ϣ�Լ������б���Ϣ
		 *    2=userid---����userId��ȡ��ǰ�û���Ϣ�Լ��û��ĺ����б���Ϣ
		 */
		String msg = "2="+userId;
		try {
			SendMessageUtil.sendMessage(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/**
		 * ��һ������һ��Ϊ3
		 */
		this.setLayout(null);
		/**
		 * ��һ������  �ɵ�ǰ�û���ͷ���ǳơ�����ǩ�������ð�ť���
		 */
		JPanel jp = new JPanel();
		jp.setLayout(null);
		//ͷ��
		ImageIcon h = new ImageIcon("images/man.png");
		header = new JLabel(h);
		header.setBounds(10, 10, 80, 80);
		jp.add(header);
		//�ǳ�
		nn = new JLabel("�����˾��ǲ�����");
		nn.setBounds(100, 10, 300, 30);
		jp.add(nn);
		//����ǩ��
		sign = new JLabel("����ֱ����ǧ��");
		sign.setBounds(100, 50, 300, 30);
		jp.add(sign);
		//���ð�ť
		ImageIcon set = new ImageIcon("images/settings.png");
		JLabel jset = new JLabel(set);
		jset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				if (clickCount == 2) {
					/**
					 * 1����Ҫ����һ���޸�����������
					 */
					ModifyPasswordPage mpp = new ModifyPasswordPage();
					//���޸�������滺������---һ��ͻ����յ�����˻�Ӧ֮����Ҫ���ٽ���
					PageCacheUtil.mpp = mpp;
					//����userId�����޸��������
					mpp.init(userId);
				}
			}
		});
		jset.setBounds(420, 30, 40, 40);
		jp.add(jset);
		jp.setBounds(0, 0, 500, 100);
		this.add(jp);
		/**
		 * �ڶ�������  ����������Ӻ��Ѱ�ť�����Ⱥ��ť���
		 */
		JPanel jp1 = new JPanel();
		jp1.setLayout(null);
		//������
		JTextField jtf = new JTextField("����������ǳ�");
		jtf.setBounds(3, 0, 350, 40);
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
						//�����˷���һ����Ϣ����  4=userId=searchKey
						msg = "2="+userId;
					}
					else {
						//�������ؼ��ֻ��������������Ŀ����Ϊ�����û����ĳ���û�Ϊ���ѳɹ�֮��ˢ���������
						PageCacheUtil.mainPage.searchKey = searchKey;
						//�����˷���һ����Ϣ����  8=userId=searchKey
						msg = "8="+userId+"="+searchKey;
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
		//��Ӻ���ͼƬ
		ImageIcon af = new ImageIcon("images/addFriend.png");
		JLabel jaf = new JLabel(af);
		jaf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				if (clickCount == 2) {
					//���ŵ�ǰ�û���userId������Ӻ��ѽ���
					AddFriendPage addFriendPage = new AddFriendPage();
					addFriendPage.init(userId);
					PageCacheUtil.addFriendPage = addFriendPage;
				}
			}
		});
		jaf.setBounds(375, 0, 40, 40);
		jp1.add(jaf);
		//ȺͼƬ
		ImageIcon ag = new ImageIcon("images/group.png");
		JLabel jag = new JLabel(ag);
		jag.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				if (clickCount == 2) {
					GroupChatListPage groupChatListPage = new GroupChatListPage();
					PageCacheUtil.groupChatListPage = groupChatListPage;
					groupChatListPage.init(userId);
				}
			}
		});
		jag.setBounds(430, 0, 40, 40);
		jp1.add(jag);
		jp1.setBounds(0, 105, 500, 40);
		this.add(jp1);
		
		/**
		 * ����������---�����б����
		 *   JScrollPaneֻ�����һ�����������������������JScrollPane�Ĺ����������
		 *   JScrollPane�����ӵ���JPanel��壬��������JPanel���ĵ�setPreferredSize�ڲ��ߴ� �������ڲ��ߴ�
		 *   �޷����ֹ�����
		 */
		jsp = new JScrollPane();
		//���ù������ĺ����������ʧ
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(0, 150, 480, 600);
		this.add(jsp);
		//0������ҳ������������
		this.setVisible(true);
		this.setSize(500,850);
		this.setResizable(false);//���ý��治������ק����
		this.setTitle("������");
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocation(0, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}