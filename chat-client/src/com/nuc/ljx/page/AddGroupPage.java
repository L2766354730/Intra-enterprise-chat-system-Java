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
		// 1、设置页面的布局 GridLayout null--绝对布局
		this.setLayout(new GridLayout(3, 2));
		// 群名输入框
		JLabel jl1 = new JLabel("群名",JLabel.CENTER);
		jl1.setFont(new Font("楷体", Font.BOLD, 20));
		JTextField jtf = new JTextField("请输入群名");
		jtf.setFont(new Font("楷体", Font.BOLD, 20));
		this.add(jl1);this.add(jtf);
		jtf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf.setText("");
			}
		});
		// 群简介输入框
		JLabel jl4 = new JLabel("简介",JLabel.CENTER);
		jl4.setFont(new Font("楷体", Font.BOLD, 20));
		JTextField jtf1 = new JTextField("请输入群简介");
		jtf1.setFont(new Font("楷体", Font.BOLD, 20));
		this.add(jl4);this.add(jtf1);
		jtf1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf1.setText("");
			}
		});
		
		JButton jb1 = new JButton("取消");
		jb1.setFont(new Font("楷体", Font.BOLD, 20));
		jb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addGroupPage.dispose();
			}
		});
		JButton jb2 = new JButton("创建");
		jb2.setFont(new Font("楷体", Font.BOLD, 20));
		jb2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//1、获取到用户输入的群信息
				String groupName = jtf.getText();
				String groupIntro = jtf1.getText();
				//2、校验是否输入了群名
				if (groupName == null || groupName.equals("")) {
					JOptionPane.showMessageDialog(null, "群名称必须输入！");
					return;
				}
				//3、准备一个字符串传递给后端 
				//9=userId=groupName=groupIntro
				String msg = "9="+userId+"="+groupName+"="+groupIntro;
				//4、发送数据给服务端完成建群逻辑
				try {
					SendMessageUtil.sendMessage(msg);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		this.add(jb1);this.add(jb2);
		//1、设置页面的布局 GridLayout  null--绝对布局
		//0、设置页面的相关配置项
		this.setVisible(true);
		this.setSize(400,300);
		this.setResizable(false);//设置界面不可拖拉拽变形
		this.setTitle("添加群界面");
		//设置页面的LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
	}
}
