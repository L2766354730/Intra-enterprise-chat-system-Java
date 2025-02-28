package com.nuc.ljx.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.nuc.ljx.StarterServer;
import com.nuc.ljx.controller.UserController;
import com.nuc.ljx.model.User;

/**
 * ����߳�����������ÿһ�����ܵ��Ŀͻ�������
 * 
 * @author 11018
 *
 */
public class ServerReceiverMessageThread implements Runnable {
	private Socket socket;
	public Integer userId;

	public ServerReceiverMessageThread(Socket socket) {
		this.socket = socket;
	}

	/**
	 * ԴԴ���ϵĽ��ܿͻ��˸����͵���Ϣ
	 */
	@Override
	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
			String line = null;
			while ((line = br.readLine()) != null) {
				// 1=zs=123456
				System.out.println("�ͻ��˷�����һ����Ϣ��" + line);
				// ����һ������ ����ȥ����ͻ��˷��͵Ĳ�ͬ���͵�����
				executeServerLogic(line);
			}
		} catch (IOException e) {
			/**
			 * ���ͻ���socket����֮��Ż�����쳣
			 */
			if (userId != null) {
				StarterServer.onLineUsers.remove(userId);
			}
		}
	}

	/**
	 * �������ͻ��˶�Ӧ��socket���ܵ��ͻ��˵�����֮�� ����ִ�в�ͬ�߼��ķ���
	 * 
	 * @param line
	 * @throws IOException
	 */
	private void executeServerLogic(String line) throws IOException {
		// line 1=zs=123456 2=zs=123456=hahaha=��
		String[] array = line.split("=");
		String xieyi = array[0];
		switch (xieyi) {
			/**
			 * 0Э��������ע���߼�
			 * 0=nickname=password=sign=sex=headerPath
			 */
			case "0":
				//1���õ�ǰ�˴��ݵ�ע����Ϣ ���
				String nickname = array[1];
				String pass = array[2];
				String sign = array[3];
				String sex = array[4];
				String headerPath = array[5];
				StarterServer.setMonitorPage("��һ���û���ע��ϵͳ���û��ǳ�Ϊ"+nickname);
				//����������Ϣʹ��Userʵ�����װһ��
				User user = User.builder().nickname(nickname).password(pass).sign(sign).sex(sex).header(headerPath).build();
				//2�����ÿ��������ע��ҵ���߼�
				//0=200=�ɹ���Ϣ=���ɵ��˺�
				//0=404=ʧ����Ϣ
				String msg0 = new UserController().register(user);
				sendMessage(msg0);
				break;
			// ��Э��Ϊ1 �������Ǵ�����ǵ�¼����
			case "1":
				String username = array[1];
				String password = array[2];
				// ����controller���ҵ���߼�
				// 1=404=ʧ����Ϣ
				//1=200=�ɹ���Ϣ=userId
				String msg = new UserController().login(username, password);
				String[] msgArray = msg.split("=");
				String code = msgArray[1];
				if (code.equals("200")) {
					//����ǰ�û���id���뵱ǰ�û��ͻ��˽���ͨ�ŵ�socket��������
					Integer userId = Integer.parseInt(msgArray[3]);
					//���û���������
					this.userId = userId;
					//���浽�����û�������
					StarterServer.onLineUsers.put(userId, socket);
					StarterServer.setMonitorPage("��һ���û�������"+userId);
				}
				sendMessage(msg);
				break;
			/**
			 * ��Э��Ϊ2��ʱ����1������� �������userId��ȡ�û��ĸ�����Ϣ�Լ���ȡ�û��ĺ����б���Ϣ 2=userId
			 */
			case "2":
				// �ڶ����ֶ����û�id�ֶ�
				String userId = array[1];
				/**
				 * ���ÿ�������ȥ�����ȡ������Ϣ��ҵ���߼� 
				 * 2=404=ʧ����Ϣ 
				 * 2=200=�ɹ���Ϣ=userJSON
				 */
				String msg1 = new UserController().selectUserAndFriendsByUserId(userId);
				sendMessage(msg1);
				break;
			/**
			 * �û��޸���������
			 * 3=userId=oldpass=newpass
			 */
			case "3":
				//��ȡǰ�˴��ݵ��û�id
				String uid = array[1];
				String oldpass = array[2];
				String newpass = array[3];
				//���ÿ���������޸������ҵ���߼�
				//3=404=ʧ��
				//3=200=�ɹ���Ϣ
				String msg2 = new UserController().updatePassword(uid, oldpass, newpass);
				sendMessage(msg2);
				break;
			/**
			 * ����userid�������ؼ���ȥ���Ҳ�����userid���ѵ��û���Ϣ
			 * 4=userId=searchKeu
			 */
			case "4":
				String id = array[1];
				String searchKey = array[2];
				//���ÿ�����ִ���߼���ѯ
				String msg4 = new UserController().searchUsersByUserIdAndSearchKey(id, searchKey);
				sendMessage(msg4);
				break;
			/**
			 * �û���Ӻ��ѵ�����
			 * 5=userId=friendId
			 */
			case "5":
				String currentUserId = array[1];
				String friendId = array[2];
				//���ÿ�������ɺ��ѵ����
				String msg5 = new UserController().addFriends(currentUserId, friendId);
				sendMessage(msg5);
				break;
			/**
			 * ������ʷ�����¼������
			 * 6=userId=friendId
			 */
			case "6":
				String cuid = array[1];
				String fid = array[2];
				//���ÿ�������ɺ��ѵ����
				//6=200=��ѯ�ɹ�=messagejson
				String msg6 = new UserController().queryChatMessage(cuid,fid);
				sendMessage(msg6);
				break;
			/**
			 * ĳһ���û������ѷ�����һ����Ϣ
			 * 7=userId=friendId=message=currentNickname
			 */
			case "7":
				String cruuentuid = array[1];
				String friendid = array[2];
				String message = array[3];
				String currentNickname = array[4];
				//��������ӵ����ݿ�
				new UserController().addMessage(cruuentuid,friendid,message);
				//�ж�һ���㷢�͵���Ϣ���û��ڲ����ߣ�������� ֱ�Ӹ�������һ����Ϣ��  xxx���㷢����һ����Ϣ �ͼ�ʱ����
				/**
				 * ��������֪��һ���û��ڲ����ߣ���ʵ�ܼ򵥣����û���¼�ɹ�֮�󣬽��û���id�͵�ǰ�û���socketͨ�Ż�������
				 * ����û��Ͽ����ӣ���ô���ڻ����н���ǰ�û��Ƴ�
				 */
				//�ж�һ�·��͵���Ϣ�����ڲ�����
				//�ж�keyֵ��ʱ�� key������Integer���͵�
				boolean flag = StarterServer.onLineUsers.containsKey(Integer.parseInt(friendid));
				System.out.println(flag);
				if (flag) {
					//���socket�����ߺ��ѵ�socket
					Socket socket = StarterServer.onLineUsers.get(Integer.parseInt(friendid));
					//7=zs���㷢����һ����Ϣ���뼰ʱ����
					String msg7 = "7="+currentNickname+"���㷢����һ����Ϣ���뼰ʱ����="+friendid;
					OutputStream outputStream = socket.getOutputStream();
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "GBK"));
					// 3���������
					bw.write(msg7);
					bw.newLine();
					bw.flush();
				}
				break;
				/**
				 * ����userid�������ؼ���ȥ����userid�ĺ��ѵ��û���Ϣ
				 * 8=userId=searchKeu
				 */
				case "8":
					String id1 = array[1];
					String searchKey1 = array[2];
					//���ÿ�����ִ���߼���ѯ
					String msg8 = new UserController().searchFriendsByUserIdAndSearchKey(id1, searchKey1);
					sendMessage(msg8);
					break;
				/**
				 * ��Ⱥ
				 * 9=userId=groupName=groupIntro
				 */
				case "9":
					String uid2 = array[1];
					String groupName = array[2];
					String groupIntro = array[3];
					//���ÿ�����ִ���߼���ѯ
					String msg9 = new UserController().addGroup(uid2, groupName, groupIntro);
					sendMessage(msg9);
					break;
				/**
				 * ��ѯ�������Ⱥ
				 * 10=userId
				 */
				case "10":
					String uid3 = array[1];
					//���ÿ�����ִ���߼���ѯ
					String msg10 = new UserController().selectGroupByUserId(uid3);
					sendMessage(msg10);
					break;
				/**
				 * ��ѯȺ����ʷ��¼
				 * 11=groupId
				 */
				case "11":
					String groupId = array[1];
					//���ÿ�����ִ���߼���ѯ
					String msg11 = new UserController().selectGroupHistoryChat(groupId);
					sendMessage(msg11);
					break;
				/**
				 * ������Ϣ��Ⱥ��
				 * 12=userId=groupId=message=currentNickname
				 */
				case "12":
					String userId1 = array[1];
					String groupId1 = array[2];
					String message1 = array[3];
					new UserController().addGroupMessage(userId1, groupId1, message1);
					break;
				/**
				 * ����userid�������ؼ���ȥ����userid��Ⱥ��Ϣ
				 * 13=userId=searchKeu
				 */
				case "13":
					String id4 = array[1];
					String searchKey2 = array[2];
					//���ÿ�����ִ���߼���ѯ
					String msg13 = new UserController().searchGroupsByUserIdAndSearchKey(id4, searchKey2);
					sendMessage(msg13);
					break;
				/**
				 * ����userid�������ؼ���ȥ����userid���ڵ�Ⱥ��Ϣ
				 * 14=userId=searchKeu
				 */
				case "14":
					String id5 = array[1];
					String searchKey3 = array[2];
					System.err.println("iddc");
					//���ÿ�����ִ���߼���ѯ
					String msg14 = new UserController().searchGroupsByUserIdAndSearchKeyForEntry(id5, searchKey3);
					sendMessage(msg14);
					break;
				/**
				 * �û�����Ⱥ����
				 * 15=userId=groupId
				 */
				case "15":
					String userId3 = array[1];
					String groupId3 = array[2];
					//���ÿ�������ɺ��ѵ����
					String msg15 = new UserController().entryGroups(userId3, groupId3);
					sendMessage(msg15);
					break;
				/**
				 * �û��˳�Ⱥ����
				 * 16=userId=groupId
				 */
				case "16":
					String userId4 = array[1];
					String groupId4 = array[2];
					//���ÿ�������ɺ��ѵ����
					String msg16 = new UserController().exitGroups(userId4, groupId4);
					sendMessage(msg16);
					break;
				/**
				 * ��ѯĳȺȺ��Ա�б�
				 * 17=groupId
				 */
				case "17":
					String groupId5 = array[1];
					//���ÿ�����ִ���߼���ѯ
					String msg17 = new UserController().selectGroupMemberByGroupId(groupId5);
					sendMessage(msg17);
					break;
			default:
				break;
		}

	}

	/**
	 * ����˸��ͻ��˷�����Ϣ����
	 * 
	 * @param msg
	 * @throws IOException
	 */
	public void sendMessage(String msg) throws IOException {
		// 2��׼����������msg
		OutputStream outputStream = socket.getOutputStream();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "GBK"));
		// 3���������
		bw.write(msg);
		bw.newLine();
		bw.flush();
	}
}