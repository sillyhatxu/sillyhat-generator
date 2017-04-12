package com.sillyhat.generator.utils;

import com.sillyhat.generator.dto.SillyHatGeneratorEntityDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SillyHatGeneratorDBUtils {

    private static Logger logger = LoggerFactory.getLogger(SillyHatGeneratorDBUtils.class);

    private volatile static SillyHatGeneratorDBUtils instance;

    private SillyHatGeneratorDBUtils(){

    }

    public static SillyHatGeneratorDBUtils getInstance() {
        if (instance == null) {
            synchronized (SillyHatGeneratorDBUtils.class) {
                if (instance == null) instance = new SillyHatGeneratorDBUtils();
            }
        }
        return instance;
    }

    /**
     * 获得数据库连接
     * @param databaseType
     * @param databaseDriverClassName
     * @param databaseUrl
     * @param databaseUserName
     * @param databasePassword
     * @return
     */
    public Connection getConnection(int databaseType,String databaseDriverClassName,String databaseUrl,String databaseUserName,String databasePassword){
        Connection connection = null;
        try {
            Class.forName(databaseDriverClassName);
            connection = DriverManager.getConnection(databaseUrl,databaseUserName,databasePassword);
        } catch (ClassNotFoundException e) {
            logger.error("Class.forName 发生异常",e);
        } catch (SQLException e) {
            logger.error("获得连接发生异常",e);
        }
        return connection;
    }


    /**
     * <p>Title: query</p>
     * <p>Description: </p>查询sql
     * @param connection
     * @param sql
     * @author 徐士宽
     * @date 2015-12-2
     * @return:List<Map<String,Object>>
     */
    public List<SillyHatGeneratorEntityDTO> query(Connection connection, String sql){
        List<SillyHatGeneratorEntityDTO> resultList = new ArrayList<SillyHatGeneratorEntityDTO>();
        PreparedStatement pstam = null;
        ResultSet rs = null;
        try {
            pstam = connection.prepareStatement(sql);
            rs = pstam.executeQuery();// 执行sql语句

            SillyHatGeneratorEntityDTO dto = null;
            while(rs.next()){
                dto = new SillyHatGeneratorEntityDTO();
                dto.setIsKey(rs.getString("IS_KEY") != null && rs.getString("IS_KEY").equals("1"));//存储DATA_TYPE时会用到此字段，所以优先存储
                dto.setColumnName(rs.getString("COLUMN_NAME"));
                dto.setComments(rs.getString("COMMENTS"));
                dto.setDataType(rs.getString("DATA_TYPE"));
                resultList.add(dto);
            }
            // 用于获取列数、或者列类型
//            ResultSetMetaData meta = rs.getMetaData();
//            while (rs.next()) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                for (int i = 1; i <= meta.getColumnCount(); i++) {
//                    String key = meta.getColumnName(i);//当前列名
//                    if(key.equals("COLUMN_COMMENT")){
//                        key = "COMMENTS";
//                    }
//                    Object value = rs.getObject(i);
//                    map.put(key, value);
//                }
//                list.add(map);
//            }
        } catch (SQLException e) {
            logger.error("查询出现异常。",e);
        } finally {
            close(null, pstam, rs);
        }
        return resultList;
    }

    public static void close(Connection con , PreparedStatement p , ResultSet rs){
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                logger.error("Connection close error",e);
            }
        }
        if(p != null){
            try {
                p.close();
            } catch (SQLException e) {
                logger.error("PreparedStatement close error",e);
            }
        }
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("ResultSet close error",e);
            }
        }
    }
}

