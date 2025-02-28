package com.nuc.ljx.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nuc.ljx.model.FriendMessage;
import com.nuc.ljx.model.GroupInfo;
import com.nuc.ljx.model.GroupMember;
import com.nuc.ljx.model.GroupMessage;
import com.nuc.ljx.model.User;
import com.nuc.ljx.util.DBUtils;

public class UserDao {
	/**
	 * 增加一个用户 以及给这个用户增加一个好友---自己
	 * 2条增加语句----事务
	 * @param user
	 * @return
	 */
	public int addUserAndFriend(User user) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			//开启一个事务
			connection.setAutoCommit(false);
			//设置事务隔离级别为读未提交
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			String sql = "insert into user(nickname,username,password,sign,sex,header,add_time) values(?,?,?,?,?,?,?)";
			statement = DBUtils.getStatement(connection, sql, user.getNickname(),user.getUsername(),user.getPassword(),user.getSign(),user.getSex(),user.getHeader(),user.getAddTime());
			statement.executeUpdate();//执行成功并不会写到我们的数据库
			//查询一下没有增加到用户表中的用户数据-主要是获取自增的userId值
			String s = "select * from user where username=?";
			statement = DBUtils.getStatement(connection, s, user.getUsername());
			ResultSet query = statement.executeQuery();
			if (query.next()) {
				//增加朋友
				String sql1 = "insert into friends(user_id,user_friend_id,add_time) values(?,?,?)";
				statement = DBUtils.getStatement(connection, sql1,query.getInt("user_id"),query.getInt("user_id"),user.getAddTime());
				statement.executeUpdate();
				connection.commit();
				//用户信息添加成功
				return 1;
			}else {
				connection.rollback();
				return 0;
			}
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally {
			//关闭资源之前，必须先将connection的手动提交改为自动提交
			if (connection !=null) {
				try {
					connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			DBUtils.closeResources(statement, connection);
		}
		return 0;
	}
	
	/**
	 * 根据用户查询单个用户
	 * @param username
	 * @return
	 */
	public User selectUserByUsername(String username) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DBUtils.getConnection();
			String sql ="select * from user where username=?";
			statement = DBUtils.getStatement(connection, sql, username);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("user_id"));
				user.setUsername(username);
				user.setPassword(resultSet.getString("password"));
				return user;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return null;
	}
	/**
	 * 根据用户id查询单个用户
	 * @param userId
	 * @return
	 */
	public User selectUserByUserId(int userId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DBUtils.getConnection();
			String sql ="select * from user where user_id=?";
			statement = DBUtils.getStatement(connection, sql, userId);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("user_id"));
				user.setUsername(resultSet.getString("username"));
				user.setNickname(resultSet.getString("nickName"));
				user.setSex(resultSet.getString("sex"));
				user.setSign(resultSet.getString("sign"));
				user.setHeader(resultSet.getString("header"));
				user.setAddTime(resultSet.getDate("add_time"));
				user.setPassword(resultSet.getString("password"));
				return user;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return null;
	}
	/**
	 * 根据用户id查找用户信息以及用户的好友信息
	 * @param userId
	 * @return
	 */
	public List<User> selectUserAndFriendsByUserId(int userId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<User> list = new ArrayList<User>();
		try {
			connection = DBUtils.getConnection();
			String sql ="SELECT * FROM `user` WHERE user_id=? UNION ALL SELECT u.* FROM friends AS f INNER JOIN `user` AS u ON f.user_friend_id=u.user_id WHERE f.user_id=?;";
			statement = DBUtils.getStatement(connection, sql, userId,userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("user_id"));
				user.setUsername(resultSet.getString("username"));
				user.setNickname(resultSet.getString("nickName"));
				user.setSex(resultSet.getString("sex"));
				user.setSign(resultSet.getString("sign"));
				user.setHeader(resultSet.getString("header"));
				user.setAddTime(resultSet.getDate("add_time"));
				user.setPassword(resultSet.getString("password"));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return list;
	}

	/**
	 * 根据用户id和新密码修改用户密码
	 * @return
	 */
	public int updatePasswordByUserId(int userId,String newpass) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			String sql ="update user set password=? where user_id=?";
			statement = DBUtils.getStatement(connection, sql, newpass,userId);
			int num = statement.executeUpdate();
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(statement, connection);
		}
		return 0;
	}
	
	
	/**
	 * 根据用户id和搜索关键字查找用户信息
	 * @param userId
	 * @return
	 */
	public List<User> selectUsersByUserIdAndSearchKey(int userId,String searchKey) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<User> list = new ArrayList<User>();
		try {
			connection = DBUtils.getConnection();
			String sql ="select * from user where (username like ? or nickname like ?) and user_id not in (select user_friend_id from friends where user_id = ?)";
			String s = "%"+searchKey+"%";
			statement = DBUtils.getStatement(connection, sql, s,s,userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("user_id"));
				user.setUsername(resultSet.getString("username"));
				user.setNickname(resultSet.getString("nickName"));
				user.setSex(resultSet.getString("sex"));
				user.setSign(resultSet.getString("sign"));
				user.setHeader(resultSet.getString("header"));
				user.setAddTime(resultSet.getDate("add_time"));
				user.setPassword(resultSet.getString("password"));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return list;
	}
	
	/**
	 * 查询两人是否已经互为好友
	 * @param userId
	 * @param friendId
	 * @return 不是好友返回0
	 */
	public int isFriend(int userId,int friendId) {
		Connection connection = null;
		PreparedStatement statement = null;
		int num = 0;
		try {
			connection = DBUtils.getConnection();
			
			//SELECT COUNT(*) FROM friends WHERE user_id='1' and user_friend_id='5' or user_id='5' and user_friend_id='1'; 
			String sql ="SELECT COUNT(*) as num FROM friends WHERE user_id=? and user_friend_id=? or user_id=? and user_friend_id=?";
			statement = DBUtils.getStatement(connection, sql,userId,friendId,friendId,userId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				num = resultSet.getInt("num");
			}
			System.out.println(num);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources( statement, connection);
		}
		return num;
	}
	
	
	/**
	 * 添加用户的好友
	 * @return
	 */
	public int addFriends(int userId,int friendId) {
		Connection connection = null;
		PreparedStatement statement = null;
		//判断两人是否已是好友
		int result = isFriend(userId, friendId);
		System.out.println("000000"+result);
		if (result!=0) {
			System.out.println("shihaoyou");
			return 0;
		}
		try {
			connection = DBUtils.getConnection();
			//创建一个当前时间  时间必须是java.sql.Date
			Date date = new Date(System.currentTimeMillis());
			//insert into friends(..) values(1,2,xxx),(2,1,xxx)
			String sql ="insert into friends(user_id,user_friend_id,add_time) values(?,?,?),(?,?,?)";
			statement = DBUtils.getStatement(connection, sql,userId,friendId,date,friendId,userId,date);
			int num = statement.executeUpdate();
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(statement, connection);
		}
		return 0;
	}
	
	/**
	 * 查找用户和好友的聊天记录
	 * 查找用户和好友的历史聊天记录 并且需要将消息的发送者的昵称进行展示
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public List<FriendMessage> selectMessageByUserIdAndFriendId(int userId,int friendId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<FriendMessage> list = new ArrayList<FriendMessage>();
		try {
			connection = DBUtils.getConnection();
			String sql ="SELECT fm.*,`user`.nickname FROM friend_message AS fm INNER JOIN `user` ON fm.sender_id=`user`.user_id WHERE fm.sender_id=? AND fm.receiver_id=? OR fm.sender_id=? AND fm.receiver_id=?";
			statement = DBUtils.getStatement(connection, sql, userId,friendId,friendId,userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				FriendMessage fm = new FriendMessage();
				fm.setFriendMessageId(resultSet.getInt("friend_message_id"));
				fm.setSenderId(resultSet.getInt("sender_id"));
				fm.setSenderNickName(resultSet.getString("nickname"));//发送者的昵称
				fm.setReceiverId(resultSet.getInt("receiver_id"));
				fm.setMessage(resultSet.getString("message"));
				fm.setSendTime(resultSet.getDate("send_time"));
				list.add(fm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return list;
		
	}
	/**
	 * 数据库添加聊天信息
	 * @param parseInt
	 * @param parseInt2
	 * @param message
	 */
	public void insertMessage(int currentUserId, int friendId, String message) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			//创建一个当前时间  时间必须是java.sql.Date
			Date date = new Date(System.currentTimeMillis());
			//insert into friends(..) values(1,2,xxx),(2,1,xxx)
			String sql ="insert into friend_message(sender_id,receiver_id,message,send_time) values(?,?,?,?) ";
			statement = DBUtils.getStatement(connection, sql,currentUserId,friendId,message,date);
			int num = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.closeResources(statement, connection);
		}
	}
	
	/**
	 * 根据用户id和搜索关键字查找好友信息
	 * @param userId
	 * @return
	 */
	public List<User> searchFriendsByUserIdAndSearchKey(int userId,String searchKey) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<User> list = new ArrayList<User>();
		try {
			connection = DBUtils.getConnection();
			//SELECT * FROM USER WHERE user_id = 2 UNION ( SELECT * FROM USER WHERE nickname LIKE '%z%' AND user_id IN ( SELECT user_friend_id FROM friends WHERE user_id = 2 ) )
			String sql ="SELECT * FROM USER WHERE user_id = ? UNION ( SELECT * FROM USER WHERE nickname LIKE ? AND user_id IN ( SELECT user_friend_id FROM friends WHERE user_id = ? ) )";
			String s = "%"+searchKey+"%";
			statement = DBUtils.getStatement(connection, sql, userId,s,userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("user_id"));
				user.setUsername(resultSet.getString("username"));
				user.setNickname(resultSet.getString("nickName"));
				user.setSex(resultSet.getString("sex"));
				user.setSign(resultSet.getString("sign"));
				user.setHeader(resultSet.getString("header"));
				user.setAddTime(resultSet.getDate("add_time"));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return list;
	}

	
	/**
	 * 根据群号查询群信息
	 * @param number
	 * @return
	 */
	public GroupInfo selectGroupByGroupName(String number) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DBUtils.getConnection();
			String sql ="select * from group_info where group_number=?";
			statement = DBUtils.getStatement(connection, sql, number);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				GroupInfo groupInfo = new GroupInfo();
				groupInfo.setGroupId(resultSet.getInt("group_id"));
				return groupInfo;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return null;
	}
	
	/**
	 * 创建一个群
	 * @param groupInfo
	 * @return
	 */
	public int addGroup(GroupInfo groupInfo) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			//开启一个事务
			connection.setAutoCommit(false);
			//设置事务隔离级别为读未提交
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			String sql = "insert into group_info(group_number,group_name,group_leader_id,group_intro,create_time) values(?,?,?,?,?)";
			statement = DBUtils.getStatement(connection, sql,groupInfo.getGroupNumber(), groupInfo.getGroupName(), groupInfo.getGroupLeaderId(), groupInfo.getGroupIntro(),groupInfo.getCreateTime());
			statement.executeUpdate();//执行成功并不会写到我们的数据库
			//查询一下没有增加到群表的群数据-主要是获取自增的groupId值
			String s = "select * from group_info where group_number=?";
			statement = DBUtils.getStatement(connection, s, groupInfo.getGroupNumber());
			ResultSet query = statement.executeQuery();
			if (query.next()) {
				//增加群成员
				String sql1 = "insert into group_member(group_id,member_id,add_time) values(?,?,?)";
				statement = DBUtils.getStatement(connection, sql1,query.getInt("group_id"),groupInfo.getGroupLeaderId(),groupInfo.getCreateTime());
				statement.executeUpdate();
				connection.commit();
				//用户信息添加成功
				return 1;
			}else {
				connection.rollback();
				return 0;
			}
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally {
			//关闭资源之前，必须先将connection的手动提交改为自动提交
			if (connection !=null) {
				try {
					connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			DBUtils.closeResources(statement, connection);
		}
		return 0;
	}

	/**
	 * 根据用户id查询用户所在群
	 * @param userId
	 * @return
	 */
	public List<GroupInfo> selectGroupByUserId(int userId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<GroupInfo> list = new ArrayList<GroupInfo>();
		try {
			connection = DBUtils.getConnection();
			//SELECT gi.group_id,gi.group_intro,gi.group_name,gi.group_number,gi.group_leader_id from group_member as gm LEFT JOIN group_info as gi ON gm.group_id=gi.group_id WHERE member_id=11;
			String sql ="SELECT gi.group_id,gi.group_intro,gi.group_name,gi.group_number,gi.group_leader_id from group_member as gm LEFT JOIN group_info as gi ON gm.group_id=gi.group_id WHERE member_id=?";
			statement = DBUtils.getStatement(connection, sql, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				GroupInfo groupInfo = new GroupInfo();
				groupInfo.setGroupId(resultSet.getInt("group_id"));
				groupInfo.setGroupNumber(resultSet.getString("group_number"));
				groupInfo.setGroupName(resultSet.getString("group_name"));
				groupInfo.setGroupLeaderId(resultSet.getInt("group_leader_id"));
				groupInfo.setGroupIntro(resultSet.getString("group_intro"));
				list.add(groupInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return list;

	}

	/**
	 * 群聊天记录
	 * @param groupId
	 * @return
	 */
	public List<GroupMessage> selectGroupHistoryChatByGroupId(int groupId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<GroupMessage> list = new ArrayList<GroupMessage>();
		try {
			connection = DBUtils.getConnection();
			//SELECT gm.group_message_id,gm.group_id,gm.member_id,gm.message,gm.send_time,u.nickname FROM group_message as gm LEFT JOIN `user` as u ON gm.member_id=u.user_id WHERE group_id=1
			String sql ="SELECT gm.group_message_id,gm.group_id,gm.member_id,gm.message,gm.send_time,u.nickname FROM group_message as gm LEFT JOIN `user` as u ON gm.member_id=u.user_id WHERE group_id=?";
			statement = DBUtils.getStatement(connection, sql,groupId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				GroupMessage gm = new GroupMessage();
				gm.setGroupId(resultSet.getInt("group_id"));
				gm.setGroupMessageId(resultSet.getInt("group_id"));
				gm.setMemberId(resultSet.getInt("group_id"));
				gm.setMessage(resultSet.getString("message"));
				gm.setSendMemberName(resultSet.getString("nickname"));
				gm.setSendTime(resultSet.getDate("send_time"));
				list.add(gm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return list;
	}

	/**
	 * 发群消息
	 * @param userId
	 * @param groupId
	 * @param message
	 */
	public void addGroupMessage(int userId, int groupId, String message) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			//创建一个当前时间  时间必须是java.sql.Date
			Date date = new Date(System.currentTimeMillis());
			//insert into group_message(group_id,member_id,message,send_time) values(11,2,'kooko','2022-07-21 23:22:42') 
			String sql ="insert into group_message(group_id,member_id,message,send_time) values(?,?,?,?) ";
			statement = DBUtils.getStatement(connection, sql,groupId,userId,message,date);
			int num = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.closeResources(statement, connection);
		}
	}

	/**
	 * 关键字查当前用户所在群
	 * @param parseInt
	 * @param searchKey
	 * @return
	 */
	public List<GroupInfo> searchGroupsByUserIdAndSearchKey(int userId, String searchKey) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<GroupInfo> list = new ArrayList<GroupInfo>();
		try {
			connection = DBUtils.getConnection();
			//SELECT * FROM group_info WHERE (group_number LIKE '%4%' OR group_name LIKE '%4%' ) AND group_id IN (SELECT group_id FROM group_member WHERE member_id = 11)
			String sql ="SELECT * FROM group_info WHERE (group_number LIKE ? OR group_name LIKE ? ) AND group_id IN (SELECT group_id FROM group_member WHERE member_id =?)";
			String s = "%"+searchKey+"%";
			statement = DBUtils.getStatement(connection, sql, s,s,userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				GroupInfo groupInfo = new GroupInfo();
				groupInfo.setGroupId(resultSet.getInt("group_id"));
				groupInfo.setGroupNumber(resultSet.getString("group_number"));
				groupInfo.setGroupName(resultSet.getString("group_name"));
				groupInfo.setGroupLeaderId(resultSet.getInt("group_leader_id"));
				groupInfo.setGroupIntro(resultSet.getString("group_intro"));
				list.add(groupInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return list;
	}

	/**
	 * 关键字查当前用户不在的群
	 * @param parseInt
	 * @param searchKey
	 * @return
	 */
	public List<GroupInfo> searchGroupsByUserIdAndSearchKeyForEntry(int userId, String searchKey) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<GroupInfo> list = new ArrayList<GroupInfo>();
		System.out.println(userId);
		System.out.println(searchKey);
		try {
			connection = DBUtils.getConnection();
			//SELECT g.group_id,g.group_name, g.group_number, g.group_intro, u.nickname FROM group_info as g LEFT JOIN `user` as u on u.user_id=g.group_leader_id where (group_number LIKE '%4%' OR group_name LIKE '%4%' ) AND group_id NOT IN (SELECT group_id FROM group_member WHERE member_id = 11)
			String sql ="SELECT g.group_id,g.group_name, g.group_number, g.group_intro, u.nickname FROM group_info as g LEFT JOIN `user` as u on u.user_id=g.group_leader_id where (group_number LIKE ? OR group_name LIKE ? ) AND group_id NOT IN (SELECT group_id FROM group_member WHERE member_id =?)";
			String s = "%"+searchKey+"%";
			statement = DBUtils.getStatement(connection, sql, s,s,userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				GroupInfo groupInfo = new GroupInfo();
				groupInfo.setGroupId(resultSet.getInt("group_id"));
				groupInfo.setGroupNumber(resultSet.getString("group_number"));
				groupInfo.setGroupName(resultSet.getString("group_name"));
				groupInfo.setGroupLeaderName(resultSet.getString("nickname"));
				groupInfo.setGroupIntro(resultSet.getString("group_intro"));
				list.add(groupInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return list;
	}
	
	/**
	 * 查询用户是否已在群中
	 * @param userId
	 * @param groupId
	 * @return 不是0
	 */
	public int inGroup(int userId,int groupId) {
		Connection connection = null;
		PreparedStatement statement = null;
		int num = 0;
		try {
			connection = DBUtils.getConnection();
			
			//select COUNT(*) as num FROM group_member WHERE group_id=1 and member_id=11
			String sql ="select COUNT(*) as num FROM group_member WHERE group_id=? and member_id=?";
			statement = DBUtils.getStatement(connection, sql,groupId,userId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				num = resultSet.getInt("num");
			}
			System.out.println(num);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources( statement, connection);
		}
		return num;
	}
	
	
	/**
	 * 群成员表中添加该用户数据
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	public int entryGroups(int userId, int groupId) {
		Connection connection = null;
		PreparedStatement statement = null;
		//判断是否已在群中
		int result = inGroup(userId, groupId);
		System.out.println("000000"+result);
		if (result!=0) {
			System.out.println("在群中");
			return 0;
		}
		try {
			connection = DBUtils.getConnection();
			//创建一个当前时间  时间必须是java.sql.Date
			Date date = new Date(System.currentTimeMillis());
			//insert into group_member(group_id,member_id,add_time) values(1,1,'2022-07-20 00:00:00')
			String sql ="insert into group_member(group_id,member_id,add_time) values(?,?,?)";
			statement = DBUtils.getStatement(connection, sql,groupId,userId,date);
			int num = statement.executeUpdate();
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(statement, connection);
		}
		return 0;
	}
	/**
	 * 根据群id查询群信息
	 * @param 
	 * @return
	 */
	public GroupInfo selectGroupByGroupId(int groupId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DBUtils.getConnection();
			String sql ="select * from group_info where group_id=?";
			statement = DBUtils.getStatement(connection, sql, groupId);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				GroupInfo groupInfo = new GroupInfo();
				groupInfo.setGroupLeaderId(resultSet.getInt("group_leader_id"));
				return groupInfo;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return null;
	}

	/**
	 * 将某用户从群成员中删除
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public int exitGroup(int userId, int groupId) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			//DELETE FROM group_member WHERE group_id=10 and member_id=11
			String sql ="DELETE FROM group_member WHERE group_id=? and member_id=?";
			statement = DBUtils.getStatement(connection, sql,groupId,userId);
			int num = statement.executeUpdate();
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(statement, connection);
		}
		return 0;
	}

	/**
	 * 删除群
	 * @param groupId
	 * @return
	 */
	public int deleteGroup(int groupId) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			//开启一个事务
			connection.setAutoCommit(false);
			//删除该群群成员
			//DELETE FROM group_member WHERE group_id=10
			String sql = "DELETE FROM group_member WHERE group_id=?";
			statement = DBUtils.getStatement(connection, sql, groupId);
			statement.executeUpdate();//执行成功并不会写到我们的数据库
			//删除该群
			//DELETE FROM group_info WHERE group_id=10
			String sql1 = "DELETE FROM group_info WHERE group_id=?";
			statement = DBUtils.getStatement(connection, sql1,groupId);
			statement.executeUpdate();
			//提交事务
			connection.commit();
			//删除成功
			return 1;
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally {
			//关闭资源之前，必须先将connection的手动提交改为自动提交
			if (connection !=null) {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			DBUtils.closeResources(statement, connection);
		}
		return 0;
	}

	/**
	 * 查询某群群成员列表
	 * @param groupId
	 * @return
	 */
	public List<User> selectGroupMemberByGroupId(int groupId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<User> list = new ArrayList<User>();
		try {
			connection = DBUtils.getConnection();
			//SELECT * FROM `user` WHERE user_id in (select member_id FROM group_member WHERE group_id=1)
			String sql ="SELECT * FROM `user` WHERE user_id in (select member_id FROM group_member WHERE group_id=?)";
			statement = DBUtils.getStatement(connection, sql, groupId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("user_id"));
				user.setUsername(resultSet.getString("username"));
				user.setNickname(resultSet.getString("nickName"));
				user.setSex(resultSet.getString("sex"));
				user.setSign(resultSet.getString("sign"));
				user.setHeader(resultSet.getString("header"));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeResources(resultSet, statement, connection);
		}
		return list;
	}
	
}	
