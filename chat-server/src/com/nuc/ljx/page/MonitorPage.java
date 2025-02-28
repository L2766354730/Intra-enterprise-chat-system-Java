package com.nuc.ljx.page;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.nuc.ljx.StarterServer;

public class MonitorPage extends JFrame {
	public JScrollPane jsp;
	public JTextArea jta;
	public void init() {
		this.setLayout(null);
		JButton jb = new JButton("发送公告");
		jb.setBounds(350, 10, 100, 40);
		jb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String msg = JOptionPane.showInputDialog(null, "请输入要发布的公告");
				/**
				 * 找到当前系统所有在线的用户  发送一个信息 8=msg
				 */
				Collection<Socket> values = StarterServer.onLineUsers.values();
				for(Socket socket: values) {
					String msg7 = "8="+msg;
					try {
						OutputStream outputStream = socket.getOutputStream();
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "GBK"));
						// 3、输出数据
						bw.write(msg7);
						bw.newLine();
						bw.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		this.add(jb);
		
		jsp = new JScrollPane();
		jsp.setBounds(0,100,800,600);
		this.add(jsp);
		this.setSize(800, 800);
		this.setTitle("服务端监控界面");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	

}
