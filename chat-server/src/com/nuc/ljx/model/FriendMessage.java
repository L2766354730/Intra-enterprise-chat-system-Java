package com.nuc.ljx.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 好友私聊消息的实体类
 * @author 11018
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendMessage {
	private Integer friendMessageId;
	private Integer senderId;
	private String senderNickName;//friend_message表中没有对应的字段
	private Integer receiverId;
	private String receiverNickName;//
	private String message;
	private Date sendTime;
}
