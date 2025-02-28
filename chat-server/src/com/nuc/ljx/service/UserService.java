package com.nuc.ljx.service;

import java.sql.Date;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.nuc.ljx.dao.UserDao;
import com.nuc.ljx.model.FriendMessage;
import com.nuc.ljx.model.GroupInfo;
import com.nuc.ljx.model.GroupMember;
import com.nuc.ljx.model.GroupMessage;
import com.nuc.ljx.model.User;

public class UserService {
	private UserDao userDao = new UserDao();
	
	/**
	 * 注册用户的业务逻辑
	 * @param user
	 * @return
	 */
	public String RegisterUser(User user) {
		//1、随机生成一个六位账号
		Random rand = new Random();
		//判断一下账号在数据库有没有使用
		String number = ""+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10);
		User u = userDao.selectUserByUsername(number);
		//如果使用过  重新生成
		while (u != null) {
			number = ""+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10);
			u = userDao.selectUserByUsername(number);
		}
		//没有使用过 把number账号赋值给user对象 这个账号就是注册用户的账号
		user.setUsername(number);
		//2、设置注册用户的创建时间 Date是java.sql.Date
		user.setAddTime(new Date(System.currentTimeMillis()));
		//3、调用dao层增加数据
		int result = userDao.addUserAndFriend(user);
		if (result >0) {
			return "0=200=注册成功="+number;
		}else {
			return "0=404=注册失败";
		}
	}
	
	/**
	 * 登录逻辑执行
	 * @param username
	 * @param password
	 * @return
	 */
	public String loginByUsernameAnaPassoword(String username,String password) {
		User user = userDao.selectUserByUsername(username);
		if (user == null) {
			return "1=404=用户不存在";
		}else {
			if (password.equals(user.getPassword())) {
				return "1=200=登录成功="+user.getUserId()+"="+user.getUsername();
			}else {
				return "1=404=密码不正确";
			}
		}
	}
	
	/**
	 * 根据用户id查询个人信息
	 * @param userId
	 * @return
	 */
	public String selectUserAndFriendsByUserId(int userId) {
		List<User> users = userDao.selectUserAndFriendsByUserId(userId);
		if (users == null || users.isEmpty()) {
			return "2=404=用户不存在";
		}else {
			/**
			 * r如果用户存在 需要给控制器返回
			 * 2=200=成功信息=user的JSON格式数据
			 */
			String userJson = JSON.toJSONString(users,SerializerFeature.WRITE_MAP_NULL_FEATURES);
			return "2=200=查询成功="+userJson;
		}
	}

	/**
	 * 真正修改密码的逻辑
	 * @param userId
	 * @param oldpass
	 * @param newpass
	 * @return
	 */
	public String updatePasswordByUserId(String userId,String oldpass,String newpass) {
		//1、先判断旧密码是否一致
		User user = userDao.selectUserByUserId(Integer.parseInt(userId));
		if (user == null) {
			return "3=404=要修改的用户不存在";
		}else {
			//判断一下用户输入的旧密码和数据库查询回来的旧密码是否一致
			if (oldpass.equals(user.getPassword())) {
				int result = userDao.updatePasswordByUserId(Integer.parseInt(userId), newpass);
				if (result >0) {
					return "3=200=修改成功！";
				}else {
					return "3=404=密码修改失败！";
				}
			}else {
				return "3=404=输入的旧密码不正确！";
			}
		}
	}
	
	
	/**
	 * 根据用户id和关键字查找不是当前用户的其他用户信息
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchUsersByUserIdAndSearchKey(String userId,String searchKey) {
		//2、调用dao层查询数据
		List<User> users = userDao.selectUsersByUserIdAndSearchKey(Integer.parseInt(userId), searchKey);
		/**
		 * 组成成一个数据返回给控制器
		 * 4=200=查询成功=usersJson
		 */
		String msg = "4=200=查询成功="+JSON.toJSONString(users,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}
	
	/**
	 * 给userId添加friendId好友
	 * 并且给friendId添加userId好友
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public String addFriends(String userId,String friendId) {
		//1、调用dao层完成数据添加
		int result = userDao.addFriends(Integer.parseInt(userId), Integer.parseInt(friendId));
		//2、处理返回结果
		if (result >0) {
			return "5=200=添加好友成功";
		}else {
			return "5=404=添加好友失败";
		}
		
	}
	/**
	 * 根据userId和好友id查询聊天记录
	 * @param cuid
	 * @param fid
	 * @return
	 */
	public String queryChatMessage(String cuid, String fid) {
		List<FriendMessage> messages = userDao.selectMessageByUserIdAndFriendId(Integer.parseInt(cuid), Integer.parseInt(fid));
		//6=200=查询成功=messagejson
		String msg = "6=200=查询成功="+JSON.toJSONString(messages,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}

	/**
	 * 增加聊天数据
	 * @param cruuentuid
	 * @param friendid
	 * @param message
	 */
	public void addMessage(String cruuentuid, String friendid, String message) {
		userDao.insertMessage(Integer.parseInt(cruuentuid),Integer.parseInt(friendid),message);
	}
	
	
	/**
	 * 根据用户id和关键字查找当前用户的好友用户信息
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchFriendsByUserIdAndSearchKey(String userId,String searchKey) {
		//2、调用dao层查询数据
		List<User> users = userDao.searchFriendsByUserIdAndSearchKey(Integer.parseInt(userId), searchKey);
		/**
		 * 组成成一个数据返回给控制器
		 * 2=200=查询成功=userJson
		 */
		String msg = "2=200=查询成功="+JSON.toJSONString(users,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}

	/**
	 * 建群
	 * @param userId
	 * @param groupName
	 * @param groupIntro
	 * @return
	 */
	public String addGroup(GroupInfo groupInfo) {
		//1、随机生成一个六位账号
		Random rand = new Random();
		//判断一下群号在数据库有没有使用
		String number = ""+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10);
		GroupInfo g = userDao.selectGroupByGroupName(number);
		System.out.println(groupInfo);
		//如果使用过  重新生成
		while (g != null) {
			number = ""+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10);
			g = userDao.selectGroupByGroupName(number);
		}
		
		//没有使用过 把number账号赋值给groupInfo对象 这个账号就是群的账号
		groupInfo.setGroupNumber(number);
		//2、设置创建时间 Date是java.sql.Date
		groupInfo.setCreateTime(new Date(System.currentTimeMillis()));
		
		//3、调用dao层增加数据
		int result = userDao.addGroup(groupInfo);
		if (result >0) {
			return "9=200=创建成功="+number;
		}else {
			return "9=404=创建失败";
		}
	}
	/**
	 * 查所有加入的群
	 * @param userId
	 * @return
	 */
	public String selectGroupByUserId(int userId) {
		List<GroupInfo> groupInfos = userDao.selectGroupByUserId(userId);
		if (groupInfos == null || groupInfos.isEmpty()) {
			return "10=404=查群为空";
		}else {
			/**
			 * 如果有群 需要给控制器返回
			 * 10=200=成功信息=groupInfos的JSON格式数据
			 */
			String groupInfosJson = JSON.toJSONString(groupInfos,SerializerFeature.WRITE_MAP_NULL_FEATURES);
			return "10=200=查询成功="+groupInfosJson;
		}
	}

	/**
	 * 查某群聊天记录
	 * @param groupId
	 * @return
	 */
	public String selectGroupHistoryChat(String groupId) {
		List<GroupMessage> messages = userDao.selectGroupHistoryChatByGroupId(Integer.parseInt(groupId));
		//11=200=查询成功=messagejson
		String msg = "11=200=查询成功="+JSON.toJSONString(messages,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}

	/**
	 * 发送群消息
	 * @param userId
	 * @param groupId
	 * @param message
	 */
	public void addGroupMessage(String userId, String groupId, String message) {
		userDao.addGroupMessage(Integer.parseInt(userId),Integer.parseInt(groupId),message);
		
	}

	
	/**
	 * 关键字查当前用户所在群
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchGroupsByUserIdAndSearchKey(String userId, String searchKey) {
		//2、调用dao层查询数据
		List<GroupInfo> groupInfos = userDao.searchGroupsByUserIdAndSearchKey(Integer.parseInt(userId), searchKey);
		/**
		 * 组成成一个数据返回给控制器
		 * 10=200=查询成功=groupInfosJson
		 */
		String msg = "10=200=查询成功="+JSON.toJSONString(groupInfos,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}

	/**
	 * 关键字查当前用户不在的群
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchGroupsByUserIdAndSearchKeyForEntry(String userId, String searchKey) {
		//2、调用dao层查询数据
		List<GroupInfo> groupInfos = userDao.searchGroupsByUserIdAndSearchKeyForEntry(Integer.parseInt(userId), searchKey);
		/**
		 * 组成成一个数据返回给控制器
		 * 12=200=查询成功=groupInfosJson
		 */
		System.out.println("service");
		String msg = "12=200=查询成功="+JSON.toJSONString(groupInfos,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}

	/**
	 * 群成员中加入该用户
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public String entryGroups(String userId, String groupId) {
		//1、调用dao层完成数据添加
		int result = userDao.entryGroups(Integer.parseInt(userId), Integer.parseInt(groupId));
		//2、处理返回结果
		if (result >0) {
			return "13=200=进入群成功";
		}else {
			return "13=404=进入群失败";
		}
	}


	/**
	 * 退群，如果是群主，则为解散群
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public String exitGroups(String userId, String groupId) {
		String msg = null;
		Integer groupLeaderId = userDao.selectGroupByGroupId(Integer.parseInt(groupId)).getGroupLeaderId();
		if (Integer.parseInt(userId)==groupLeaderId) {
			//是群主
			int r = userDao.deleteGroup(Integer.parseInt(groupId));
			if (r==1) {
				msg = "14=200=解散群成功";
			}else {
				msg = "14=404=解散群失败";
			}
		} else {
			//不是群主
			int result = userDao.exitGroup(Integer.parseInt(userId), Integer.parseInt(groupId));
			if(result==1) {
				msg = "14=200=退群成功";
			}else {
				msg = "14=404=退群失败";
			}
		}
		
		return msg;
		
	}

	/**
	 * 查询某群群成员列表
	 * @param groupId
	 * @return
	 */
	public String selectGroupMemberByGroupId(String groupId) {
		List<User> groupMembers = userDao.selectGroupMemberByGroupId(Integer.parseInt(groupId));
		if (groupMembers == null || groupMembers.isEmpty()) {
			return "15=404=群成员为空";
		}else {
			/**
			 * 如果有群 需要给控制器返回
			 * 15=200=成功信息=groupMembers的JSON格式数据
			 */
			String groupMembersJson = JSON.toJSONString(groupMembers,SerializerFeature.WRITE_MAP_NULL_FEATURES);
			return "15=200=查询成功="+groupMembersJson;
		}
	}
}
