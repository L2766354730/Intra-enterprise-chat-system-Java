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
	 * ע���û���ҵ���߼�
	 * @param user
	 * @return
	 */
	public String RegisterUser(User user) {
		//1���������һ����λ�˺�
		Random rand = new Random();
		//�ж�һ���˺������ݿ���û��ʹ��
		String number = ""+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10);
		User u = userDao.selectUserByUsername(number);
		//���ʹ�ù�  ��������
		while (u != null) {
			number = ""+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10);
			u = userDao.selectUserByUsername(number);
		}
		//û��ʹ�ù� ��number�˺Ÿ�ֵ��user���� ����˺ž���ע���û����˺�
		user.setUsername(number);
		//2������ע���û��Ĵ���ʱ�� Date��java.sql.Date
		user.setAddTime(new Date(System.currentTimeMillis()));
		//3������dao����������
		int result = userDao.addUserAndFriend(user);
		if (result >0) {
			return "0=200=ע��ɹ�="+number;
		}else {
			return "0=404=ע��ʧ��";
		}
	}
	
	/**
	 * ��¼�߼�ִ��
	 * @param username
	 * @param password
	 * @return
	 */
	public String loginByUsernameAnaPassoword(String username,String password) {
		User user = userDao.selectUserByUsername(username);
		if (user == null) {
			return "1=404=�û�������";
		}else {
			if (password.equals(user.getPassword())) {
				return "1=200=��¼�ɹ�="+user.getUserId()+"="+user.getUsername();
			}else {
				return "1=404=���벻��ȷ";
			}
		}
	}
	
	/**
	 * �����û�id��ѯ������Ϣ
	 * @param userId
	 * @return
	 */
	public String selectUserAndFriendsByUserId(int userId) {
		List<User> users = userDao.selectUserAndFriendsByUserId(userId);
		if (users == null || users.isEmpty()) {
			return "2=404=�û�������";
		}else {
			/**
			 * r����û����� ��Ҫ������������
			 * 2=200=�ɹ���Ϣ=user��JSON��ʽ����
			 */
			String userJson = JSON.toJSONString(users,SerializerFeature.WRITE_MAP_NULL_FEATURES);
			return "2=200=��ѯ�ɹ�="+userJson;
		}
	}

	/**
	 * �����޸�������߼�
	 * @param userId
	 * @param oldpass
	 * @param newpass
	 * @return
	 */
	public String updatePasswordByUserId(String userId,String oldpass,String newpass) {
		//1�����жϾ������Ƿ�һ��
		User user = userDao.selectUserByUserId(Integer.parseInt(userId));
		if (user == null) {
			return "3=404=Ҫ�޸ĵ��û�������";
		}else {
			//�ж�һ���û�����ľ���������ݿ��ѯ�����ľ������Ƿ�һ��
			if (oldpass.equals(user.getPassword())) {
				int result = userDao.updatePasswordByUserId(Integer.parseInt(userId), newpass);
				if (result >0) {
					return "3=200=�޸ĳɹ���";
				}else {
					return "3=404=�����޸�ʧ�ܣ�";
				}
			}else {
				return "3=404=����ľ����벻��ȷ��";
			}
		}
	}
	
	
	/**
	 * �����û�id�͹ؼ��ֲ��Ҳ��ǵ�ǰ�û��������û���Ϣ
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchUsersByUserIdAndSearchKey(String userId,String searchKey) {
		//2������dao���ѯ����
		List<User> users = userDao.selectUsersByUserIdAndSearchKey(Integer.parseInt(userId), searchKey);
		/**
		 * ��ɳ�һ�����ݷ��ظ�������
		 * 4=200=��ѯ�ɹ�=usersJson
		 */
		String msg = "4=200=��ѯ�ɹ�="+JSON.toJSONString(users,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}
	
	/**
	 * ��userId���friendId����
	 * ���Ҹ�friendId���userId����
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public String addFriends(String userId,String friendId) {
		//1������dao������������
		int result = userDao.addFriends(Integer.parseInt(userId), Integer.parseInt(friendId));
		//2�������ؽ��
		if (result >0) {
			return "5=200=��Ӻ��ѳɹ�";
		}else {
			return "5=404=��Ӻ���ʧ��";
		}
		
	}
	/**
	 * ����userId�ͺ���id��ѯ�����¼
	 * @param cuid
	 * @param fid
	 * @return
	 */
	public String queryChatMessage(String cuid, String fid) {
		List<FriendMessage> messages = userDao.selectMessageByUserIdAndFriendId(Integer.parseInt(cuid), Integer.parseInt(fid));
		//6=200=��ѯ�ɹ�=messagejson
		String msg = "6=200=��ѯ�ɹ�="+JSON.toJSONString(messages,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}

	/**
	 * ������������
	 * @param cruuentuid
	 * @param friendid
	 * @param message
	 */
	public void addMessage(String cruuentuid, String friendid, String message) {
		userDao.insertMessage(Integer.parseInt(cruuentuid),Integer.parseInt(friendid),message);
	}
	
	
	/**
	 * �����û�id�͹ؼ��ֲ��ҵ�ǰ�û��ĺ����û���Ϣ
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchFriendsByUserIdAndSearchKey(String userId,String searchKey) {
		//2������dao���ѯ����
		List<User> users = userDao.searchFriendsByUserIdAndSearchKey(Integer.parseInt(userId), searchKey);
		/**
		 * ��ɳ�һ�����ݷ��ظ�������
		 * 2=200=��ѯ�ɹ�=userJson
		 */
		String msg = "2=200=��ѯ�ɹ�="+JSON.toJSONString(users,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}

	/**
	 * ��Ⱥ
	 * @param userId
	 * @param groupName
	 * @param groupIntro
	 * @return
	 */
	public String addGroup(GroupInfo groupInfo) {
		//1���������һ����λ�˺�
		Random rand = new Random();
		//�ж�һ��Ⱥ�������ݿ���û��ʹ��
		String number = ""+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10);
		GroupInfo g = userDao.selectGroupByGroupName(number);
		System.out.println(groupInfo);
		//���ʹ�ù�  ��������
		while (g != null) {
			number = ""+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10);
			g = userDao.selectGroupByGroupName(number);
		}
		
		//û��ʹ�ù� ��number�˺Ÿ�ֵ��groupInfo���� ����˺ž���Ⱥ���˺�
		groupInfo.setGroupNumber(number);
		//2�����ô���ʱ�� Date��java.sql.Date
		groupInfo.setCreateTime(new Date(System.currentTimeMillis()));
		
		//3������dao����������
		int result = userDao.addGroup(groupInfo);
		if (result >0) {
			return "9=200=�����ɹ�="+number;
		}else {
			return "9=404=����ʧ��";
		}
	}
	/**
	 * �����м����Ⱥ
	 * @param userId
	 * @return
	 */
	public String selectGroupByUserId(int userId) {
		List<GroupInfo> groupInfos = userDao.selectGroupByUserId(userId);
		if (groupInfos == null || groupInfos.isEmpty()) {
			return "10=404=��ȺΪ��";
		}else {
			/**
			 * �����Ⱥ ��Ҫ������������
			 * 10=200=�ɹ���Ϣ=groupInfos��JSON��ʽ����
			 */
			String groupInfosJson = JSON.toJSONString(groupInfos,SerializerFeature.WRITE_MAP_NULL_FEATURES);
			return "10=200=��ѯ�ɹ�="+groupInfosJson;
		}
	}

	/**
	 * ��ĳȺ�����¼
	 * @param groupId
	 * @return
	 */
	public String selectGroupHistoryChat(String groupId) {
		List<GroupMessage> messages = userDao.selectGroupHistoryChatByGroupId(Integer.parseInt(groupId));
		//11=200=��ѯ�ɹ�=messagejson
		String msg = "11=200=��ѯ�ɹ�="+JSON.toJSONString(messages,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}

	/**
	 * ����Ⱥ��Ϣ
	 * @param userId
	 * @param groupId
	 * @param message
	 */
	public void addGroupMessage(String userId, String groupId, String message) {
		userDao.addGroupMessage(Integer.parseInt(userId),Integer.parseInt(groupId),message);
		
	}

	
	/**
	 * �ؼ��ֲ鵱ǰ�û�����Ⱥ
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchGroupsByUserIdAndSearchKey(String userId, String searchKey) {
		//2������dao���ѯ����
		List<GroupInfo> groupInfos = userDao.searchGroupsByUserIdAndSearchKey(Integer.parseInt(userId), searchKey);
		/**
		 * ��ɳ�һ�����ݷ��ظ�������
		 * 10=200=��ѯ�ɹ�=groupInfosJson
		 */
		String msg = "10=200=��ѯ�ɹ�="+JSON.toJSONString(groupInfos,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}

	/**
	 * �ؼ��ֲ鵱ǰ�û����ڵ�Ⱥ
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchGroupsByUserIdAndSearchKeyForEntry(String userId, String searchKey) {
		//2������dao���ѯ����
		List<GroupInfo> groupInfos = userDao.searchGroupsByUserIdAndSearchKeyForEntry(Integer.parseInt(userId), searchKey);
		/**
		 * ��ɳ�һ�����ݷ��ظ�������
		 * 12=200=��ѯ�ɹ�=groupInfosJson
		 */
		System.out.println("service");
		String msg = "12=200=��ѯ�ɹ�="+JSON.toJSONString(groupInfos,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		return msg;
	}

	/**
	 * Ⱥ��Ա�м�����û�
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public String entryGroups(String userId, String groupId) {
		//1������dao������������
		int result = userDao.entryGroups(Integer.parseInt(userId), Integer.parseInt(groupId));
		//2�������ؽ��
		if (result >0) {
			return "13=200=����Ⱥ�ɹ�";
		}else {
			return "13=404=����Ⱥʧ��";
		}
	}


	/**
	 * ��Ⱥ�������Ⱥ������Ϊ��ɢȺ
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public String exitGroups(String userId, String groupId) {
		String msg = null;
		Integer groupLeaderId = userDao.selectGroupByGroupId(Integer.parseInt(groupId)).getGroupLeaderId();
		if (Integer.parseInt(userId)==groupLeaderId) {
			//��Ⱥ��
			int r = userDao.deleteGroup(Integer.parseInt(groupId));
			if (r==1) {
				msg = "14=200=��ɢȺ�ɹ�";
			}else {
				msg = "14=404=��ɢȺʧ��";
			}
		} else {
			//����Ⱥ��
			int result = userDao.exitGroup(Integer.parseInt(userId), Integer.parseInt(groupId));
			if(result==1) {
				msg = "14=200=��Ⱥ�ɹ�";
			}else {
				msg = "14=404=��Ⱥʧ��";
			}
		}
		
		return msg;
		
	}

	/**
	 * ��ѯĳȺȺ��Ա�б�
	 * @param groupId
	 * @return
	 */
	public String selectGroupMemberByGroupId(String groupId) {
		List<User> groupMembers = userDao.selectGroupMemberByGroupId(Integer.parseInt(groupId));
		if (groupMembers == null || groupMembers.isEmpty()) {
			return "15=404=Ⱥ��ԱΪ��";
		}else {
			/**
			 * �����Ⱥ ��Ҫ������������
			 * 15=200=�ɹ���Ϣ=groupMembers��JSON��ʽ����
			 */
			String groupMembersJson = JSON.toJSONString(groupMembers,SerializerFeature.WRITE_MAP_NULL_FEATURES);
			return "15=200=��ѯ�ɹ�="+groupMembersJson;
		}
	}
}
