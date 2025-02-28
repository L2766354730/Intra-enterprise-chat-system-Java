package com.nuc.ljx.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ���ѱ��Ӧ��ʵ����
 * @author 11018
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Friends {
	private Integer friendId;
	private Integer userId;
	private Integer userFriendId;
	private Date addTime;
}
