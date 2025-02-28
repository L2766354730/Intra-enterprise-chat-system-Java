package com.nuc.ljx.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.nuc.ljx.StartClient;

/**
 * 工具类  用来给服务端的socket发送数据
 * @author 11018
 */
public class SendMessageUtil {
	public static void sendMessage(String msg) throws IOException {
		//1、拿到客户端的socket
		Socket socket = StartClient.socket;
		//2、准备输出流输出msg
		OutputStream outputStream = socket.getOutputStream();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream,"GBK"));
		//3、输出数据
		bw.write(msg);
		bw.newLine();
		bw.flush();
	}
}
