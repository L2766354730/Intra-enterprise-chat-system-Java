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
 * 添加好友界面
 * @author 11018
 */
public class AddFriendPage extends JFrame{
	public JScrollPane jsp;
	public JTable jt;
	public String userId;
	public String searchKey;
	/**
	 * 带着当前用户id创建增加好友界面
	 * @param userId
	 */
	public void init(String userId) {
		this.userId = userId;
		
		//1、设置页面的布局 GridLayout  null--绝对布局
		this.setLayout(null);
		/**
		 * 第一块区域  搜索框
		 */
		JTextField jtf = new JTextField();
		jtf.setFont(new Font("楷体",Font.BOLD,20));
		jtf.setText("请输入用户名或者账号");
		jtf.setBounds(100, 60, 600, 50);
		jtf.setBorder(BorderFactory.createLineBorder(Color.green));
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
					if (searchKey ==null || searchKey.equals("")) {
						JOptionPane.showMessageDialog(null, "未填写搜索关键字");
						return;
					}
					//将搜索关键字缓存起来，缓存的目的是为了在用户添加某个用户为好友成功之后，刷新搜索结果
					PageCacheUtil.addFriendPage.searchKey = searchKey;
					//向服务端发送一个信息数据  4=userId=searchKey
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
		 * 第二块区域 可以滚动的表格
		 */
		Object[] columnNames = {"用户id","用户昵称","用户账号","用户性别","用户个性签名"};
		jt = new JTable(new Object[0][0], columnNames);
		jt.setFont(new Font("楷体",Font.BOLD,14));
		
		//设置表格单元格无法修改
		jt.setEnabled(false);
		jsp = new JScrollPane(jt);
		jsp.setBounds(20, 120, 760, 600);
		this.add(jsp);
		
		
		
		//0、设置页面的相关配置项
		this.setVisible(true);
		this.setSize(800,800);
		this.setResizable(false);//设置界面不可拖拉拽变形
		this.setTitle("添加好友界面");
		//设置页面的LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * 添加好友
	 */
	public void addFriend() {
		/**
		 * 1、双击表格的某一行数据提示是否要添加这行用户为好友
		 */
		//设置表格不可双击更改
		PageCacheUtil.addFriendPage.jt.setEnabled(false);
		//双击表格弹窗提示是否要添加本行数据的用户昵称为含有
		PageCacheUtil.addFriendPage.jt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					//弹窗提示是否要添加xxx为好友
					//获取点击的表格的某一行
					int row = PageCacheUtil.addFriendPage.jt.rowAtPoint(e.getPoint());
					//获取点击的表格的某一行的第二列
					Object nickname = PageCacheUtil.addFriendPage.jt.getValueAt(row, 1);
					Object friendId = PageCacheUtil.addFriendPage.jt.getValueAt(row, 0);
					//弹窗提示是否要添加nickname为好友----带有确定取消的弹出窗
					int result = JOptionPane.showConfirmDialog(null, "是否确定添加"+nickname+"为好友");
					//用户点击了确定
					if (result == 0) {
						//向服务端发送一个消息  5=userId=friendId
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















