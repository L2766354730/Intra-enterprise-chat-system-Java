package com.nuc.ljx.page;

import java.awt.Font;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.nuc.ljx.util.SendMessageUtil;

public class GroupMemberPage extends JFrame{
	private static final long serialVersionUID = 1L;
	public JScrollPane jsp;
	/**
	 * 进入群成员列表 必须带着groupId
	 * @param groupId
	 */
	public void init(String groupId) {
		/**
		 * 1、向服务端发送一个消息 获取当前群对应的群成员信息
		 *    17=groupId
		 */
		String msg = "17="+groupId;
		try {
			SendMessageUtil.sendMessage(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setLayout(null);
		/**
		 * 群成员列表界面
		 *   JScrollPane只能添加一个组件，而且这个组件必须在JScrollPane的构造器中添加
		 *   JScrollPane如果添加的是JPanel面板，必须设置JPanel面板的的setPreferredSize内部尺寸 不设置内部尺寸
		 *   无法出现滚动条
		 */
		JLabel jLabel = new JLabel("群成员",JLabel.CENTER);
		jLabel.setBounds(2, 0, 480, 50);
		jLabel.setFont(new Font("楷体", Font.BOLD, 20));
		jsp = new JScrollPane();
		//设置滚动面板的横向滚动条消失
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(2, 50, 480, 700);
		this.add(jLabel);
		this.add(jsp);
		//0、设置页面的相关配置项
		this.setVisible(true);
		this.setSize(500,850);
		this.setResizable(false);//设置界面不可拖拉拽变形
		this.setTitle("该群群成员列表");
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocation(1000, 150);
	}

}