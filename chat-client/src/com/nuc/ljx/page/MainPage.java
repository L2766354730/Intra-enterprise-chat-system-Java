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
	 * 进入首页 必须带着userId
	 * 1、获取当前用户信息 渲染第一块区域
	 * 2、获取当前用户的好友列表，渲染第二块区域
	 * @param userId
	 */
	public void init(String userId) {
		/**
		 * 1、向服务端发送一个消息 获取当前用户id对应的用户信息以及好友列表信息
		 *    2=userid---根据userId获取当前用户信息以及用户的好友列表信息
		 */
		String msg = "2="+userId;
		try {
			SendMessageUtil.sendMessage(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/**
		 * 将一个界面一分为3
		 */
		this.setLayout(null);
		/**
		 * 第一块区域  由当前用户的头像、昵称、个性签名、设置按钮组成
		 */
		JPanel jp = new JPanel();
		jp.setLayout(null);
		//头像
		ImageIcon h = new ImageIcon("images/man.png");
		header = new JLabel(h);
		header.setBounds(10, 10, 80, 80);
		jp.add(header);
		//昵称
		nn = new JLabel("不爱了就是不爱了");
		nn.setBounds(100, 10, 300, 30);
		jp.add(nn);
		//个性签名
		sign = new JLabel("飞流直下三千尺");
		sign.setBounds(100, 50, 300, 30);
		jp.add(sign);
		//设置按钮
		ImageIcon set = new ImageIcon("images/settings.png");
		JLabel jset = new JLabel(set);
		jset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				if (clickCount == 2) {
					/**
					 * 1、需要创建一个修改密码界面对象
					 */
					ModifyPasswordPage mpp = new ModifyPasswordPage();
					//将修改密码界面缓存起来---一会客户端收到服务端回应之后，需要销毁界面
					PageCacheUtil.mpp = mpp;
					//带着userId创建修改密码界面
					mpp.init(userId);
				}
			}
		});
		jset.setBounds(420, 30, 40, 40);
		jp.add(jset);
		jp.setBounds(0, 0, 500, 100);
		this.add(jp);
		/**
		 * 第二块区域  由搜索框、添加好友按钮、添加群按钮组成
		 */
		JPanel jp1 = new JPanel();
		jp1.setLayout(null);
		//搜索框
		JTextField jtf = new JTextField("请输入好友昵称");
		jtf.setBounds(3, 0, 350, 40);
		jp1.add(jtf);
		jtf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf.setText("");
			}
		});
		//当搜索框输入信息之后 按回车我们才连接服务端 查询数据 渲染表格
		jtf.addKeyListener(new KeyAdapter() {
			//键盘某个按键 按下的操作逻辑
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					/**
					 * 获取到用户输入的搜索信息
					 */
					String searchKey = jtf.getText();
					String msg = "";
					if (searchKey ==null || searchKey.equals("")) {
						//向服务端发送一个信息数据  4=userId=searchKey
						msg = "2="+userId;
					}
					else {
						//将搜索关键字缓存起来，缓存的目的是为了在用户添加某个用户为好友成功之后，刷新搜索结果
						PageCacheUtil.mainPage.searchKey = searchKey;
						//向服务端发送一个信息数据  8=userId=searchKey
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
		//添加好友图片
		ImageIcon af = new ImageIcon("images/addFriend.png");
		JLabel jaf = new JLabel(af);
		jaf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				if (clickCount == 2) {
					//带着当前用户的userId进入添加好友界面
					AddFriendPage addFriendPage = new AddFriendPage();
					addFriendPage.init(userId);
					PageCacheUtil.addFriendPage = addFriendPage;
				}
			}
		});
		jaf.setBounds(375, 0, 40, 40);
		jp1.add(jaf);
		//群图片
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
		 * 第三块区域---好友列表界面
		 *   JScrollPane只能添加一个组件，而且这个组件必须在JScrollPane的构造器中添加
		 *   JScrollPane如果添加的是JPanel面板，必须设置JPanel面板的的setPreferredSize内部尺寸 不设置内部尺寸
		 *   无法出现滚动条
		 */
		jsp = new JScrollPane();
		//设置滚动面板的横向滚动条消失
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(0, 150, 480, 600);
		this.add(jsp);
		//0、设置页面的相关配置项
		this.setVisible(true);
		this.setSize(500,850);
		this.setResizable(false);//设置界面不可拖拉拽变形
		this.setTitle("主界面");
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocation(0, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}