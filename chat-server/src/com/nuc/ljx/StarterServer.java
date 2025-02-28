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
 * ����˵���������
 * @author 11018
 *
 */
public class StarterServer {
	public static MonitorPage monitorPage;
	//���������û���
	public static Map<Integer, Socket> onLineUsers = new HashMap<Integer, Socket>();
	public static void main(String[] args) throws IOException {
		//����һ�������
		ServerSocket ss = new ServerSocket(8000);
		MonitorPage monitorPage = new MonitorPage();
		StarterServer.monitorPage = monitorPage;
		monitorPage.init();
		JTextArea jta = new JTextArea();
		jta.setEnabled(false);
		jta.append("========================����������ɹ�,�ȴ��ͻ�������==========================");
		StarterServer.monitorPage.jta = jta;
		StarterServer.monitorPage.jsp.getViewport().add(jta);
		
		//���ܶ���ͻ��˵����� ���ںͶ���ͻ���֮�����ͨ��
		while (true) {
			//ֻ�ܽ���һ���ͻ��˵���������
			Socket socket = ss.accept();
			//����һ�������̣߳�ר������������ܵ���һ���ͻ�������
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