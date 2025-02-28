package com.nuc.ljx.test;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.nuc.ljx.model.User;

/**
 * json对象格式 
 *     { "name":"value", "name":"value" } 
 *   json数组格式 
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
		//将一个Java对象转成json对象的字符串格式
		String jsonString = JSON.toJSONString(user,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		System.out.println(jsonString);
		//将一个Java集合或者数据转换成未json数组的字符串格式
		String jsonString2 = JSON.toJSONString(list,SerializerFeature.WRITE_MAP_NULL_FEATURES);
		System.out.println(jsonString2);
	}
}
