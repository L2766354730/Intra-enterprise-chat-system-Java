package com.nuc.ljx.page;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.nuc.ljx.util.PageCacheUtil;
import com.nuc.ljx.util.SendMessageUtil;

/**
 * 登录界面：
 *    1、用户名和用户名输入框
 *    2、密码和密码输入框
 *    3、注册按钮和登录按钮
 * @author 11018
 */
public class ModifyPasswordPage extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(String userId) {
		//1、设置页面的布局 GridLayout  null--绝对布局
		this.setLayout(new GridLayout(4, 2));
		
		JLabel jl1 = new JLabel("旧密码");
		jl1.setFont(new Font("楷体", Font.BOLD, 20));
		JPasswordField jpf = new JPasswordField("");
		
		JLabel jl2 = new JLabel("新密码");
		jl2.setFont(new Font("楷体", Font.BOLD, 20));
		JPasswordField jpf1 = new JPasswordField("");
		
		JLabel jl3 = new JLabel("请再次输入新密码");
		jl3.setFont(new Font("楷体", Font.BOLD, 20));
		JPasswordField jpf2 = new JPasswordField("");
		
		/**
		 * 如果点击取消，代表不想修改密码了，需要将修改密码界面销毁
		 */
		JButton jb1 = new JButton("取消");
		jb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PageCacheUtil.mpp.dispose();
			}
		});
		jb1.setFont(new Font("楷体", Font.BOLD, 20));
		
		/**
		 * 修改密码的话
		 *   1、校验一下两次输入的新密码是否一致
		 *   2、如果一致向服务端发送信息
		 *      3=userId=oldpassword=newpassword
		 */
		JButton jb2 = new JButton("修改");
		jb2.setFont(new Font("楷体", Font.BOLD, 20));
		jb2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//1、先将用户输入的旧密码和两次的新密码获取到
				String oldPassword = jpf.getText();
				String newPassword = jpf1.getText();
				String renewPassword = jpf2.getText();
				//2、判断用户是否输入新密码和重复输入的新密码
				if (newPassword == null || newPassword.equals("") || renewPassword == null || renewPassword.equals("")) {
					JOptionPane.showMessageDialog(null, "新密码未输入！");
					return;
				}
				//3、判断一下两次输入的新密码是否一致
				if (newPassword.equals(renewPassword)) {
					//两次输入新密码一致  向服务端发送一个3=userId=oldpassword=newpassword
					//3=1=123456=654321
					String msg = "3="+userId+"="+oldPassword+"="+newPassword;
					try {
						//将信息发送给服务端
						SendMessageUtil.sendMessage(msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					//两次输入的新密码不一致  弹窗提示两次密码不一致
					JOptionPane.showMessageDialog(null, "两次输入新密码不一致！");
				}
				
			}
		});
		
		this.add(jl1);this.add(jpf);
		this.add(jl2);this.add(jpf1);
		this.add(jl3);this.add(jpf2);
		this.add(jb1);this.add(jb2);
		
		
		//0、设置页面的相关配置项
		this.setVisible(true);
		this.setSize(400,250);
		this.setResizable(false);//设置界面不可拖拉拽变形
		this.setTitle("修改密码界面");
		//设置页面的LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
	}
}
