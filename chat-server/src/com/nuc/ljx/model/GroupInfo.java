package com.nuc.ljx.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ⱥ��Ϣ���ʵ����
 * @author 11018
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupInfo {
	private Integer groupId;
	private String groupNumber;
	private String groupName;
	private Integer groupLeaderId;
	private String groupLeaderName;
	private String groupIntro;
	private Date createTime;
}
