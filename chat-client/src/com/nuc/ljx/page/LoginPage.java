package com.nuc.ljx.page;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.nuc.ljx.util.PageCacheUtil;
import com.nuc.ljx.util.SendMessageUtil;

/**
 * 登录界面：
 *    1、用户名和用户名输入框
 *    2、密码和密码输入框
 *    3、注册按钮和登录按钮
 * @author 11018
 */
public class LoginPage extends JFrame{
	private static final long serialVersionUID = 1L;

	public void init(String userId) {
		LoginPage loginPage = this;
		//1、设置页面的布局 GridLayout  null--绝对布局
		this.setLayout(new GridLayout(3, 2));
		
		JLabel jl1 = new JLabel("用户名");
		jl1.setFont(new Font("楷体", Font.BOLD, 20));
		System.out.println(userId);
		String s = null;
		if (userId.equals("")||userId==null) {
			s = "请输入用户名";
		}
		else {
			s = userId;
		}
		JTextField jtf = new JTextField(s);
		jtf.setFont(new Font("楷体", Font.BOLD, 20));
		jtf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf.setText("");
			}
		});
		JLabel jl2 = new JLabel("密码");
		jl2.setFont(new Font("楷体", Font.BOLD, 20));
		JPasswordField jpf = new JPasswordField("");
		
		JButton jb1 = new JButton("去注册");
		jb1.setFont(new Font("楷体", Font.BOLD, 20));
		jb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterPage rp = new RegisterPage();
				rp.init();
				PageCacheUtil.rp = rp;
				PageCacheUtil.loginPage.dispose();
			}
		});
		
		JButton jb2 = new JButton("登录");
		jb2.setFont(new Font("楷体", Font.BOLD, 20));
		jb2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/**
				 * 1、接受到用户输入的用户名和密码数据
				 */
				String username = jtf.getText();
				String password = jpf.getText();
				/**
				 * 2、把消息发送给服务端---登录
				 *   如果发送的登录的功能数据 发送数据格式如下
				 *   1=username=password
				 */
				String msg = "1="+username+"="+password;
				try {
					PageCacheUtil.userId = username;
					SendMessageUtil.sendMessage(msg);
					System.out.println("客户端发送登录请求成功");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		this.add(jl1);this.add(jtf);
		this.add(jl2);this.add(jpf);
		this.add(jb1);this.add(jb2);
		//0、设置页面的相关配置项
		this.setVisible(true);
		this.setSize(400,200);
		this.setResizable(false);//设置界面不可拖拉拽变形
		this.setTitle("登录界面");
		//设置页面的LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
