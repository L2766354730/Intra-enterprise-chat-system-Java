package com.nuc.ljx.test;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.nuc.ljx.model.User;

/**
 * json�����ʽ 
 *     { "name":"value", "name":"value" } 
 *   json�����ʽ 
 *   [ 
 *      { "name":"value","name":"value" },
 *      { "name":"value", "name":"value" }, 
 *   ]
 * 
 * @author 11018
 *
 */
public class JSONDemo {
	public static void main(String[] args) {
		User user =new User();
		List<User> list = new ArrayList<User>();
		list.add(user);
		//��һ��Java����ת��json������ַ�����ʽ
		String jsonString = JSON.toJSONString(user,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		System.out.println(jsonString);
		//��һ��Java���ϻ�������ת����δjson������ַ�����ʽ
		String jsonString2 = JSON.toJSONString(list,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		System.out.println(jsonString2);
	}
}
