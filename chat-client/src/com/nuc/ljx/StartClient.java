package com.nuc.ljx;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.nuc.ljx.page.LoginPage;
import com.nuc.ljx.util.ClientReceiverMessageThread;
import com.nuc.ljx.util.PageCacheUtil;
/**
 * 客户端的启动程序
 * @author 11018
 *
 */
public class StartClient {
	public static Socket socket;
	public static void main(String[] args) throws UnknownHostException, IOException{
		//1、创建一个登录界面，并且展示了登录界面
		LoginPage loginPage = new LoginPage();
		loginPage.init("");
		//将创建的loginPage界面缓存起来，目的是为了等接受消息线程接受到登录成功信息之后 关闭这个登录界面
		PageCacheUtil.loginPage = loginPage;
		//2、创建一个Socket连接服务端ServerSokct
		Socket socket = new Socket("localhost", 8000);
		//3、如果第19行代码不报错 代表连接服务端成功  成功之后需要将客户端的socket缓存起来
		StartClient.socket = socket;
		//4、启动一个接受消息线程用于不间断接受服务端给客户端返回的消息
		new Thread(new ClientReceiverMessageThread()).start();
	}
}
