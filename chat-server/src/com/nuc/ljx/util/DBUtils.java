package com.nuc.ljx.util;
/**
 * DBUtils��JDBC�Ĺ����࣬��JDBC�г��õ�һЩ�����Ͳ��趨��Ϊһ���������ķ�����
 * �����Ļ����ǳ���Ա�ڿ���dao���ʱ�򣬾�û��Ҫȥһ��һ����ȥдJDBC���ӵ��߲���
 * 
 * ��������Java������࣬��װ��ȡ���Ƕ������õ���ͳһ�ķ���
 * ������Ҫ����������˽�л����������еķ��������Ǿ�̬����
 * 
 * DBUtils��һ���װ���ĸ�����
 *  getConnection():Connection
 *  getStatement(Connection conn,String sql):PreparedStatement
 *  closeResources(PreparedStatement,Connection)
 *  closeResources(ResultSet,PreparedStatement,Connecion)
 * @author 11018
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DBUtils {
	/**
	 * һ����̬��������----���ݿ����ӳ�
	 */
	private static DataSource pools;
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("druid.properties")));
			pools = DruidDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private DBUtils() {
		
	}
	
	/**
	 * ��ȡ���ӵķ���
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		Connection connection = pools.getConnection();
		return connection;
	}
	/**
	 * ��ȡС�Ƴ� ��������ռλ��ֵ��
	 * @throws SQLException 
	 */
	public static PreparedStatement getStatement(Connection connection,String sql,Object... params) throws SQLException {
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			prepareStatement.setObject(i+1, param);
		}
		return prepareStatement;
	}
	/**
	 * �ر���Դ�ķ���
	 * @throws SQLException 
	 */
	public static void closeResources(PreparedStatement preparedStatement,Connection connection) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void closeResources(ResultSet resultSet,PreparedStatement preparedStatement,Connection connection) {
		
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}