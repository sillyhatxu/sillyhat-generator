package com.sillyhat.generator.main;

import com.sillyhat.generator.dto.CreatedDTO;
import com.sillyhat.generator.dto.TemplatePathDTO;
import com.sillyhat.generator.utils.SillyHatGeneratorConstants;
import com.sillyhat.generator.utils.SillyHatGeneratorFileUtils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

/**
 * CreatedMain
 *
 * @author 徐士宽
 * @date 2017/4/10 17:01
 */
public class CreatedMain {

    private static Logger logger = LoggerFactory.getLogger(CreatedMain.class);

    private volatile static CreatedMain instance;

    private CreatedMain(){

    }

    /**
     * Oracle数据类型
     */
    public static int DATABASE_TYPE_ORACLE = 1;

    /**
     * Mysql数据类型
     */
    public static int DATABASE_TYPE_MYSQL = 2;


    public static CreatedMain getInstance() {
        if (instance == null) {
            synchronized (CreatedMain.class) {
                if (instance == null) instance = new CreatedMain();
            }
        }
        return instance;
    }


    /**
     * 根据默认模板，生成文件
     * @param outFilePath   输出路径
     * @param author        创建人
     * @param tableName     数据库表名称
     * @param databaseType  数据库类型
     */
    public void createDefault(CreatedDTO dto, int databaseType){
        created(dto,databaseType);
    }

    /**
     * 根据自己的模板，生成文件
     * @param outFilePath   输出路径
     * @param author        创建人
     * @param tableName     数据库表名称
     * @param databaseType  数据库类型
     * @param dto            需要生成的文件列表
     */
    public void createByMyTemplate(CreatedDTO dto, int databaseType){
        created(dto,databaseType);
    }



    private void created(CreatedDTO dto, int databaseType){
        this.outFilePath = dto.getOutFilePath();
        this.author = dto.getAuthor();
        this.tableName = dto.getTableName();
        this.packageName = dto.getPackageName();
        this.moduelName = dto.getModuelName();
        this.entityName = dto.getEntityName();
        this.templateList = dto.getTemplateList();
        this.databaseType = databaseType;
        if(createdCheck()){
            if(dto.isUseDefaultTemplate()){
                executeSQL();//执行SQL
                createdDTO();
            }else{
                logger.error("未开发…………………………………………");
            }
        }
    }

    private void createdDTO(){
        //使用默认模板    {0}:作者; {1}:包名; {2}:模块名; {3}:实体名
        String[] fileReplaceSign = new String[] {
                author,packageName,moduelName,entityName,"4444","5555","6666","7777","8888"
        };

    }

    private void createdFile(String outFilePath,String outFileName,String[] fileReplaceSign,String templatePathNamt){
        try {
            String result = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(SillyHatGeneratorConstants.TEMPLATE_PATH_NAMT_DTO), SillyHatGeneratorConstants.ENCODING_UTF8);
            result = MessageFormat.format(result, fileReplaceSign);


        } catch (IOException e) {
            logger.error("文件处理发生异常",e);
        }
    }

    private void executeSQL(){
        String sql = getSql();


    }

    private String getSql(){
        if(databaseType == DATABASE_TYPE_MYSQL){
            return MessageFormat.format(SillyHatGeneratorConstants.DATABASE_MYSQL_SQL, tableName);
        }else if(databaseType == DATABASE_TYPE_ORACLE){
            return MessageFormat.format(SillyHatGeneratorConstants.DATABASE_MYSQL_SQL, tableName);
        }else{
            logger.error("不支持的数据库类型");
            return "";
        }
    }

    /**
     * 校验参数
     * @param outFilePath   输出路径
     * @param author        创建人
     * @param tableName     数据库表名称
     * @param databaseType  数据库类型
     * @return
     */
    public boolean createdCheck(){
        boolean check = false;
        if(outFilePath == null || "".equals(outFilePath)){
            logger.error("outFilePath");
        }else if(author  == null || "".equals(author)){
            logger.error("author");
        }else if(tableName  == null || "".equals(tableName)){
            logger.error("tableName必填");
        }else if(databaseType != DATABASE_TYPE_MYSQL && databaseType!= DATABASE_TYPE_ORACLE){
            logger.error("不支持的数据库类型");
        }else{
            check = true;
        }
        return check;
    }

    private int databaseType;
    private String outFilePath;
    private String author;
    private String tableName;
    private String packageName;
    private String moduelName;
    private String entityName;
    private List<TemplatePathDTO> templateList;


    public static void main(String[] args) {
        String outFilePath = "E:\\Log\\Created";
        String author = "XUSHIKUAN";
        String tableName = "t_learning_word_repository";
        String packageName = "";
        String moduelName = "";
        String entityName = "";
        CreatedDTO dto = new CreatedDTO(outFilePath,author,tableName,packageName,moduelName,entityName);
        CreatedMain.getInstance().createDefault(dto,CreatedMain.DATABASE_TYPE_MYSQL);
    }
}
