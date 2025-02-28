package com.nuc.ljx.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 群聊消息对应的实体类
 * @author 11018
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMessage {
	private Integer groupMessageId;
	private Integer groupId;
	private Integer memberId;
	private String sendMemberName;
	private String message;
	private Date sendTime;
}
