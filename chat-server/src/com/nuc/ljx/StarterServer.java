package com.nuc.ljx;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextArea;

import com.nuc.ljx.page.MonitorPage;
import com.nuc.ljx.util.ServerReceiverMessageThread;

/**
 * 服务端的启动程序
 * @author 11018
 *
 */
public class StarterServer {
	public static MonitorPage monitorPage;
	//缓存在线用户的
	public static Map<Integer, Socket> onLineUsers = new HashMap<Integer, Socket>();
	public static void main(String[] args) throws IOException {
		//创建一个服务端
		ServerSocket ss = new ServerSocket(8000);
		MonitorPage monitorPage = new MonitorPage();
		StarterServer.monitorPage = monitorPage;
		monitorPage.init();
		JTextArea jta = new JTextArea();
		jta.setEnabled(false);
		jta.append("========================服务端启动成功,等待客户端连接==========================");
		StarterServer.monitorPage.jta = jta;
		StarterServer.monitorPage.jsp.getViewport().add(jta);
		
		//接受多个客户端的请求 用于和多个客户端之间进行通信
		while (true) {
			//只能接受一个客户端的连接请求
			Socket socket = ss.accept();
			//开启一个独立线程，专门用来处理接受到的一个客户端请求
			new Thread(new ServerReceiverMessageThread(socket)).start();
		}
		
	}
	
	public static void setMonitorPage(String msg) {
		String text = StarterServer.monitorPage.jta.getText();
		JTextArea jta = new JTextArea();
		jta.setEnabled(false);
		jta.append(text);
		jta.append("\r\n");
		jta.append(msg);
		jta.append("\r\n");
		StarterServer.monitorPage.jta = jta;
		StarterServer.monitorPage.jsp.getViewport().add(jta);
	}
	
}