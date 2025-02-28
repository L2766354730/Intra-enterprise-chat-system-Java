package com.nuc.ljx.controller;

import com.nuc.ljx.model.GroupInfo;
import com.nuc.ljx.model.User;
import com.nuc.ljx.service.UserService;

/**
 * ������û����йص��߼�
 * @author 11018
 *
 */
public class UserController {
	private UserService userService = new UserService();
	/**
	 * �û��� ����  ��¼�ж�
	 * @param username
	 * @param password
	 * @return  ���ص���������Ǹ��ͻ��˵�
	 * ���ص������ǿͻ��˵�¼��Ҫ������
	 * 1=404=ʧ��ԭ��
	 * 1=200=�ɹ���Ϣ=�û�id
	 */
	public String login(String username,String password) {
		//1��У�����ݵ�׼ȷ��
		if (username == null || username.equals("")) {
			return "1=404=�û���δ����";
		}
		if (password == null || password.equals("")) {
			return "1=404=����δ����";
		}
		//2������serviceȥ�жϵ�¼ҵ���߼�
		String result = userService.loginByUsernameAnaPassoword(username, password);
		return result;
	}
	/**
	 * �����û�idȥ��ѯ�û��ĺ����б���Ϣ�Լ���ǰ�û���Ϣ
	 * @param userId
	 * @return
	 * ������Ϣ����
	 * 2=404=ʧ����Ϣ
	 * 2=200=�ɹ���Ϣ=��ѯ������userJson����
	 */
	public String selectUserAndFriendsByUserId(String userId) {
		if (userId==null || userId.equals("")) {
			return "2=404=�û����δ����";
		}
		
		String msg = userService.selectUserAndFriendsByUserId(Integer.parseInt(userId));
		return msg;
	}

	/**
	 * �����û�idȥ�޸��û�����
	 * @param userId
	 * @param oldpass
	 * @param newpass
	 * @return
	 * 3=404=ʧ��
	 * 3=200=�ɹ���Ϣ
	 */
	public String updatePassword(String userId,String oldpass,String newpass) {
		if (userId == null || userId.equals("")) {
			return "3=404=�û�Idδ����";
		}
		if (oldpass.equals(newpass)) {
			return "3=404=�¾�����һ��";
		}
		
		//����service���ҵ���߼�
		String msg = userService.updatePasswordByUserId(userId, oldpass, newpass);
		return msg;
	}

	/**
	 * ���ݿͻ��˴��ݵ������Ϣȥע���û�
	 * @param user
	 * @return
	 */
	public String register(User user) {
		//1������У�� ǰ�˴��ݵ�ÿһ�����ݶ���У�� �ǿ�У�� ���ַ���У��
		if (user == null) {
			return "0=404=�û���ϢΪ��";
		}
		//2������service���ע���߼�
		//0=404=ע��ʧ��
		//0=200=ע��ɹ�=number
		String msg = userService.RegisterUser(user);
		return msg;
	}
	
	/**
	 * �����û�id�������ؼ��������û���Ϣ
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchUsersByUserIdAndSearchKey(String userId,String searchKey) {
		//1��У������
		if (userId == null || userId.equals("")) {
			return "4=404=�û�Idδ����";
		}
		//2������service��ʵ�ֲ�ѯ�߼�
		String msg = userService.searchUsersByUserIdAndSearchKey(userId, searchKey);
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
		//1��У������
		if (userId == null || userId.equals("")) {
			return "5=404=�û�Idδ����";
		}
		if (friendId == null || friendId.equals("")) {
			return "5=404=����Idδ����";
		}
		//2������service������ݵ����
		String msg = userService.addFriends(userId, friendId);
		return msg;
	}
	/**
	 * �����û�id�ͺ���id��ѯ����֮��������¼
	 * @param cuid
	 * @param fid
	 * @return
	 */
	public String queryChatMessage(String cuid, String fid) {
		//1��У������
		if (cuid == null || cuid.equals("")) {
			return "6=404=�û�Idδ����";
		}
		if (fid == null || fid.equals("")) {
			return "6=404=����Idδ����";
		}
		//2������service�����Ϣ�Ĳ���
		String msg = userService.queryChatMessage(cuid,fid);
		return msg;
	}
	/**
	 * �����������
	 * @param cruuentuid
	 * @param friendid
	 * @param message
	 */
	public void addMessage(String cruuentuid, String friendid, String message) {
		userService.addMessage(cruuentuid,friendid,message);
	}
	
	/**
	 * �����û�id�������ؼ�������������Ϣ
	 * @param userId
	 * @param searchKey
	 * @return
	 */
	public String searchFriendsByUserIdAndSearchKey(String userId,String searchKey) {
		//1��У������
		if (userId == null || userId.equals("")) {
			return "2=404=�û�Idδ����";
		}
		//2������service��ʵ�ֲ�ѯ�߼�
		String msg = userService.searchFriendsByUserIdAndSearchKey(userId, searchKey);
		return msg;
	}
	/**
	 * ��Ⱥ
	 * @param uid2
	 * @param groupName
	 * @param groupIntro
	 * @return
	 */
	public String addGroup(String userId, String groupName, String groupIntro) {
		//1������У�� ǰ�˴��ݵ�ÿһ�����ݶ���У�� �ǿ�У�� ���ַ���У��
		if (userId == null || userId.equals("")) {
			return "9=404=�û�Idδ����";
		}
		if (groupName == null || groupName.equals("")) {
			return "9=404=Ⱥ��δ����";
		}
		if (groupIntro == null || groupIntro.equals("")) {
			return "9=404=Ⱥ����δ����";
		}
		//2������service����߼�
		//0=404=��Ⱥʧ��
		//0=200=��Ⱥ�ɹ�=groupNumber
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setGroupLeaderId(Integer.parseInt(userId));
		groupInfo.setGroupName(groupName);
		groupInfo.setGroupIntro(groupIntro);
		String msg = userService.addGroup(groupInfo);
		return msg;
	}
	
	/**
	 * �û�id��Ⱥ
	 * @param uid3
	 * @return
	 */
	public String selectGroupByUserId(String uid3) {
		//1������У�� ǰ�˴��ݵ�ÿһ�����ݶ���У�� �ǿ�У�� ���ַ���У��
		if (uid3==null || uid3.equals("")) {
			return "10=404=�û����δ����";
		}
		
		String msg = userService.selectGroupByUserId(Integer.parseInt(uid3));
		return msg;
	}
	public String selectGroupHistoryChat(String groupId) {
		//1��У������
		if (groupId == null || groupId.equals("")) {
			return "11=404=Ⱥ��Idδ����";
		}
		//2������service�����Ϣ�Ĳ���
		String msg = userService.selectGroupHistoryChat(groupId);
		return msg;
	}
	
	/**
	 * ������Ϣ��Ⱥ��
	 * @param userId1
	 * @param groupId1
	 * @param message1
	 * @return
	 */
	public void addGroupMessage(String userId, String groupId, String message) {
		userService.addGroupMessage(userId,groupId,message);
	}
	
	/**
	 * �ؼ��ֲ鵱ǰ�û�����Ⱥ
	 * @param id4
	 * @param searchKey2
	 * @return
	 */
	public String searchGroupsByUserIdAndSearchKey(String userId, String searchKey) {
		//1��У������
		if (userId == null || userId.equals("")) {
			return "10=404=�û�Idδ����";
		}
		//2������service��ʵ�ֲ�ѯ�߼�
		String msg = userService.searchGroupsByUserIdAndSearchKey(userId, searchKey);
		return msg;
	}
	
	/**
	 * �ؼ��ֲ鵱ǰ�û�����Ⱥ
	 * @param id5
	 * @param searchKey3
	 * @return
	 */
	public String searchGroupsByUserIdAndSearchKeyForEntry(String userId, String searchKey) {
		//1��У������
		if (userId == null || userId.equals("")) {
			return "12=404=�û�Idδ����";
		}
		//2������service��ʵ�ֲ�ѯ�߼�
		System.out.println("contro");
		String msg = userService.searchGroupsByUserIdAndSearchKeyForEntry(userId, searchKey);
		return msg;
	}
	
	/**
	 * ����Ⱥ
	 * Ⱥ��Ա���м�����û�
	 * @param userId3
	 * @param groupId3
	 * @return
	 */
	public String entryGroups(String userId, String groupId) {
		//1��У������
		if (userId == null || userId.equals("")) {
			return "13=404=�û�Idδ����";
		}
		if (groupId == null || groupId.equals("")) {
			return "13=404=ȺIdδ����";
		}
		//2������service������ݵ����
		String msg = userService.entryGroups(userId, groupId);
		return msg;
	}
	
	/**
	 * ��Ⱥ
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public String exitGroups(String userId, String groupId) {
		//1��У������
		if (userId == null || userId.equals("")) {
			return "14=404=�û�Idδ����";
		}
		if (groupId == null || groupId.equals("")) {
			return "14=404=ȺIdδ����";
		}
		//2������service������ݵ����
		String msg = userService.exitGroups(userId, groupId);
		return msg;
	}
	
	/**
	 * ��ѯĳȺȺ��Ա�б�
	 * @param groupId
	 * @return
	 */
	public String selectGroupMemberByGroupId(String groupId) {
		//1������У�� ǰ�˴��ݵ�ÿһ�����ݶ���У�� �ǿ�У�� ���ַ���У��
		
		if (groupId==null || groupId.equals("")) {
			return "15=404=Ⱥ��δ����";
		}
		
		String msg = userService.selectGroupMemberByGroupId(groupId);
		return msg;
	}
	
	
}
