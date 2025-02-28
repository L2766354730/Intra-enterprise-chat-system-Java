package com.nuc.ljx.util;
/**
 * DBUtils是JDBC的工具类，将JDBC中常用的一些方法和步骤定义为一个个公开的方法，
 * 这样的话我们程序员在开发dao层的时候，就没必要去一步一步的去写JDBC连接的七步曲
 * 
 * 工具类是Java特殊的类，封装抽取我们多个类会用到的统一的方法
 * 工具类要求构造器必须私有化，工具类中的方法必须是静态方法
 * 
 * DBUtils中一般封装了四个方法
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
	 * 一个静态常量属性----数据库连接池
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
	 * 获取连接的方法
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		Connection connection = pools.getConnection();
		return connection;
	}
	/**
	 * 获取小推车 并且设置占位符值的
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
	 * 关闭资源的方法
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