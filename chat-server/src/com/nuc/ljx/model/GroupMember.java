package com.nuc.ljx.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 群聊成员表对应的实体类
 * @author 11018
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMember {
	private Integer groupMemberId;
	private Integer groupId;
	private Integer memberId;
	private Date addTime;
}	
