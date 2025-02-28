package com.nuc.ljx.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuc.ljx.StartClient;
import com.nuc.ljx.page.GroupChatListPage;
import com.nuc.ljx.page.GroupChatPage;
import com.nuc.ljx.page.GroupMemberPage;
import com.nuc.ljx.page.LoginPage;
import com.nuc.ljx.page.MainPage;
import com.nuc.ljx.page.SingelChatPage;

/**
 * �ͻ��˽�����Ϣ���߳���
 * @author 11018
 *
 */
public class ClientReceiverMessageThread implements Runnable{
	/**
	 * �߳�������ԴԴ���ϵĽ��ܷ������Ϣ
	 */
	@Override
	public void run() {
		Socket socket = StartClient.socket;
		try {
			InputStream inputStream = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"gbk"));
			String line = null;
			while((line = br.readLine()) != null) {
				System.out.println("����˻�Ӧ��һ����Ϣ��"+line);
				//У�����˸��ҷ��ص�����
				exuecuteClientLogic(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ���ܵ�����˷��صĴ����������ݲ�ͬ�Ĵ����� ��ɲ�ͬ��ҵ���߼�
	 * @param line
	 * 1=404=
	 * 2=xxx=xxx=xx
	 */
	private void exuecuteClientLogic(String line) {
		// TODO Auto-generated method stub
		String[] array = line.split("=");
		String xieyi = array[0];
		switch (xieyi) {
			case "0":
				handlerRegisterLogic(array);
				break;
			/**
			 * ������ܵ��ķ���˷��ص���Ϣ��1��ͷ�� ������ǵ�¼У�����Ϣ
			 * 1=200=�ɹ���Ϣ=userid
			 * 1=404=ʧ����Ϣ
			 */
			case "1":
				handlerLoginLogic(array);
				break;
			case "2":
				handlerMainLogic(array);
				break;
			case "3":
				handlerUpdatePasswordLogic(array);
				break;
			case "4":
				handlerSearchLogic(array);
				break;
			case "5":
				handlerAddFriendLogic(array);
				break;
			case "6":
				handlerHistoryMessageLogic(array);
				break;
			case "7":
				// "7="+friendNickname+"���㷢����һ����Ϣ���뼰ʱ����"+friendid;
				JOptionPane.showMessageDialog(null, array[1]);
//				String friendNickname = array[1];
//				String friendId = array[3];
//				PageCacheUtil.singelChatPage.init(PageCacheUtil.userId,friendId.toString(),friendNickname,PageCacheUtil.userNickname);
//				
				break;
			case "8":
				JOptionPane.showMessageDialog(null, "ϵͳ�������棺"+array[1]);
				break;
			case "9":
				handlerAddGroup(array);
				break;
			case "10":
				handlerGroupListsLogic(array);
				break;
			case "11":
				handlerGroupHistoryChatLogic(array);
				break;
			case "12":
				handlerSearchGroupLogic(array);
				break;
			case "13":
				handlerEntryGroupLogic(array);
				break;
			case "14":
				handlerExitGroupLogic(array);
				break;
			case "15":
				handlerGroupMemberLogic(array);
				break;
			default:
				break;
		}
	}
	
	/**
	 * Ⱥ��Աҳ����Ⱦ
	 * "15=200=��ѯ�ɹ�="+groupMembersJson
	 * "15=404=Ⱥ��ԱΪ��"
	 * @param array
	 */
	private void handlerGroupMemberLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//��ҳ�滺���л�ȡ����ǰ������������
			GroupMemberPage groupMemberPage = PageCacheUtil.groupMemberPage;
			JPanel members = new JPanel();
			members.setLayout(null);
			System.out.println(array[3]);
			/**
			 * groupInfoJson��һ��json����---Java���� �����������߶��Ⱥ��Ϣ
			 */
			String userJSON = array[3];
			//��һ��json�ַ���ת��һ��json����
			JSONArray jsonArray = JSON.parseArray(userJSON);
			/**
			 * ��ȡjson��������----Ⱥ�б�����
			 */
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				System.out.println(i+"======="+jsonObject);
				/**
				 * jp����ÿһ��Ⱥ�����
				 */
				JPanel jp = new JPanel();
				jp.setLayout(null);
				//ͷ��
				ImageIcon h = new ImageIcon(jsonObject.getString("header"));
				JLabel header = new JLabel(h);
				header.setBounds(10, 10, 80, 80);
				jp.add(header);
				//�ǳ�
				JLabel nickname = new JLabel(jsonObject.getString("nickname"));
				nickname.setBounds(100, 10, 300, 30);
				jp.add(nickname);
				//����ǩ��
				JLabel sign = new JLabel(jsonObject.getString("sign"));
				sign.setBounds(100, 50, 300, 30);
				jp.add(sign);
				jp.setBounds(0,(i)*100+5, 500, 100);
				jp.setBorder(BorderFactory.createEtchedBorder());
				members.add(jp);
			}
			members.setPreferredSize(new Dimension(480,(jsonArray.size()-1)*100));
			groupMemberPage.jsp.getViewport().add(members);
		}else {
			/**
			 * ��ѯʧ��,û��Ⱥ��������ʾ
			 */
			JOptionPane.showMessageDialog(null, "û��Ⱥ��Ա��");
		}
		
	}
	/**
	 * ɾ��Ⱥ�������߼�
	 * 14=200=��Ⱥ�ɹ�
	 * @param array
	 */
	private void handlerExitGroupLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, array[2]);
			PageCacheUtil.groupChatPage.dispose();
			//��Ҫ����10��Э�� ��Ⱥ�б������Ҳͬ������һ��
			String msg1 = "10="+PageCacheUtil.userId;
			try {
				SendMessageUtil.sendMessage(msg1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, array[2]);
		}
	}
	/**
	 * ����Ҫ�����Ⱥ�߼�
	 * @param array
	 */
	private void handlerSearchGroupLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			System.out.println(array[3]);
			//��һ��json�����ʽ�����ݣ����������ǲ�ѯ�����Ķ���ƥ������
			String userJson = array[3];
			//json���飬json�����зŵľ��ǲ�ѯ������һ�����û���Ϣ
			JSONArray jsonArray = JSON.parseArray(userJson);
			if (jsonArray.isEmpty()) {
				//���û���ҵ�ƥ����Ϣ ���˵�����ʾ֮�⣬�ѱ������Ϊһ���ձ��
				JOptionPane.showMessageDialog(null, "û���ҵ�ƥ���Ⱥ��Ϣ");
				Object[] columnNames = {"Ⱥ��id","Ⱥ������","Ⱥ���˺�","Ⱥ������","Ⱥ�ļ��"};
				Object[][] rows =  new Object[0][0];
				PageCacheUtil.entryGroupPage.jt = new JTable(rows,columnNames);
				PageCacheUtil.entryGroupPage.jsp.getViewport().add(PageCacheUtil.entryGroupPage.jt);
			}else {
				/**
				 * ��json�����е����ݴ�����ΪJTable���Ȼ�������AddFriendPage�����е�JScollpane��
				 * Jtable��Ҫ��������
				 *   1��Object[]  columnNames
				 *   2��Object[][]  rows
				 */
				Object[] columnNames = {"Ⱥ��id","Ⱥ������","Ⱥ���˺�","Ⱥ������","Ⱥ�ļ��"};
				Object[][]  rows = new Object[jsonArray.size()][5];
				//��json�����е�ÿһ��jsonObject����ת����ΪObject���͵�����
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Object[] row = new Object[5];
					row[0] = jsonObject.get("groupId"); 
					row[1] = jsonObject.get("groupName"); 
					row[2] = jsonObject.get("groupNumber"); 
					row[3] = jsonObject.get("groupLeaderName"); 
					row[4] = jsonObject.get("groupIntro"); 
					rows[i] = row;
				}
				PageCacheUtil.entryGroupPage.jt = new JTable(rows,columnNames);
				//�ȸ�����������һ������¼�---�������е�ĳһ��Ⱥ
				PageCacheUtil.entryGroupPage.entryGroup();
				//��Ҫ�Ѵ����õ�jtable���뵽���������
				PageCacheUtil.entryGroupPage.jsp.getViewport().add(PageCacheUtil.entryGroupPage.jt);
			}
		}else {
			JOptionPane.showMessageDialog(null, "��ѯʧ��");
		}
	}
	
	/**
	 * ����Ⱥ�߼�
	 * 13=200=�����Ⱥ�ɹ�
	 * 13=404=����Ⱥʧ��
	 * @param array
	 */
	private void handlerEntryGroupLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//������ʾ��ӳɹ�
			JOptionPane.showMessageDialog(null, array[2]);
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//��Ҫ������Ⱥ������������ͬ������һ�� �����˷���һ��14=userId=searchKey
			String msg = "14="+PageCacheUtil.entryGroupPage.userId+"="+PageCacheUtil.entryGroupPage.searchKey;
			System.out.println("����Ⱥ�ɹ�"+msg);
			try {
				SendMessageUtil.sendMessage(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//��Ҫ����10��Э�� ��Ⱥ�б������Ҳͬ������һ��
			String msg1 = "10="+PageCacheUtil.entryGroupPage.userId;
			try {
				SendMessageUtil.sendMessage(msg1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			JOptionPane.showMessageDialog(null, array[2]);
		}
	}
	
	/**
	 * ��ѯȺ����ʷ��¼
	 * @param array
	 */
	private void handlerGroupHistoryChatLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//��ȡ���������¼
			String messageJson = array[3];
			System.out.println(messageJson);
			JSONArray jsonArray = JSON.parseArray(messageJson);
			//json���鲻Ϊ�� �������¼
			JTextArea jta = new JTextArea();
			jta.setFont(new Font("΢���ź�", Font.BOLD, 16));
			jta.setEnabled(false);//�����ı���
			jta.setLineWrap(true);//�����ı����Զ�����
			if (!jsonArray.isEmpty()) {
				for (int i = 0; i < jsonArray.size(); i++) {
					//json����ʹ���һ�������¼
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Object sender = jsonObject.get("sendMemberName");//�������ݵķ�����
					Object message = jsonObject.get("message");//��������
					System.out.println(sender);
					System.out.println(message);
					//�ѷ����ߺ���Ϣƴ�ӵ�˽�Ľ������ʷ�����¼����jta
					jta.append(sender.toString()+":");
					jta.append("\r\n");
					jta.append(message.toString());
					jta.append("\r\n");
				}
			}
			PageCacheUtil.groupChatPage.jta = jta;
			PageCacheUtil.groupChatPage.jsp.getViewport().add(jta);
		}
	}
	/**
	 * ��ʼ��Ⱥ���б�
	 * 10=200=��ѯ�ɹ�=groupInfoJson
	 * 10=404=û��Ⱥ
	 * @param array
	 */
	private void handlerGroupListsLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//��ҳ�滺���л�ȡ����ǰ������������
			GroupChatListPage groupChatListPage = PageCacheUtil.groupChatListPage;
			JPanel groups = new JPanel();
			groups.setLayout(null);
			System.out.println(array[3]);
			/**
			 * groupInfoJson��һ��json����---Java���� �����������߶��Ⱥ��Ϣ
			 */
			String userJSON = array[3];
			//��һ��json�ַ���ת��һ��json����
			JSONArray jsonArray = JSON.parseArray(userJSON);
			/**
			 * ��ȡjson��������----Ⱥ�б�����
			 */
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				System.out.println(i+"======="+jsonObject);
				/**
				 * jp����ÿһ��Ⱥ�����
				 */
				JPanel jp = new JPanel();
				jp.setLayout(null);
				String groupname = jsonObject.get("groupName").toString()+"("+jsonObject.get("groupNumber").toString()+")";
				//ͷ��
				ImageIcon h = new ImageIcon("images/group.png");
				JLabel header = new JLabel(h);
				header.setBounds(10, 10, 80, 80);
				jp.add(header);
				//Ⱥ��
				JLabel gn = new JLabel(groupname);
				gn.setBounds(100, 10, 300, 30);
				jp.add(gn);
				//Ⱥ���
				JLabel groupIntro = new JLabel(jsonObject.getString("groupIntro"));
				groupIntro.setBounds(100, 50, 300, 30);
				jp.add(groupIntro);
				jp.setBounds(0,(i)*100+5, 500, 100);
				
				Object currentGroupId = jsonObject.get("groupId");
				jp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int clickCount = e.getClickCount();
						if (clickCount == 2) {
							//currentUserId   friendId  nickname
							GroupChatPage groupChatPage = new GroupChatPage();
							PageCacheUtil.groupChatPage = groupChatPage;
							groupChatPage.init(currentGroupId.toString(),jsonObject.getString("groupName"),PageCacheUtil.userNickname);
							System.out.println("Ⱥ�Ľ����Ѿ�����");
						}
					}
				});
				jp.setBorder(BorderFactory.createEtchedBorder());
				groups.add(jp);
			}
			groups.setPreferredSize(new Dimension(480,(jsonArray.size()-1)*100));
			groupChatListPage.jsp.getViewport().add(groups);
		}else {
			/**
			 * ��ѯʧ��,û��Ⱥ��������ʾ
			 */
			GroupChatListPage groupChatListPage = PageCacheUtil.groupChatListPage;
			JPanel groups = new JPanel();
			JOptionPane.showMessageDialog(null, "û���ҵ�Ⱥ��Ŷ�����Ⱥ����ɣ�");
			groupChatListPage.jsp.getViewport().add(groups);
		}
		
	}
	/**
	 * �ͻ��˽��մ���Ⱥ�������
	 * 9=200=�����ɹ�=numberȺ��
	 * 9=404=����ʧ��
	 * @param array
	 */
	private void handlerAddGroup(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//������ʾ��ӳɹ�
			JOptionPane.showMessageDialog(null, array[2]+"Ⱥ��Ϊ"+array[3]);
			PageCacheUtil.agpAddGroupPage.dispose();
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//��Ҫ��Ⱥ���б����Ľ��ͬ������һ�� �����˷���һ��10=userId
			String msg = "10="+PageCacheUtil.userId;
			System.out.println("��Ⱥ�ɹ�"+msg);
			try {
				SendMessageUtil.sendMessage(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			JOptionPane.showMessageDialog(null, array[2]);
		}
	}
	
	
	/**
	 * �ͻ����������ܷ���˲�ѯ�������û���ĳ������֮�����ʷ�����¼
	 * 6=200=��ѯ�ɹ�=messagejson
	 * @param array
	 */
	private void handlerHistoryMessageLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//��ȡ�û��ͺ��ѵ����������¼
			String messageJson = array[3];
			System.out.println(messageJson);
			JSONArray jsonArray = JSON.parseArray(messageJson);
			//json���鲻Ϊ�� �������¼
			JTextArea jta = new JTextArea();
			jta.setFont(new Font("΢���ź�", Font.BOLD, 16));
			jta.setEnabled(false);//�����ı���
			jta.setLineWrap(true);//�����ı����Զ�����
			if (!jsonArray.isEmpty()) {
				for (int i = 0; i < jsonArray.size(); i++) {
					//json����ʹ���һ�������¼
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Object sender = jsonObject.get("senderNickName");//�������ݵķ�����
					Object message = jsonObject.get("message");//��������
					System.out.println(sender);
					System.out.println(message);
					//�ѷ����ߺ���Ϣƴ�ӵ�˽�Ľ������ʷ�����¼����jta
					jta.append(sender.toString()+":");
					jta.append("\r\n");
					jta.append(message.toString());
					jta.append("\r\n");
				}
			}
			PageCacheUtil.singelChatPage.jta = jta;
			PageCacheUtil.singelChatPage.jsp.getViewport().add(jta);
		}
	}
	/**
	 * �ͻ��������������˷��͵����Ӻ��ѵ�һ����Ϣ
	 * 5=200=�ɹ�
	 * 5=404=ʧ��
	 * @param array
	 */
	private void handlerAddFriendLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//������ʾ��ӳɹ�
			JOptionPane.showMessageDialog(null, array[2]);
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//��Ҫ����Ӻ��ѽ�����������ͬ������һ�� �����˷���һ��4=userId=searchKey
			String msg = "4="+PageCacheUtil.addFriendPage.userId+"="+PageCacheUtil.addFriendPage.searchKey;
			System.out.println("��Ӻ��ѳɹ�"+msg);
			try {
				SendMessageUtil.sendMessage(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//��Ҫ����2��Э�� ����ҳ������Ҳͬ������һ��
			String msg1 = "2="+PageCacheUtil.addFriendPage.userId;
			try {
				SendMessageUtil.sendMessage(msg1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			JOptionPane.showMessageDialog(null, array[2]);
		}
		
	}
	/**
	 * �������˷��ص���Ӻ��ѽ����������ѵ�����
	 * 4=200=��ѯ�ɹ�=userjSON
	 * @param array
	 */
	private void handlerSearchLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			System.out.println(array[3]);
			//��һ��json�����ʽ�����ݣ����������ǲ�ѯ�����Ķ���ƥ������
			String userJson = array[3];
			//json���飬json�����зŵľ��ǲ�ѯ������һ�����û���Ϣ
			JSONArray jsonArray = JSON.parseArray(userJson);
			if (jsonArray.isEmpty()) {
				//���û���ҵ�ƥ����Ϣ ���˵�����ʾ֮�⣬�ѱ������Ϊһ���ձ��
				JOptionPane.showMessageDialog(null, "û���ҵ�ƥ����û���Ϣ");
				Object[] columnNames = {"�û�id","�û��ǳ�","�û��˺�","�û��Ա�","�û�����ǩ��"};
				Object[][] rows =  new Object[0][0];
				PageCacheUtil.addFriendPage.jt = new JTable(rows,columnNames);
				PageCacheUtil.addFriendPage.jsp.getViewport().add(PageCacheUtil.addFriendPage.jt);
			}else {
				/**
				 * ��json�����е����ݴ�����ΪJTable���Ȼ�������AddFriendPage�����е�JScollpane��
				 * Jtable��Ҫ��������
				 *   1��Object[]  columnNames
				 *   2��Object[][]  rows
				 */
				Object[] columnNames = {"�û�id","�û��ǳ�","�û��˺�","�û��Ա�","�û�����ǩ��"};
				Object[][]  rows = new Object[jsonArray.size()][5];
				//��json�����е�ÿһ��jsonObject����ת����ΪObject���͵�����
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Object[] row = new Object[5];
					row[0] = jsonObject.get("userId"); 
					row[1] = jsonObject.get("nickname"); 
					row[2] = jsonObject.get("username"); 
					row[3] = jsonObject.get("sex"); 
					row[4] = jsonObject.get("sign"); 
					rows[i] = row;
				}
				PageCacheUtil.addFriendPage.jt = new JTable(rows,columnNames);
				//�ȸ�����������һ������¼�---��ӱ���е�ĳһ���û�Ϊ����
				PageCacheUtil.addFriendPage.addFriend();
				//��Ҫ�Ѵ����õ�jtable���뵽���������
				PageCacheUtil.addFriendPage.jsp.getViewport().add(PageCacheUtil.addFriendPage.jt);
			}
		}else {
			JOptionPane.showMessageDialog(null, "��ѯʧ��");
		}
	}
	/**
	 * �ͻ��˽��ܵ������ע������֮��ķ�����Ϣ
	 * 0=200=ע��ɹ�=number
	 * 0=404=ע��ʧ��
	 * @param array
	 */
	private void handlerRegisterLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//1��������ʾע��ɹ�
			JOptionPane.showMessageDialog(null, array[2]+",����˺�Ϊ"+array[3]+",�뱣����˺����ڵ�¼ϵͳ");
			PageCacheUtil.userName = array[3];
			//2������ע�����  ������¼����
			PageCacheUtil.rp.dispose();
			LoginPage loginPage = new LoginPage();
			PageCacheUtil.loginPage = loginPage;
			loginPage.init(array[3]);
		}else {
			JOptionPane.showMessageDialog(null, array[2]);
		}
		
	}
	/**
	 * �ͻ��˽��ܵ�������޸���������֮��ķ�����Ϣ  ��������Ϣ
	 * 3=200=�ɹ���Ϣ
	 * 3=404=ʧ����Ϣ
	 * @param array
	 */
	private void handlerUpdatePasswordLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//����ɹ�
			//������ʾ�޸ĳɹ�
			JOptionPane.showMessageDialog(null, array[2]+"�����µ�¼��");
			//�����޸��������
			PageCacheUtil.mpp.dispose();
			PageCacheUtil.mainPage.dispose();
			LoginPage loginPage = new LoginPage();
			System.out.println(PageCacheUtil.userName);
			loginPage.init(PageCacheUtil.userName);
			PageCacheUtil.loginPage = loginPage;
		}else {
			//�����޸�ʧ��
			JOptionPane.showMessageDialog(null, array[2]);
		}
		
	}
	/**
	 * �������˷��صĺ���ҳ���ݲ�ѯ��Ⱦ�йص���Ϣ
	 * 2=200=��ѯ�ɹ�=userJson
	 * 2=404=��ѯʧ��
	 * @param array
	 */
	private void handlerMainLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			System.out.println(array[3]);
			/**
			 * userJson��һ��json����---Java���� �����һ�����߶���û���Ϣ
			 * ���е�һ����Ϣ�ǵ�ǰ�û���Ϣ
			 * �ڶ������ݿ�ʼ�ͱ�������û��ĺ����б���Ϣ
			 */
			String userJSON = array[3];
			//��һ��json�ַ���ת��һ��json����
			JSONArray jsonArray = JSON.parseArray(userJSON);
			//��ȡjson�����еĵ�һ������δjson����
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			/**
			 * userJson�����е��û��ǳ� ����ǩ��  ͷ������Ԫ�ص�ֵ��ֵ�����ǵ�MainPage�����
			 * ͷ�񡢸���ǩ�����ǳ�
			 */
			Object header = jsonObject.get("header");
			Object nickname = jsonObject.get("nickname");
			Object sign = jsonObject.get("sign");
			//��ҳ�滺���л�ȡ����ǰ������������
			MainPage mainPage = PageCacheUtil.mainPage;
			//���������еĵ�һ�������ͷ���ͼƬ���ǳƵ��ı���ǩ�����ı���������
			ImageIcon h = new ImageIcon(header.toString());
			mainPage.header.setIcon(h);
			mainPage.nn.setText(nickname.toString());
			mainPage.sign.setText(sign.toString());
			mainPage.setTitle(nickname.toString()+"��������");
			PageCacheUtil.userNickname = nickname.toString();
			/**
			 * ���õ������������巽��
			 * 1����������������
			 * 2��������Ϣ
			 */
			//�õ���ǰ��¼�û���userId ԭ����Ϊ��ȥ�ͺ��������ʱ���֪����˭��˭����
			Object currentUserId = jsonObject.get("userId");
			addFriendPanel(mainPage.jsp,jsonArray,currentUserId,nickname);
		}else {
			/**
			 * ��ѯʧ�ܣ�����һ����¼���� ���Ұ���ҳ������ ���ҵ�����ʾ������Ϣ
			 */
			JOptionPane.showMessageDialog(null, array[2]);
			PageCacheUtil.mainPage.dispose();
			LoginPage loginPage = new LoginPage();
			loginPage.init(PageCacheUtil.userName);
			PageCacheUtil.loginPage = loginPage;
		}
	}
	/**
	 * �������˷��صĺ͵�¼�����йص������߼�
	 * @param array
	 */
	private void handlerLoginLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//��¼�ɹ�
			//1������¼�������ٵ� ���ǵ�¼���治���ڿͻ��˽�����Ϣ�߳��д�����
			//��¼���洴���ɹ�������¼���滺������
			PageCacheUtil.loginPage.dispose();
			//2������userIdȥ���ǵ���ҳ
			PageCacheUtil.userId = array[3];
			PageCacheUtil.userName = array[4];
			MainPage mainPage = new MainPage();
			//����ҳ����ҳ��������
			PageCacheUtil.mainPage = mainPage;
			mainPage.init(PageCacheUtil.userId);
		}else {
			//��¼ʧ�ܵĵ�����ʾ��Ϣ
			String errMsg = array[2];
			JOptionPane.showMessageDialog(null, errMsg);
		}
	}
	
	/**
	 * ����main����ĵ�����������û������б�
	 * @param currentUserId 
	 * @param friends
	 */
	private void addFriendPanel(JScrollPane jsp,JSONArray jsonArray, Object currentUserId,Object currentNickname) {
		JPanel friends = new JPanel();
		friends.setLayout(null);
		//json����ֻ��һ������ ����û������
		if (jsonArray.size()==1) {
			JOptionPane.showMessageDialog(null, "û���ҵ�����Ŷ����Ӻ�������ɣ�");
			return;
		}
		/**
		 * ��ȡjson����ĵڶ������ݰ����ڶ���֮�������----�����б�����
		 */
		for (int i = 1; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			/**
			 * jp����ÿһ�����ѵ����
			 */
			JPanel jp = new JPanel();
			jp.setLayout(null);
			//ͷ��
			ImageIcon h = new ImageIcon(jsonObject.getString("header"));
			JLabel header = new JLabel(h);
			header.setBounds(10, 10, 80, 80);
			jp.add(header);
			//�ǳ�
			JLabel nn = new JLabel(jsonObject.getString("nickname"));
			nn.setBounds(100, 10, 300, 30);
			jp.add(nn);
			//����ǩ��
			JLabel sign = new JLabel(jsonObject.getString("sign"));
			sign.setBounds(100, 50, 300, 30);
			jp.add(sign);
			jp.setBounds(0,(i-1)*100+5, 500, 100);
			
			Object friendId = jsonObject.get("userId");
			jp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int clickCount = e.getClickCount();
					if (clickCount == 2) {
						//currentUserId   friendId  nickname
						SingelChatPage singelChatPage = new SingelChatPage();
						PageCacheUtil.singelChatPage = singelChatPage;
						singelChatPage.init(currentUserId.toString(),friendId.toString(),jsonObject.get("nickname").toString(),currentNickname.toString());
						System.out.println("˽�Ľ����Ѿ�����");
					}
				}
			});
			jp.setBorder(BorderFactory.createEtchedBorder());
			friends.add(jp);
		}
		friends.setPreferredSize(new Dimension(480,(jsonArray.size()-1)*100));
		jsp.getViewport().add(friends);
	}
}
