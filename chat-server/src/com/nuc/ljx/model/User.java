package com.nuc.ljx.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * �û����Ӧ��ʵ����
 * @author 11018
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	private Integer userId;//�û�id
	private String nickname;
	private String username;
	private String password;
	private String sign;
	private String sex;
	private String header;
	private Date addTime;//��һ��datetime���� ������Java��Ӧ����java.sql.Date
}
