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
	 * 进入群聊列表 必须带着userId
	 * 1.第一块区域放置建群按钮
	 * 2、获取当前用户的群列表，渲染第三块区域
	 * @param userId
	 */
	public void init(String userId) {
		/**
		 * 1、向服务端发送一个消息 获取当前用户id对应的群列表信息
		 *    10=userid---根据userId获取当前用户的群列表信息
		 */
		String msg = "10="+userId;
		try {
			SendMessageUtil.sendMessage(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/**
		 * 将一个界面一分为2
		 */
		this.setLayout(null);
		
		/**
		 * 第一块区域  由搜索框、添加群按钮组成
		 */
		JPanel jp1 = new JPanel();
		jp1.setLayout(null);
		//搜索框
		JTextField jtf = new JTextField("请输入群名称或群号");
		jtf.setBounds(3, 0, 350, 55);
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
						//向服务端发送一个信息数据  10=userId
						msg = "10="+userId;
					}
					else {
						//将搜索关键字缓存起来，缓存的目的是为了在用户添加群成功之后，刷新搜索结果
						PageCacheUtil.groupChatListPage.searchKey = searchKey;
						//向服务端发送一个信息数据  13=userId=searchKey
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
					 * 1、需要创建一个加入群界面对象
					 */
					EntryGroupPage entryGroupPage = new EntryGroupPage();
					//将建群界面缓存起来---一会客户端收到服务端回应之后，需要销毁界面
					PageCacheUtil.entryGroupPage = entryGroupPage;
					//带着userId创建建群页面
					entryGroupPage.init(userId);
				}
			}
		});
		jentry.setBounds(370, 0, 40, 40);
		jp1.add(jentry);
		JLabel jentryword = new JLabel("加入群");
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
					 * 1、需要创建一个建群界面对象
					 */
					AddGroupPage addGroupPage = new AddGroupPage();
					//将建群界面缓存起来---一会客户端收到服务端回应之后，需要销毁界面
					PageCacheUtil.agpAddGroupPage = addGroupPage;
					//带着userId创建建群页面
					addGroupPage.init(userId);
				}
			}
		});
		jadd.setBounds(432, 0, 40, 40);
		jp1.add(jadd);
		JLabel jaddword = new JLabel("创建群");
		jaddword.setBounds(432, 45, 40, 15);
		jp1.add(jaddword);
		
		
		jp1.setBounds(0, 10, 500, 60);
		this.add(jp1);
		
		/**
		 * 第二块区域---群聊列表界面
		 *   JScrollPane只能添加一个组件，而且这个组件必须在JScrollPane的构造器中添加
		 *   JScrollPane如果添加的是JPanel面板，必须设置JPanel面板的的setPreferredSize内部尺寸 不设置内部尺寸
		 *   无法出现滚动条
		 */
		jsp = new JScrollPane();
		//设置滚动面板的横向滚动条消失
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(2, 75, 480, 700);
		this.add(jsp);
		//0、设置页面的相关配置项
		this.setVisible(true);
		this.setSize(500,850);
		this.setResizable(false);//设置界面不可拖拉拽变形
		this.setTitle("你的群聊列表");
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocation(500, 150);
	}

}
