package com.nuc.ljx.util;

import com.nuc.ljx.page.AddFriendPage;
import com.nuc.ljx.page.AddGroupPage;
import com.nuc.ljx.page.EntryGroupPage;
import com.nuc.ljx.page.GroupChatListPage;
import com.nuc.ljx.page.GroupChatPage;
import com.nuc.ljx.page.GroupMemberPage;
import com.nuc.ljx.page.LoginPage;
import com.nuc.ljx.page.MainPage;
import com.nuc.ljx.page.ModifyPasswordPage;
import com.nuc.ljx.page.RegisterPage;
import com.nuc.ljx.page.SingelChatPage;
/**
 * 缓存页面的工具类
 * @author 11018
 *
 */
public class PageCacheUtil {
	public static String userId;
	public static String userName;
	public static String userNickname;
	public static LoginPage loginPage;
	public static MainPage mainPage;
	public static ModifyPasswordPage mpp;
	public static RegisterPage rp;
	public static AddFriendPage addFriendPage;
	public static SingelChatPage singelChatPage;
	public static AddGroupPage agpAddGroupPage;
	public static GroupChatListPage groupChatListPage;
	public static GroupChatPage groupChatPage;
	public static EntryGroupPage entryGroupPage;
	public static GroupMemberPage groupMemberPage;
}
