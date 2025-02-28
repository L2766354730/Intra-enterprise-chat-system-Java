package com.nuc.ljx;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.nuc.ljx.page.LoginPage;
import com.nuc.ljx.util.ClientReceiverMessageThread;
import com.nuc.ljx.util.PageCacheUtil;
/**
 * �ͻ��˵���������
 * @author 11018
 *
 */
public class StartClient {
	public static Socket socket;
	public static void main(String[] args) throws UnknownHostException, IOException{
		//1������һ����¼���棬����չʾ�˵�¼����
		LoginPage loginPage = new LoginPage();
		loginPage.init("");
		//��������loginPage���滺��������Ŀ����Ϊ�˵Ƚ�����Ϣ�߳̽��ܵ���¼�ɹ���Ϣ֮�� �ر������¼����
		PageCacheUtil.loginPage = loginPage;
		//2������һ��Socket���ӷ����ServerSokct
		Socket socket = new Socket("localhost", 8000);
		//3�������19�д��벻���� �������ӷ���˳ɹ�  �ɹ�֮����Ҫ���ͻ��˵�socket��������
		StartClient.socket = socket;
		//4������һ��������Ϣ�߳����ڲ���Ͻ��ܷ���˸��ͻ��˷��ص���Ϣ
		new Thread(new ClientReceiverMessageThread()).start();
	}
}
