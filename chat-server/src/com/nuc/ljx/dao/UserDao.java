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
	 * ����һ���û� �Լ�������û�����һ������---�Լ�
	 * 2���������----����
	 * @param user
	 * @return
	 */
	public int addUserAndFriend(User user) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			//����һ������
			connection.setAutoCommit(false);
			//����������뼶��Ϊ��δ�ύ
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			String sql = "insert into user(nickname,username,password,sign,sex,header,add_time) values(?,?,?,?,?,?,?)";
			statement = DBUtils.getStatement(connection, sql, user.getNickname(),user.getUsername(),user.getPassword(),user.getSign(),user.getSex(),user.getHeader(),user.getAddTime());
			statement.executeUpdate();//ִ�гɹ�������д�����ǵ����ݿ�
			//��ѯһ��û�����ӵ��û����е��û�����-��Ҫ�ǻ�ȡ������userIdֵ
			String s = "select * from user where username=?";
			statement = DBUtils.getStatement(connection, s, user.getUsername());
			ResultSet query = statement.executeQuery();
			if (query.next()) {
				//��������
				String sql1 = "insert into friends(user_id,user_friend_id,add_time) values(?,?,?)";
				statement = DBUtils.getStatement(connection, sql1,query.getInt("user_id"),query.getInt("user_id"),user.getAddTime());
				statement.executeUpdate();
				connection.commit();
				//�û���Ϣ��ӳɹ�
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
			//�ر���Դ֮ǰ�������Ƚ�connection���ֶ��ύ��Ϊ�Զ��ύ
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
	 * �����û���ѯ�����û�
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
	 * �����û�id��ѯ�����û�
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
	 * �����û�id�����û���Ϣ�Լ��û��ĺ�����Ϣ
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
	 * �����û�id���������޸��û�����
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
	 * �����û�id�������ؼ��ֲ����û���Ϣ
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
	 * ��ѯ�����Ƿ��Ѿ���Ϊ����
	 * @param userId
	 * @param friendId
	 * @return ���Ǻ��ѷ���0
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
	 * ����û��ĺ���
	 * @return
	 */
	public int addFriends(int userId,int friendId) {
		Connection connection = null;
		PreparedStatement statement = null;
		//�ж������Ƿ����Ǻ���
		int result = isFriend(userId, friendId);
		System.out.println("000000"+result);
		if (result!=0) {
			System.out.println("shihaoyou");
			return 0;
		}
		try {
			connection = DBUtils.getConnection();
			//����һ����ǰʱ��  ʱ�������java.sql.Date
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
	 * �����û��ͺ��ѵ������¼
	 * �����û��ͺ��ѵ���ʷ�����¼ ������Ҫ����Ϣ�ķ����ߵ��ǳƽ���չʾ
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
				fm.setSenderNickName(resultSet.getString("nickname"));//�����ߵ��ǳ�
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
	 * ���ݿ����������Ϣ
	 * @param parseInt
	 * @param parseInt2
	 * @param message
	 */
	public void insertMessage(int currentUserId, int friendId, String message) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			//����һ����ǰʱ��  ʱ�������java.sql.Date
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
	 * �����û�id�������ؼ��ֲ��Һ�����Ϣ
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
	 * ����Ⱥ�Ų�ѯȺ��Ϣ
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
	 * ����һ��Ⱥ
	 * @param groupInfo
	 * @return
	 */
	public int addGroup(GroupInfo groupInfo) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			//����һ������
			connection.setAutoCommit(false);
			//����������뼶��Ϊ��δ�ύ
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			String sql = "insert into group_info(group_number,group_name,group_leader_id,group_intro,create_time) values(?,?,?,?,?)";
			statement = DBUtils.getStatement(connection, sql,groupInfo.getGroupNumber(), groupInfo.getGroupName(), groupInfo.getGroupLeaderId(), groupInfo.getGroupIntro(),groupInfo.getCreateTime());
			statement.executeUpdate();//ִ�гɹ�������д�����ǵ����ݿ�
			//��ѯһ��û�����ӵ�Ⱥ���Ⱥ����-��Ҫ�ǻ�ȡ������groupIdֵ
			String s = "select * from group_info where group_number=?";
			statement = DBUtils.getStatement(connection, s, groupInfo.getGroupNumber());
			ResultSet query = statement.executeQuery();
			if (query.next()) {
				//����Ⱥ��Ա
				String sql1 = "insert into group_member(group_id,member_id,add_time) values(?,?,?)";
				statement = DBUtils.getStatement(connection, sql1,query.getInt("group_id"),groupInfo.getGroupLeaderId(),groupInfo.getCreateTime());
				statement.executeUpdate();
				connection.commit();
				//�û���Ϣ��ӳɹ�
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
			//�ر���Դ֮ǰ�������Ƚ�connection���ֶ��ύ��Ϊ�Զ��ύ
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
	 * �����û�id��ѯ�û�����Ⱥ
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
	 * Ⱥ�����¼
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
	 * ��Ⱥ��Ϣ
	 * @param userId
	 * @param groupId
	 * @param message
	 */
	public void addGroupMessage(int userId, int groupId, String message) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			//����һ����ǰʱ��  ʱ�������java.sql.Date
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
	 * �ؼ��ֲ鵱ǰ�û�����Ⱥ
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
	 * �ؼ��ֲ鵱ǰ�û����ڵ�Ⱥ
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
	 * ��ѯ�û��Ƿ�����Ⱥ��
	 * @param userId
	 * @param groupId
	 * @return ����0
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
	 * Ⱥ��Ա������Ӹ��û�����
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	public int entryGroups(int userId, int groupId) {
		Connection connection = null;
		PreparedStatement statement = null;
		//�ж��Ƿ�����Ⱥ��
		int result = inGroup(userId, groupId);
		System.out.println("000000"+result);
		if (result!=0) {
			System.out.println("��Ⱥ��");
			return 0;
		}
		try {
			connection = DBUtils.getConnection();
			//����һ����ǰʱ��  ʱ�������java.sql.Date
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
	 * ����Ⱥid��ѯȺ��Ϣ
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
	 * ��ĳ�û���Ⱥ��Ա��ɾ��
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
	 * ɾ��Ⱥ
	 * @param groupId
	 * @return
	 */
	public int deleteGroup(int groupId) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtils.getConnection();
			//����һ������
			connection.setAutoCommit(false);
			//ɾ����ȺȺ��Ա
			//DELETE FROM group_member WHERE group_id=10
			String sql = "DELETE FROM group_member WHERE group_id=?";
			statement = DBUtils.getStatement(connection, sql, groupId);
			statement.executeUpdate();//ִ�гɹ�������д�����ǵ����ݿ�
			//ɾ����Ⱥ
			//DELETE FROM group_info WHERE group_id=10
			String sql1 = "DELETE FROM group_info WHERE group_id=?";
			statement = DBUtils.getStatement(connection, sql1,groupId);
			statement.executeUpdate();
			//�ύ����
			connection.commit();
			//ɾ���ɹ�
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
			//�ر���Դ֮ǰ�������Ƚ�connection���ֶ��ύ��Ϊ�Զ��ύ
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
	 * ��ѯĳȺȺ��Ա�б�
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
