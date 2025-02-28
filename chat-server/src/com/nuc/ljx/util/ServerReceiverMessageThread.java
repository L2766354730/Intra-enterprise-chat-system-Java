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
 * 这个线程是用来处理每一个接受到的客户端请求
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
	 * 源源不断的接受客户端给发送的消息
	 */
	@Override
	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
			String line = null;
			while ((line = br.readLine()) != null) {
				// 1=zs=123456
				System.out.println("客户端发送了一个消息：" + line);
				// 调用一个方法 用来去处理客户端发送的不同类型的请求
				executeServerLogic(line);
			}
		} catch (IOException e) {
			/**
			 * 当客户端socket掉线之后才会出现异常
			 */
			if (userId != null) {
				StarterServer.onLineUsers.remove(userId);
			}
		}
	}

	/**
	 * 服务端与客户端对应的socket接受到客户端的数据之后 用来执行不同逻辑的方法
	 * 
	 * @param line
	 * @throws IOException
	 */
	private void executeServerLogic(String line) throws IOException {
		// line 1=zs=123456 2=zs=123456=hahaha=男
		String[] array = line.split("=");
		String xieyi = array[0];
		switch (xieyi) {
			/**
			 * 0协议代表的是注册逻辑
			 * 0=nickname=password=sign=sex=headerPath
			 */
			case "0":
				//1、拿到前端传递的注册信息 五个
				String nickname = array[1];
				String pass = array[2];
				String sign = array[3];
				String sex = array[4];
				String headerPath = array[5];
				StarterServer.setMonitorPage("有一个用户在注册系统，用户昵称为"+nickname);
				//将这个五个信息使用User实体类包装一下
				User user = User.builder().nickname(nickname).password(pass).sign(sign).sex(sex).header(headerPath).build();
				//2、调用控制器完成注册业务逻辑
				//0=200=成功信息=生成的账号
				//0=404=失败信息
				String msg0 = new UserController().register(user);
				sendMessage(msg0);
				break;
			// 当协议为1 代表我们处理的是登录请求
			case "1":
				String username = array[1];
				String password = array[2];
				// 调用controller完成业务逻辑
				// 1=404=失败信息
				//1=200=成功信息=userId
				String msg = new UserController().login(username, password);
				String[] msgArray = msg.split("=");
				String code = msgArray[1];
				if (code.equals("200")) {
					//将当前用户的id和与当前用户客户端进行通信的socket缓存起来
					Integer userId = Integer.parseInt(msgArray[3]);
					//将用户缓存起来
					this.userId = userId;
					//缓存到在线用户集合中
					StarterServer.onLineUsers.put(userId, socket);
					StarterServer.setMonitorPage("有一个用户上线了"+userId);
				}
				sendMessage(msg);
				break;
			/**
			 * 当协议为2的时候，有1种情况， 代表根据userId获取用户的个人信息以及获取用户的好友列表信息 2=userId
			 */
			case "2":
				// 第二个字段是用户id字段
				String userId = array[1];
				/**
				 * 调用控制器层去处理获取个人信息的业务逻辑 
				 * 2=404=失败信息 
				 * 2=200=成功信息=userJSON
				 */
				String msg1 = new UserController().selectUserAndFriendsByUserId(userId);
				sendMessage(msg1);
				break;
			/**
			 * 用户修改密码请求
			 * 3=userId=oldpass=newpass
			 */
			case "3":
				//获取前端传递的用户id
				String uid = array[1];
				String oldpass = array[2];
				String newpass = array[3];
				//调用控制器完成修改密码的业务逻辑
				//3=404=失败
				//3=200=成功信息
				String msg2 = new UserController().updatePassword(uid, oldpass, newpass);
				sendMessage(msg2);
				break;
			/**
			 * 根据userid和搜索关键字去查找不属于userid好友的用户信息
			 * 4=userId=searchKeu
			 */
			case "4":
				String id = array[1];
				String searchKey = array[2];
				//调用控制器执行逻辑查询
				String msg4 = new UserController().searchUsersByUserIdAndSearchKey(id, searchKey);
				sendMessage(msg4);
				break;
			/**
			 * 用户添加好友的请求
			 * 5=userId=friendId
			 */
			case "5":
				String currentUserId = array[1];
				String friendId = array[2];
				//调用控制器完成好友的添加
				String msg5 = new UserController().addFriends(currentUserId, friendId);
				sendMessage(msg5);
				break;
			/**
			 * 查找历史聊天记录的请求
			 * 6=userId=friendId
			 */
			case "6":
				String cuid = array[1];
				String fid = array[2];
				//调用控制器完成好友的添加
				//6=200=查询成功=messagejson
				String msg6 = new UserController().queryChatMessage(cuid,fid);
				sendMessage(msg6);
				break;
			/**
			 * 某一个用户给好友发送了一个消息
			 * 7=userId=friendId=message=currentNickname
			 */
			case "7":
				String cruuentuid = array[1];
				String friendid = array[2];
				String message = array[3];
				String currentNickname = array[4];
				//把数据添加到数据库
				new UserController().addMessage(cruuentuid,friendid,message);
				//判断一下你发送的消息的用户在不在线，如果在线 直接给他推送一个消息，  xxx给你发送了一个消息 就及时查收
				/**
				 * 服务端如何知道一个用户在不在线，其实很简单，等用户登录成功之后，将用户的id和当前用户的socket通信缓存起来
				 * 如果用户断开连接，那么就在缓存中将当前用户移除
				 */
				//判断一下发送的信息好友在不在线
				//判断key值的时候 key必须是Integer类型的
				boolean flag = StarterServer.onLineUsers.containsKey(Integer.parseInt(friendid));
				System.out.println(flag);
				if (flag) {
					//这个socket是在线好友的socket
					Socket socket = StarterServer.onLineUsers.get(Integer.parseInt(friendid));
					//7=zs给你发送了一条消息，请及时查收
					String msg7 = "7="+currentNickname+"给你发送了一条消息，请及时查收="+friendid;
					OutputStream outputStream = socket.getOutputStream();
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "GBK"));
					// 3、输出数据
					bw.write(msg7);
					bw.newLine();
					bw.flush();
				}
				break;
				/**
				 * 根据userid和搜索关键字去查找userid的好友的用户信息
				 * 8=userId=searchKeu
				 */
				case "8":
					String id1 = array[1];
					String searchKey1 = array[2];
					//调用控制器执行逻辑查询
					String msg8 = new UserController().searchFriendsByUserIdAndSearchKey(id1, searchKey1);
					sendMessage(msg8);
					break;
				/**
				 * 建群
				 * 9=userId=groupName=groupIntro
				 */
				case "9":
					String uid2 = array[1];
					String groupName = array[2];
					String groupIntro = array[3];
					//调用控制器执行逻辑查询
					String msg9 = new UserController().addGroup(uid2, groupName, groupIntro);
					sendMessage(msg9);
					break;
				/**
				 * 查询所加入的群
				 * 10=userId
				 */
				case "10":
					String uid3 = array[1];
					//调用控制器执行逻辑查询
					String msg10 = new UserController().selectGroupByUserId(uid3);
					sendMessage(msg10);
					break;
				/**
				 * 查询群聊历史记录
				 * 11=groupId
				 */
				case "11":
					String groupId = array[1];
					//调用控制器执行逻辑查询
					String msg11 = new UserController().selectGroupHistoryChat(groupId);
					sendMessage(msg11);
					break;
				/**
				 * 发送消息到群聊
				 * 12=userId=groupId=message=currentNickname
				 */
				case "12":
					String userId1 = array[1];
					String groupId1 = array[2];
					String message1 = array[3];
					new UserController().addGroupMessage(userId1, groupId1, message1);
					break;
				/**
				 * 根据userid和搜索关键字去查找userid的群信息
				 * 13=userId=searchKeu
				 */
				case "13":
					String id4 = array[1];
					String searchKey2 = array[2];
					//调用控制器执行逻辑查询
					String msg13 = new UserController().searchGroupsByUserIdAndSearchKey(id4, searchKey2);
					sendMessage(msg13);
					break;
				/**
				 * 根据userid和搜索关键字去查找userid不在的群信息
				 * 14=userId=searchKeu
				 */
				case "14":
					String id5 = array[1];
					String searchKey3 = array[2];
					System.err.println("iddc");
					//调用控制器执行逻辑查询
					String msg14 = new UserController().searchGroupsByUserIdAndSearchKeyForEntry(id5, searchKey3);
					sendMessage(msg14);
					break;
				/**
				 * 用户加入群请求
				 * 15=userId=groupId
				 */
				case "15":
					String userId3 = array[1];
					String groupId3 = array[2];
					//调用控制器完成好友的添加
					String msg15 = new UserController().entryGroups(userId3, groupId3);
					sendMessage(msg15);
					break;
				/**
				 * 用户退出群请求
				 * 16=userId=groupId
				 */
				case "16":
					String userId4 = array[1];
					String groupId4 = array[2];
					//调用控制器完成好友的添加
					String msg16 = new UserController().exitGroups(userId4, groupId4);
					sendMessage(msg16);
					break;
				/**
				 * 查询某群群成员列表
				 * 17=groupId
				 */
				case "17":
					String groupId5 = array[1];
					//调用控制器执行逻辑查询
					String msg17 = new UserController().selectGroupMemberByGroupId(groupId5);
					sendMessage(msg17);
					break;
			default:
				break;
		}

	}

	/**
	 * 服务端给客户端发送消息方法
	 * 
	 * @param msg
	 * @throws IOException
	 */
	public void sendMessage(String msg) throws IOException {
		// 2、准备输出流输出msg
		OutputStream outputStream = socket.getOutputStream();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "GBK"));
		// 3、输出数据
		bw.write(msg);
		bw.newLine();
		bw.flush();
	}
}