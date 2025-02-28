package com.nuc.ljx.controller;

import com.nuc.ljx.model.GroupInfo;
import com.nuc.ljx.model.User;
import com.nuc.ljx.service.UserService;

/**
 * 处理和用户表有关的逻辑
 * @author 11018
 *
 */
public class UserController {
	private UserService userService = new UserService();
	/**
	 * 用户名 密码  登录判断
	 * @param username
	 * @param password
	 * @return  返回的数据最后是给客户端的
	 * 返回的数据是客户端登录需要的数据
	 * 1=404=失败原因
	 * 1=200=成功信息=用户id
	 */
	public String login(String username,String password) {
		//1、校验数据的准确性
		if (username == null || username.equals("")) {
			return "1=404=用户名未输入";
		}
		if (password == null || password.equals("")) {
			return "1=404=密码未输入";
		}
		//2、调用service去判断登录业务逻辑
		String result = userService.loginByUsernameAnaPassoword(username, password);
		return result;
	}
	/**
	 * 根据用户id去查询用户的好友列表信息以及当前用户信息
	 * @param userId
	 * @return
	 * 返回信息数据
	 * 2=404=失败信息
	 * 2=200=成功信息=查询回来的userJson数据
	 */
	public String selectUserAndFriendsByUserId(String userId) {
		if (userId==null || userId.equals("")) {
			return "2=404=用户编号未传递";
		}
		
		String msg = userService.selectUserAndFriendsByUserId(Integer.parseInt(userId));
		return msg;
	}

	/**
	 * 根据用户id去修改用户密码
	 * @param userId
	 * @param oldpass
	 * @param newpass
	 * @return
	 * 3=404=失败
	 * 3=200=成功信息
	 */
	public String updatePassword(String userId,String oldpass,String newpass) {
		if (userId == null || userId.equals("")) {
			return "3=404=用户Id未传递";
		}
		if (oldpass.equals(newpass)) {
			return "3=404=新旧密码一致";
		}
		
		//调用service完成业务逻辑
		String msg = userService.updatePasswordByUserId(userId, oldpass, newpass);
		return msg;
	}

	/**
	 * 根据客户端传递的五个信息去注册用户
	 * @param user
	 * @return
	 */
	public String register(User user) {
		//1、数据校验 前端传递的每一个数据都得校验 非空校验 空字符串校验
		if (user == null) {
			return "0=404=用户信息为空";
		}
		//2、调用service完成注册逻辑
		//0=404=注册失败
		//0=200=注册成功=number
		String msg = userService.RegisterUser(user);
		return msg;
	}
	
	/**
	 * 根据用户id和搜索关键字搜索用户信息
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchUsersByUserIdAndSearchKey(String userId,String searchKey) {
		//1、校验数据
		if (userId == null || userId.equals("")) {
			return "4=404=用户Id未传递";
		}
		//2、调用service层实现查询逻辑
		String msg = userService.searchUsersByUserIdAndSearchKey(userId, searchKey);
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
		//1、校验数据
		if (userId == null || userId.equals("")) {
			return "5=404=用户Id未传递";
		}
		if (friendId == null || friendId.equals("")) {
			return "5=404=朋友Id未传递";
		}
		//2、调用service完成数据的添加
		String msg = userService.addFriends(userId, friendId);
		return msg;
	}
	/**
	 * 根据用户id和好友id查询两者之间的聊天记录
	 * @param cuid
	 * @param fid
	 * @return
	 */
	public String queryChatMessage(String cuid, String fid) {
		//1、校验数据
		if (cuid == null || cuid.equals("")) {
			return "6=404=用户Id未传递";
		}
		if (fid == null || fid.equals("")) {
			return "6=404=朋友Id未传递";
		}
		//2、调用service完成信息的查找
		String msg = userService.queryChatMessage(cuid,fid);
		return msg;
	}
	/**
	 * 添加聊天数据
	 * @param cruuentuid
	 * @param friendid
	 * @param message
	 */
	public void addMessage(String cruuentuid, String friendid, String message) {
		userService.addMessage(cruuentuid,friendid,message);
	}
	
	/**
	 * 根据用户id和搜索关键字搜索好友信息
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchFriendsByUserIdAndSearchKey(String userId,String searchKey) {
		//1、校验数据
		if (userId == null || userId.equals("")) {
			return "2=404=用户Id未传递";
		}
		//2、调用service层实现查询逻辑
		String msg = userService.searchFriendsByUserIdAndSearchKey(userId, searchKey);
		return msg;
	}
	/**
	 * 建群
	 * @param uid2
	 * @param groupName
	 * @param groupIntro
	 * @return
	 */
	public String addGroup(String userId, String groupName, String groupIntro) {
		//1、数据校验 前端传递的每一个数据都得校验 非空校验 空字符串校验
		if (userId == null || userId.equals("")) {
			return "9=404=用户Id未传递";
		}
		if (groupName == null || groupName.equals("")) {
			return "9=404=群名未传递";
		}
		if (groupIntro == null || groupIntro.equals("")) {
			return "9=404=群介绍未传递";
		}
		//2、调用service完成逻辑
		//0=404=建群失败
		//0=200=建群成功=groupNumber
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setGroupLeaderId(Integer.parseInt(userId));
		groupInfo.setGroupName(groupName);
		groupInfo.setGroupIntro(groupIntro);
		String msg = userService.addGroup(groupInfo);
		return msg;
	}
	
	/**
	 * 用户id查群
	 * @param uid3
	 * @return
	 */
	public String selectGroupByUserId(String uid3) {
		//1、数据校验 前端传递的每一个数据都得校验 非空校验 空字符串校验
		if (uid3==null || uid3.equals("")) {
			return "10=404=用户编号未传递";
		}
		
		String msg = userService.selectGroupByUserId(Integer.parseInt(uid3));
		return msg;
	}
	public String selectGroupHistoryChat(String groupId) {
		//1、校验数据
		if (groupId == null || groupId.equals("")) {
			return "11=404=群组Id未传递";
		}
		//2、调用service完成信息的查找
		String msg = userService.selectGroupHistoryChat(groupId);
		return msg;
	}
	
	/**
	 * 发送消息到群聊
	 * @param userId1
	 * @param groupId1
	 * @param message1
	 * @return
	 */
	public void addGroupMessage(String userId, String groupId, String message) {
		userService.addGroupMessage(userId,groupId,message);
	}
	
	/**
	 * 关键字查当前用户所在群
	 * @param id4
	 * @param searchKey2
	 * @return
	 */
	public String searchGroupsByUserIdAndSearchKey(String userId, String searchKey) {
		//1、校验数据
		if (userId == null || userId.equals("")) {
			return "10=404=用户Id未传递";
		}
		//2、调用service层实现查询逻辑
		String msg = userService.searchGroupsByUserIdAndSearchKey(userId, searchKey);
		return msg;
	}
	
	/**
	 * 关键字查当前用户不在群
	 * @param id5
	 * @param searchKey3
	 * @return
	 */
	public String searchGroupsByUserIdAndSearchKeyForEntry(String userId, String searchKey) {
		//1、校验数据
		if (userId == null || userId.equals("")) {
			return "12=404=用户Id未传递";
		}
		//2、调用service层实现查询逻辑
		System.out.println("contro");
		String msg = userService.searchGroupsByUserIdAndSearchKeyForEntry(userId, searchKey);
		return msg;
	}
	
	/**
	 * 加入群
	 * 群成员表中加入该用户
	 * @param userId3
	 * @param groupId3
	 * @return
	 */
	public String entryGroups(String userId, String groupId) {
		//1、校验数据
		if (userId == null || userId.equals("")) {
			return "13=404=用户Id未传递";
		}
		if (groupId == null || groupId.equals("")) {
			return "13=404=群Id未传递";
		}
		//2、调用service完成数据的添加
		String msg = userService.entryGroups(userId, groupId);
		return msg;
	}
	
	/**
	 * 退群
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public String exitGroups(String userId, String groupId) {
		//1、校验数据
		if (userId == null || userId.equals("")) {
			return "14=404=用户Id未传递";
		}
		if (groupId == null || groupId.equals("")) {
			return "14=404=群Id未传递";
		}
		//2、调用service完成数据的添加
		String msg = userService.exitGroups(userId, groupId);
		return msg;
	}
	
	/**
	 * 查询某群群成员列表
	 * @param groupId
	 * @return
	 */
	public String selectGroupMemberByGroupId(String groupId) {
		//1、数据校验 前端传递的每一个数据都得校验 非空校验 空字符串校验
		
		if (groupId==null || groupId.equals("")) {
			return "15=404=群号未传递";
		}
		
		String msg = userService.selectGroupMemberByGroupId(groupId);
		return msg;
	}
	
	
}
