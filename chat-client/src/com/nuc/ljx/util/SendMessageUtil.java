package com.nuc.ljx.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.nuc.ljx.StartClient;

/**
 * ������  ����������˵�socket��������
 * @author 11018
 */
public class SendMessageUtil {
	public static void sendMessage(String msg) throws IOException {
		//1���õ��ͻ��˵�socket
		Socket socket = StartClient.socket;
		//2��׼����������msg
		OutputStream outputStream = socket.getOutputStream();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream,"GBK"));
		//3���������
		bw.write(msg);
		bw.newLine();
		bw.flush();
	}
}
