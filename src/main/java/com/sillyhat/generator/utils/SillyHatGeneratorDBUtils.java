//package com.sillyhat.generator.utils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.paic.learn.adminapi.common.code.generator.dto.Database;
//
//public class SillyHatGeneratorDBUtils {
//
//	private SillyHatGeneratorDBUtils(){
//
//	}
//
//	private static SillyHatGeneratorDBUtils instance = null;
//
//	private static Database database;
//
//	public static SillyHatGeneratorDBUtils getInstance(Database db) {
//		if (instance == null) {
//			instance = new SillyHatGeneratorDBUtils();
//		}
//		if (database == null) {
//			database = new Database();
//		}
//		database = db;
//		return instance;
//	}
//
//
//	public Connection getConnection(){
//		String dbtype = database.getDbtype();
//		String sid = database.getSid();
//		String name = database.getUser();
//		String pass = database.getPassword();
//		String ip = database.getIp();
//		String port = database.getPort();
//		Connection conn = null;
//		if(dbtype.equalsIgnoreCase(GeneratorConstants.DATABASE_SORT_ORACLE)){
//			try{
//				Class.forName("oracle.jdbc.driver.OracleDriver");
//				String url="jdbc:oracle:thin:@//"+ip+":"+port+"/"+sid;
//				conn= DriverManager.getConnection(url,name,pass);
//			}catch(Exception e){
//				System.out.println("getConnection error" + e.getMessage());
//				System.out.println("getConnection error" + e);
//			}
//		}else if(dbtype.equalsIgnoreCase(GeneratorConstants.DATABASE_SORT_MYSQL)){
//			try{
//				Class.forName("com.mysql.jdbc.Driver");
//				String url="jdbc:mysql://"+ip+":"+port+"/"+sid;
//				conn= DriverManager.getConnection(url,name,pass);
//			}catch(Exception e){
//				System.out.println("getConnection error" + e.getMessage());
//				System.out.println("getConnection error" + e);
//			}
//		}
//		return conn;
//	}
//
//	/**
//	 * <p>Title: query</p>
//	 * <p>Description: </p>查询sql
//	 * @param @param db
//	 * @param @param sql
//	 * @param @return
//	 * @author 徐士宽
//	 * @date 2015-12-2
//	 * @return:List<Map<String,Object>>
//	 */
//	public List<Map<String,Object>> query(String sql){
//		Connection conn = getConnection();
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		try {
//			PreparedStatement pstam = conn.prepareStatement(sql);
//			ResultSet rs = pstam.executeQuery();// 执行sql语句
//			// 用于获取列数、或者列类型
//			ResultSetMetaData meta = rs.getMetaData();
//			while (rs.next()) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				for (int i = 1; i <= meta.getColumnCount(); i++) {
//					String key = meta.getColumnName(i);//当前列名
//					Object value = rs.getObject(i);
//					map.put(key, value);
//				}
//				list.add(map);
//			}
//			close(null, pstam, rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//	public static void close(Connection con , PreparedStatement p , ResultSet rs){
//		if(con != null){
//			try {
//				con.close();
//			} catch (SQLException e) {
//				System.out.println("Connection close error" + e.getMessage());
//			}
//		}
//		if(p != null){
//			try {
//				p.close();
//			} catch (SQLException e) {
//				System.out.println("PreparedStatement close error" + e.getMessage());
//			}
//		}
//		if(rs != null){
//			try {
//				rs.close();
//			} catch (SQLException e) {
//				System.out.println("ResultSet close error" + e.getMessage());
//			}
//		}
//	}
//
//	public static void main(String args[]){
//		String sid = "d0hrmln";
//		String name = "mlearndata";
//		String pass = "paic4321";
//		String ip = "10.20.128.24";
//		String port = "1526";
//		String dbtype = GeneratorConstants.DATABASE_SORT_ORACLE;
//		Database database = new Database();
//    	database.setDbtype(dbtype);
//    	database.setSid(sid);
//    	database.setIp(ip);
//    	database.setPort(port);
//    	database.setUser(name);
//    	database.setPassword(pass);
//		Connection con = SillyHatGeneratorDBUtils.getInstance(database).getConnection();
//		if(con == null){
//			System.out.println("连接失败");
//		}else{
//			System.out.println(con);
//			System.out.println("连接成功");
//		}
//	}
//}
//
