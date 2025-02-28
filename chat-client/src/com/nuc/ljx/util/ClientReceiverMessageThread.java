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
 * 客户端接受消息的线程类
 * @author 11018
 *
 */
public class ClientReceiverMessageThread implements Runnable{
	/**
	 * 线程是用来源源不断的接受服务端消息
	 */
	@Override
	public void run() {
		Socket socket = StartClient.socket;
		try {
			InputStream inputStream = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"gbk"));
			String line = null;
			while((line = br.readLine()) != null) {
				System.out.println("服务端回应了一个消息："+line);
				//校验服务端给我返回的数据
				exuecuteClientLogic(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 接受到服务端返回的处理结果，根据不同的处理结果 完成不同的业务逻辑
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
			 * 如果接受到的服务端返回的消息以1开头的 代表的是登录校验的信息
			 * 1=200=成功信息=userid
			 * 1=404=失败信息
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
				// "7="+friendNickname+"给你发送了一条消息，请及时查收"+friendid;
				JOptionPane.showMessageDialog(null, array[1]);
//				String friendNickname = array[1];
//				String friendId = array[3];
//				PageCacheUtil.singelChatPage.init(PageCacheUtil.userId,friendId.toString(),friendNickname,PageCacheUtil.userNickname);
//				
				break;
			case "8":
				JOptionPane.showMessageDialog(null, "系统发布公告："+array[1]);
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
	 * 群成员页面渲染
	 * "15=200=查询成功="+groupMembersJson
	 * "15=404=群成员为空"
	 * @param array
	 */
	private void handlerGroupMemberLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//从页面缓存中获取到当前创建的主界面
			GroupMemberPage groupMemberPage = PageCacheUtil.groupMemberPage;
			JPanel members = new JPanel();
			members.setLayout(null);
			System.out.println(array[3]);
			/**
			 * groupInfoJson是一个json数组---Java集合 存放了零个或者多个群信息
			 */
			String userJSON = array[3];
			//将一个json字符串转成一个json数组
			JSONArray jsonArray = JSON.parseArray(userJSON);
			/**
			 * 获取json数组数据----群列表数据
			 */
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				System.out.println(i+"======="+jsonObject);
				/**
				 * jp就是每一个群的面板
				 */
				JPanel jp = new JPanel();
				jp.setLayout(null);
				//头像
				ImageIcon h = new ImageIcon(jsonObject.getString("header"));
				JLabel header = new JLabel(h);
				header.setBounds(10, 10, 80, 80);
				jp.add(header);
				//昵称
				JLabel nickname = new JLabel(jsonObject.getString("nickname"));
				nickname.setBounds(100, 10, 300, 30);
				jp.add(nickname);
				//个性签名
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
			 * 查询失败,没有群，弹窗提示
			 */
			JOptionPane.showMessageDialog(null, "没有群成员！");
		}
		
	}
	/**
	 * 删除群接收器逻辑
	 * 14=200=退群成功
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
			//需要发送10的协议 把群列表的数据也同步更新一下
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
	 * 搜索要加入的群逻辑
	 * @param array
	 */
	private void handlerSearchGroupLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			System.out.println(array[3]);
			//是一个json数组格式的数据，包含着我们查询回来的多条匹配数据
			String userJson = array[3];
			//json数组，json数组中放的就是查询回来的一条条用户信息
			JSONArray jsonArray = JSON.parseArray(userJson);
			if (jsonArray.isEmpty()) {
				//如果没有找到匹配信息 除了弹窗提示之外，把表格设置为一个空表格
				JOptionPane.showMessageDialog(null, "没有找到匹配的群信息");
				Object[] columnNames = {"群聊id","群聊名称","群聊账号","群主名称","群聊简介"};
				Object[][] rows =  new Object[0][0];
				PageCacheUtil.entryGroupPage.jt = new JTable(rows,columnNames);
				PageCacheUtil.entryGroupPage.jsp.getViewport().add(PageCacheUtil.entryGroupPage.jt);
			}else {
				/**
				 * 将json数组中的数据创建成为JTable表格，然后表格加入AddFriendPage界面中的JScollpane中
				 * Jtable需要两个数据
				 *   1、Object[]  columnNames
				 *   2、Object[][]  rows
				 */
				Object[] columnNames = {"群聊id","群聊名称","群聊账号","群主名称","群聊简介"};
				Object[][]  rows = new Object[jsonArray.size()][5];
				//将json数组中的每一个jsonObject数据转换成为Object类型的数组
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
				//先给这个表格增加一个点击事件---进入表格中的某一个群
				PageCacheUtil.entryGroupPage.entryGroup();
				//需要把创建好的jtable放入到滚动面板中
				PageCacheUtil.entryGroupPage.jsp.getViewport().add(PageCacheUtil.entryGroupPage.jt);
			}
		}else {
			JOptionPane.showMessageDialog(null, "查询失败");
		}
	}
	
	/**
	 * 加入群逻辑
	 * 13=200=进入该群成功
	 * 13=404=进入群失败
	 * @param array
	 */
	private void handlerEntryGroupLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//弹窗提示添加成功
			JOptionPane.showMessageDialog(null, array[2]);
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//需要将进入群界面的搜索结果同步更新一下 向服务端发送一个14=userId=searchKey
			String msg = "14="+PageCacheUtil.entryGroupPage.userId+"="+PageCacheUtil.entryGroupPage.searchKey;
			System.out.println("进入群成功"+msg);
			try {
				SendMessageUtil.sendMessage(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//需要发送10的协议 把群列表的数据也同步更新一下
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
	 * 查询群聊历史记录
	 * @param array
	 */
	private void handlerGroupHistoryChatLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//获取所有聊天记录
			String messageJson = array[3];
			System.out.println(messageJson);
			JSONArray jsonArray = JSON.parseArray(messageJson);
			//json数组不为空 有聊天记录
			JTextArea jta = new JTextArea();
			jta.setFont(new Font("微软雅黑", Font.BOLD, 16));
			jta.setEnabled(false);//禁用文本域
			jta.setLineWrap(true);//设置文本域自动换行
			if (!jsonArray.isEmpty()) {
				for (int i = 0; i < jsonArray.size(); i++) {
					//json对象就代表一条聊天记录
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Object sender = jsonObject.get("sendMemberName");//聊天数据的发送者
					Object message = jsonObject.get("message");//聊天数据
					System.out.println(sender);
					System.out.println(message);
					//把发送者和消息拼接到私聊界面的历史聊天记录区域jta
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
	 * 初始化群聊列表
	 * 10=200=查询成功=groupInfoJson
	 * 10=404=没有群
	 * @param array
	 */
	private void handlerGroupListsLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//从页面缓存中获取到当前创建的主界面
			GroupChatListPage groupChatListPage = PageCacheUtil.groupChatListPage;
			JPanel groups = new JPanel();
			groups.setLayout(null);
			System.out.println(array[3]);
			/**
			 * groupInfoJson是一个json数组---Java集合 存放了零个或者多个群信息
			 */
			String userJSON = array[3];
			//将一个json字符串转成一个json数组
			JSONArray jsonArray = JSON.parseArray(userJSON);
			/**
			 * 获取json数组数据----群列表数据
			 */
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				System.out.println(i+"======="+jsonObject);
				/**
				 * jp就是每一个群的面板
				 */
				JPanel jp = new JPanel();
				jp.setLayout(null);
				String groupname = jsonObject.get("groupName").toString()+"("+jsonObject.get("groupNumber").toString()+")";
				//头像
				ImageIcon h = new ImageIcon("images/group.png");
				JLabel header = new JLabel(h);
				header.setBounds(10, 10, 80, 80);
				jp.add(header);
				//群名
				JLabel gn = new JLabel(groupname);
				gn.setBounds(100, 10, 300, 30);
				jp.add(gn);
				//群简介
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
							System.out.println("群聊界面已经缓存");
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
			 * 查询失败,没有群，弹窗提示
			 */
			GroupChatListPage groupChatListPage = PageCacheUtil.groupChatListPage;
			JPanel groups = new JPanel();
			JOptionPane.showMessageDialog(null, "没有找到群聊哦，快加群聊天吧！");
			groupChatListPage.jsp.getViewport().add(groups);
		}
		
	}
	/**
	 * 客户端接收创建群结果处理
	 * 9=200=创建成功=number群号
	 * 9=404=创建失败
	 * @param array
	 */
	private void handlerAddGroup(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//弹窗提示添加成功
			JOptionPane.showMessageDialog(null, array[2]+"群号为"+array[3]);
			PageCacheUtil.agpAddGroupPage.dispose();
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//需要将群聊列表界面的结果同步更新一下 向服务端发送一个10=userId
			String msg = "10="+PageCacheUtil.userId;
			System.out.println("建群成功"+msg);
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
	 * 客户端用来接受服务端查询回来的用户与某个好友之间的历史聊天记录
	 * 6=200=查询成功=messagejson
	 * @param array
	 */
	private void handlerHistoryMessageLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//获取用户和好友的所有聊天记录
			String messageJson = array[3];
			System.out.println(messageJson);
			JSONArray jsonArray = JSON.parseArray(messageJson);
			//json数组不为空 有聊天记录
			JTextArea jta = new JTextArea();
			jta.setFont(new Font("微软雅黑", Font.BOLD, 16));
			jta.setEnabled(false);//禁用文本域
			jta.setLineWrap(true);//设置文本域自动换行
			if (!jsonArray.isEmpty()) {
				for (int i = 0; i < jsonArray.size(); i++) {
					//json对象就代表一条聊天记录
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Object sender = jsonObject.get("senderNickName");//聊天数据的发送者
					Object message = jsonObject.get("message");//聊天数据
					System.out.println(sender);
					System.out.println(message);
					//把发送者和消息拼接到私聊界面的历史聊天记录区域jta
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
	 * 客户端用来处理服务端发送的增加好友的一个信息
	 * 5=200=成功
	 * 5=404=失败
	 * @param array
	 */
	private void handlerAddFriendLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//弹窗提示添加成功
			JOptionPane.showMessageDialog(null, array[2]);
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//需要将添加好友界面的搜索结果同步更新一下 向服务端发送一个4=userId=searchKey
			String msg = "4="+PageCacheUtil.addFriendPage.userId+"="+PageCacheUtil.addFriendPage.searchKey;
			System.out.println("添加好友成功"+msg);
			try {
				SendMessageUtil.sendMessage(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//需要发送2的协议 把首页的数据也同步更新一下
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
	 * 处理服务端返回的添加好友界面搜索好友的数据
	 * 4=200=查询成功=userjSON
	 * @param array
	 */
	private void handlerSearchLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			System.out.println(array[3]);
			//是一个json数组格式的数据，包含着我们查询回来的多条匹配数据
			String userJson = array[3];
			//json数组，json数组中放的就是查询回来的一条条用户信息
			JSONArray jsonArray = JSON.parseArray(userJson);
			if (jsonArray.isEmpty()) {
				//如果没有找到匹配信息 除了弹窗提示之外，把表格设置为一个空表格
				JOptionPane.showMessageDialog(null, "没有找到匹配的用户信息");
				Object[] columnNames = {"用户id","用户昵称","用户账号","用户性别","用户个性签名"};
				Object[][] rows =  new Object[0][0];
				PageCacheUtil.addFriendPage.jt = new JTable(rows,columnNames);
				PageCacheUtil.addFriendPage.jsp.getViewport().add(PageCacheUtil.addFriendPage.jt);
			}else {
				/**
				 * 将json数组中的数据创建成为JTable表格，然后表格加入AddFriendPage界面中的JScollpane中
				 * Jtable需要两个数据
				 *   1、Object[]  columnNames
				 *   2、Object[][]  rows
				 */
				Object[] columnNames = {"用户id","用户昵称","用户账号","用户性别","用户个性签名"};
				Object[][]  rows = new Object[jsonArray.size()][5];
				//将json数组中的每一个jsonObject数据转换成为Object类型的数组
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
				//先给这个表格增加一个点击事件---添加表格中的某一个用户为好友
				PageCacheUtil.addFriendPage.addFriend();
				//需要把创建好的jtable放入到滚动面板中
				PageCacheUtil.addFriendPage.jsp.getViewport().add(PageCacheUtil.addFriendPage.jt);
			}
		}else {
			JOptionPane.showMessageDialog(null, "查询失败");
		}
	}
	/**
	 * 客户端接受到服务端注册请求之后的返回信息
	 * 0=200=注册成功=number
	 * 0=404=注册失败
	 * @param array
	 */
	private void handlerRegisterLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//1、弹窗提示注册成功
			JOptionPane.showMessageDialog(null, array[2]+",你的账号为"+array[3]+",请保存好账号用于登录系统");
			PageCacheUtil.userName = array[3];
			//2、销毁注册界面  创建登录界面
			PageCacheUtil.rp.dispose();
			LoginPage loginPage = new LoginPage();
			PageCacheUtil.loginPage = loginPage;
			loginPage.init(array[3]);
		}else {
			JOptionPane.showMessageDialog(null, array[2]);
		}
		
	}
	/**
	 * 客户端接受到服务端修改密码请求之后的返回信息  处理返回信息
	 * 3=200=成功信息
	 * 3=404=失败信息
	 * @param array
	 */
	private void handlerUpdatePasswordLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//如果成功
			//弹窗提示修改成功
			JOptionPane.showMessageDialog(null, array[2]+"请重新登录！");
			//销毁修改密码界面
			PageCacheUtil.mpp.dispose();
			PageCacheUtil.mainPage.dispose();
			LoginPage loginPage = new LoginPage();
			System.out.println(PageCacheUtil.userName);
			loginPage.init(PageCacheUtil.userName);
			PageCacheUtil.loginPage = loginPage;
		}else {
			//密码修改失败
			JOptionPane.showMessageDialog(null, array[2]);
		}
		
	}
	/**
	 * 处理服务端返回的和首页数据查询渲染有关的信息
	 * 2=200=查询成功=userJson
	 * 2=404=查询失败
	 * @param array
	 */
	private void handlerMainLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			System.out.println(array[3]);
			/**
			 * userJson是一个json数组---Java集合 存放了一个或者多个用户信息
			 * 其中第一个信息是当前用户信息
			 * 第二条数据开始就变成我们用户的好友列表信息
			 */
			String userJSON = array[3];
			//将一个json字符串转成一个json数组
			JSONArray jsonArray = JSON.parseArray(userJSON);
			//获取json数组中的第一条数据未json对象
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			/**
			 * userJson数据中的用户昵称 个人签名  头像三个元素的值赋值给我们的MainPage界面的
			 * 头像、个性签名、昵称
			 */
			Object header = jsonObject.get("header");
			Object nickname = jsonObject.get("nickname");
			Object sign = jsonObject.get("sign");
			//从页面缓存中获取到当前创建的主界面
			MainPage mainPage = PageCacheUtil.mainPage;
			//将主界面中的第一块区域的头像的图片、昵称的文本、签名的文本重新设置
			ImageIcon h = new ImageIcon(header.toString());
			mainPage.header.setIcon(h);
			mainPage.nn.setText(nickname.toString());
			mainPage.sign.setText(sign.toString());
			mainPage.setTitle(nickname.toString()+"的主界面");
			PageCacheUtil.userNickname = nickname.toString();
			/**
			 * 设置第三块区域的面板方法
			 * 1、第三块区域的面板
			 * 2、好友信息
			 */
			//拿到当前登录用户的userId 原因是为了去和好友聊天的时候的知道是谁和谁在聊
			Object currentUserId = jsonObject.get("userId");
			addFriendPanel(mainPage.jsp,jsonArray,currentUserId,nickname);
		}else {
			/**
			 * 查询失败，创建一个登录界面 并且把首页给销毁 并且弹窗提示错误信息
			 */
			JOptionPane.showMessageDialog(null, array[2]);
			PageCacheUtil.mainPage.dispose();
			LoginPage loginPage = new LoginPage();
			loginPage.init(PageCacheUtil.userName);
			PageCacheUtil.loginPage = loginPage;
		}
	}
	/**
	 * 处理服务端返回的和登录功能有关的数据逻辑
	 * @param array
	 */
	private void handlerLoginLogic(String[] array) {
		String code = array[1];
		if (code.equals("200")) {
			//登录成功
			//1、将登录界面销毁掉 但是登录界面不是在客户端接受消息线程中创建的
			//登录界面创建成功，将登录界面缓存起来
			PageCacheUtil.loginPage.dispose();
			//2、带着userId去我们的首页
			PageCacheUtil.userId = array[3];
			PageCacheUtil.userName = array[4];
			MainPage mainPage = new MainPage();
			//将首页对象页缓存起来
			PageCacheUtil.mainPage = mainPage;
			mainPage.init(PageCacheUtil.userId);
		}else {
			//登录失败的弹窗提示消息
			String errMsg = array[2];
			JOptionPane.showMessageDialog(null, errMsg);
		}
	}
	
	/**
	 * 设置main界面的第三块区域的用户好友列表
	 * @param currentUserId 
	 * @param friends
	 */
	private void addFriendPanel(JScrollPane jsp,JSONArray jsonArray, Object currentUserId,Object currentNickname) {
		JPanel friends = new JPanel();
		friends.setLayout(null);
		//json数组只有一条数据 代表没有朋友
		if (jsonArray.size()==1) {
			JOptionPane.showMessageDialog(null, "没有找到朋友哦，快加好友聊天吧！");
			return;
		}
		/**
		 * 获取json数组的第二条数据包括第二条之后的数据----好友列表数据
		 */
		for (int i = 1; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			/**
			 * jp就是每一个好友的面板
			 */
			JPanel jp = new JPanel();
			jp.setLayout(null);
			//头像
			ImageIcon h = new ImageIcon(jsonObject.getString("header"));
			JLabel header = new JLabel(h);
			header.setBounds(10, 10, 80, 80);
			jp.add(header);
			//昵称
			JLabel nn = new JLabel(jsonObject.getString("nickname"));
			nn.setBounds(100, 10, 300, 30);
			jp.add(nn);
			//个性签名
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
						System.out.println("私聊界面已经缓存");
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
