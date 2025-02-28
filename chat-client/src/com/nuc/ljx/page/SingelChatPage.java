package com.nuc.ljx.page;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.alibaba.fastjson.JSONObject;
import com.nuc.ljx.util.PageCacheUtil;
import com.nuc.ljx.util.SendMessageUtil;
/**
 * 好友私聊界面
 * @author 11018
 *
 */
public class SingelChatPage extends JFrame{
	public JScrollPane jsp; 
	public JTextArea jta; //历史聊天记录区域的文本框
	public void init(String currentUserId,String friendId,String friendNickname,String currentNickname) {
		/**
		 * 1、向服务端发送一个消息
		 * 6=userId=friendId 查询用户和好友之间的历史聊天记录
		 */
		String msg = "6="+currentUserId+"="+friendId;
		try {
			SendMessageUtil.sendMessage(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//1、设置页面的布局 GridLayout  null--绝对布局
		this.setLayout(null);
		/**
		 * 第一块区域
		 */
		jsp = new JScrollPane();
		jsp.setBounds(0, 0, 500, 550);
		this.add(jsp);
		/**
		 * 第二块区域  由文本域、发送文件按钮  发送消息按钮三部分组成
		 */
		JPanel jp = new JPanel();
		jp.setLayout(null);
		//1、文本域
		JTextArea jta1 = new JTextArea();
		jta1.setBounds(0, 0, 500, 150);
		
		jp.add(jta1);
		
		//2、发送文件按钮
		JButton jb = new JButton("发送文件");
		jb.setBounds(300, 150, 100, 30);
		//双击jl7开启一个文件选择器 选择文件
		jb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/**
				 * 创建一个文件选择器
				 */
				JFileChooser jfc = new JFileChooser();
				//设置一个文件选择器可以选择什么东西
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = jfc.showOpenDialog(null);
				//如果用户在文件选择器选择了东西 result!=1  
				if (result != 1) {
					//file是我们在文件选择器中选择的文件 
					File file = jfc.getSelectedFile();
					String fileName = file.getName();
					String[] array = fileName.split("\\.");
					String suffixName = array[array.length-1];
					//上传到本地的文件路径  chatfiles/12312312313.png
					String name = array[array.length-2]+"."+suffixName;
					if (JOptionPane.showConfirmDialog(null, "确定发送该文件吗？\n"+name)==0) {
						String path = "chatfiles/"+name;
						//IO流的传输  字节流  文件复制
						try {
							FileInputStream fis = new FileInputStream(file);
							FileOutputStream fos = new FileOutputStream(new File(path));
							int read =0;
							while((read = fis.read()) != -1) {
								fos.write(read);
							}
							//2、将聊天信息数据先放到历史聊天记录区域  当前的用户昵称+message
							//先拿到以前的历史聊天记录文本
							String historyMessage = PageCacheUtil.singelChatPage.jta.getText();
							//创建一个新的文本框 将历史聊天记录和当前发送的这个聊天信息全部拼接到新创建的jta里面
							JTextArea jta = new JTextArea();
							jta.setFont(new Font("微软雅黑", Font.BOLD, 16));
							jta.setEnabled(false);//禁用文本域
							jta.setLineWrap(true);//设置文本域自动换行
							jta.append(historyMessage);
							jta.append(currentNickname+":");
							jta.append("\r\n");
							jta.append("发送了一个文件，路径为："+path);
							jta.append("\r\n");
							PageCacheUtil.singelChatPage.jta = jta;
							PageCacheUtil.singelChatPage.jsp.getViewport().add(jta);
							String msg = "7="+currentUserId+"="+friendId+"="+"发送了一个文件，路径为："+path+"="+currentNickname;
							try {
								SendMessageUtil.sendMessage(msg);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "没有选择文件");
					
				}
			}
		});
		jp.add(jb);
		//3、发送消息按钮
		JButton jb1 = new JButton("发送消息");
		jb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//1、获取到用户输入的信息数据
				String message = jta1.getText();
				if (message.equals("")||message==null) {
					JOptionPane.showMessageDialog(null, "不能发送空消息！");
				}
				else {
					//2、将聊天信息数据先放到历史聊天记录区域  当前的用户昵称+message
					//先拿到以前的历史聊天记录文本
					String historyMessage = PageCacheUtil.singelChatPage.jta.getText();
					//创建一个新的文本框 将历史聊天记录和当前发送的这个聊天信息全部拼接到新创建的jta里面
					JTextArea jta = new JTextArea();
					jta.setFont(new Font("微软雅黑", Font.BOLD, 16));
					jta.setEnabled(false);//禁用文本域
					jta.setLineWrap(true);//设置文本域自动换行
					jta.append(historyMessage);
					jta.append(currentNickname+":");
					jta.append("\r\n");
					jta.append(message);
					jta.append("\r\n");
					PageCacheUtil.singelChatPage.jta = jta;
					PageCacheUtil.singelChatPage.jsp.getViewport().add(jta);
					//3、将用户填写的信息清空
					jta1.setText("");
					//4、客户端准备一个消息 7=userId=friendId=message=currentNickname
					String msg = "7="+currentUserId+"="+friendId+"="+message+"="+currentNickname;
					try {
						SendMessageUtil.sendMessage(msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		jb1.setBounds(410, 150, 90, 30);
		jp.add(jb1);
		jp.setBounds(0,560,500,180);
		this.add(jp);
		/**
		 * 第三块区域
		 */
		ImageIcon image = new ImageIcon("images/back.jpg");
		JLabel jl = new JLabel(image);
		jl.setBounds(500, 0, 300, 750);
		this.add(jl);
		
		
		//0、设置页面的相关配置项
		this.setVisible(true);
		this.setSize(800,800);
		this.setResizable(false);//设置界面不可拖拉拽变形
		this.setTitle("和"+friendNickname+"的私聊界面");
		//设置页面的LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
	}
}
