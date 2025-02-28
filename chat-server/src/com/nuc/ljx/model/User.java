package com.nuc.ljx.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表对应的实体类
 * @author 11018
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	private Integer userId;//用户id
	private String nickname;
	private String username;
	private String password;
	private String sign;
	private String sex;
	private String header;
	private Date addTime;//是一个datetime类型 类型在Java对应的是java.sql.Date
}
