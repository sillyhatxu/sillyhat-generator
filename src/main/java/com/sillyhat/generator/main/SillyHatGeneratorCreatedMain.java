package com.sillyhat.generator.main;

import com.sillyhat.generator.cache.SillyHatGeneratorCache;
import com.sillyhat.generator.dto.SillyHatGeneratorCreatedDTO;
import com.sillyhat.generator.dto.SillyHatGeneratorEntityDTO;
import com.sillyhat.generator.dto.SillyHatGeneratorTemplatePathDTO;
import com.sillyhat.generator.interceptor.SillyHatGeneratorTypeMappingInterceptor;
import com.sillyhat.generator.utils.SillyHatGeneratorConstants;
import com.sillyhat.generator.utils.SillyHatGeneratorDBUtils;
import com.sillyhat.generator.utils.SillyHatGeneratorFileUtils;
import com.sillyhat.generator.utils.SillyHatGeneratorStringUtils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * SillyHatGeneratorCreatedMain
 *
 * @author 徐士宽
 * @date 2017/4/10 17:01
 */
public class SillyHatGeneratorCreatedMain {

    private static Logger logger = LoggerFactory.getLogger(SillyHatGeneratorCreatedMain.class);

    private volatile static SillyHatGeneratorCreatedMain instance;

    private SillyHatGeneratorCreatedMain(){

    }

    public static SillyHatGeneratorCreatedMain getInstance() {
        if (instance == null) {
            synchronized (SillyHatGeneratorCreatedMain.class) {
                if (instance == null) instance = new SillyHatGeneratorCreatedMain();
            }
        }
        return instance;
    }


    /**
     * 根据默认模板，生成文件
     * @param SillyHatGeneratorCreatedDTO    必录字段实体类
     * @param databaseType                     数据库类型
     * @param usePackagePath                   使用包路径
     */
    public void createDefault(SillyHatGeneratorCreatedDTO dto, int databaseType,boolean usePackagePath,boolean createdFieldMerge){
        created(dto,databaseType,usePackagePath,createdFieldMerge);
    }

    /**
     * 根据自己的模板，生成文件
     * @param SillyHatGeneratorCreatedDTO    必录字段实体类
     * @param databaseType                     数据库类型
     * @param usePackagePath                   使用包路径
     */
    public void createByMyTemplate(SillyHatGeneratorCreatedDTO dto, int databaseType,boolean usePackagePath,boolean createdFieldMerge){
        created(dto,databaseType,usePackagePath,createdFieldMerge);
    }



    private void created(SillyHatGeneratorCreatedDTO dto, int databaseType,boolean usePackagePath,boolean createdFieldMerge){
        this.createdFieldMerge = createdFieldMerge;
        this.usePackagePath = usePackagePath;
        this.databaseDriverClassName = dto.getDatabaseDriverClassName();
        this.databaseUrl = dto.getDatabaseUrl();
        this.databaseUserName = dto.getDatabaseUserName();
        this.databasePassword = dto.getDatabasePassword();
        this.outFilePath = SillyHatGeneratorFileUtils.getDirPath(dto.getOutFilePath());
        this.author = dto.getAuthor();
        this.tableName = dto.getTableName();
        this.packageName = dto.getPackageName();
        this.moduelName = dto.getModuelName();
        this.entityName = dto.getEntityName();
        this.templateList = dto.getTemplateList();
        this.databaseType = databaseType;
        SillyHatGeneratorCache.getInstance().setDatabaseType(databaseType);
        SillyHatGeneratorCache.getInstance().setAuthor(author);
        if(createdCheck()){
            if(dto.isUseDefaultTemplate()){
                executeSQL();//执行SQL
                writeDTO();
                writeService();
                writeServiceImpl();
                writeMapper();
                writeMapping();
                writeTest();
            }else{
                logger.error("未开发…………………………………………");
            }
        }
    }

    private void writeDTO(){
        StringBuffer entity = new StringBuffer();
        StringBuffer getMethod = new StringBuffer();
        StringBuffer setMethod = new StringBuffer();
        StringBuffer otherImport = new StringBuffer();
        StringBuffer toString = new StringBuffer();
        Set<String> otherImportSet = new HashSet<String>();
        toString.append(SillyHatGeneratorConstants.SIGN_TAB).append("@Override").append(SillyHatGeneratorConstants.SIGN_ENTER);
        toString.append(SillyHatGeneratorConstants.SIGN_TAB).append("public String toString() {").append(SillyHatGeneratorConstants.SIGN_ENTER);
        toString.append(SillyHatGeneratorStringUtils.getSignX(SillyHatGeneratorConstants.SIGN_TAB, 2)).append("return \"").append(entityName).append("{\" +").append(SillyHatGeneratorConstants.SIGN_ENTER);
        for (int i = 0; i < entityList.size(); i++) {
            SillyHatGeneratorEntityDTO entityDTO = entityList.get(i);
            String entityFieldType = entityDTO.getEntityFieldType();
            String comment = entityDTO.getComments();
            String entityFieldName = entityDTO.getEntityFieldName();
            String importPackage = SillyHatGeneratorTypeMappingInterceptor.getImportPackage(entityFieldType,entityFieldName);
            if(importPackage != null && !"".equals(importPackage)) otherImportSet.add(importPackage);//存在需要导入的包
            if(i + 1 == entityList.size()){
                toString.append(SillyHatGeneratorStringUtils.getSignX(SillyHatGeneratorConstants.SIGN_TAB, 3)).append("\"").append(entityFieldName).append("='\" + ");
                toString.append(entityFieldName).append(" + '").append("\\'' +").append(SillyHatGeneratorConstants.SIGN_ENTER);
            }else{
                toString.append(SillyHatGeneratorStringUtils.getSignX(SillyHatGeneratorConstants.SIGN_TAB, 3)).append("\"").append(entityFieldName).append("='\" + ");
                toString.append(entityFieldName).append(" + '").append("\\'' +  \",\" +").append(SillyHatGeneratorConstants.SIGN_ENTER);
            }
            entity.append(SillyHatGeneratorConstants.SIGN_TAB).append("private ").append(entityFieldType).append(" ").append(entityFieldName).append(";");
            entity.append(SillyHatGeneratorConstants.SIGN_ANNOTATION).append(comment).append(SillyHatGeneratorConstants.SIGN_ENTER).append(SillyHatGeneratorConstants.SIGN_ENTER);
            getMethod.append(SillyHatGeneratorStringUtils.getFieldGetMethod(SillyHatGeneratorConstants.SIGN_TAB, entityFieldName, entityFieldType, comment));
            setMethod.append(SillyHatGeneratorStringUtils.getFieldSetMethod(SillyHatGeneratorConstants.SIGN_TAB, entityFieldName, entityFieldType, comment));
        }
        toString.append(SillyHatGeneratorStringUtils.getSignX(SillyHatGeneratorConstants.SIGN_TAB, 3)).append("'}';").append(SillyHatGeneratorConstants.SIGN_ENTER);
        toString.append(SillyHatGeneratorConstants.SIGN_TAB).append("}").append(SillyHatGeneratorConstants.SIGN_ENTER);
        Iterator<String> iter = otherImportSet.iterator();
        while(iter.hasNext()){
            otherImport.append("import ").append(iter.next()).append(";").append(SillyHatGeneratorConstants.SIGN_ENTER);
        }
        //使用默认模板    {0}:作者; {1}:包名; {2}:模块名; {3}:实体名
        String[] fileReplaceSign = new String[] {
                author,packageName,moduelName,entityName,otherImport.toString(),entity.toString(),getMethod.toString(),setMethod.toString(),toString.toString()
        };

        /*******输出文件*******/
        String outPath;
        if(usePackagePath){
            //拼写输出路径
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath)
                    + SillyHatGeneratorFileUtils.getDirPath(moduelName);
            if(!createdFieldMerge) outPath += SillyHatGeneratorFileUtils.getDirPath("dto");
            outPath += SillyHatGeneratorFileUtils.getDirPath(SillyHatGeneratorFileUtils.packageToPath(packageName))
                    + SillyHatGeneratorFileUtils.getDirPath(moduelName)
                    + SillyHatGeneratorFileUtils.getDirPath(SillyHatGeneratorFileUtils.packageToPath("dto"));
        }else{
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath);
        }
        createdFile(outPath,entityName+"DTO.java",fileReplaceSign,SillyHatGeneratorConstants.TEMPLATE_PATH_NAMT_DTO);
        logger.info("..........创建DTO成功..........");
    }

    private void writeService(){
        String[] fileReplaceSign = new String[] {
                author,packageName,moduelName,entityName,SillyHatGeneratorCache.getInstance().getCreatedDate()
        };
        /*******输出文件*******/
        String outPath;
        if(usePackagePath){
            //拼写输出路径
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath)
                    + SillyHatGeneratorFileUtils.getDirPath(moduelName);
            if(!createdFieldMerge) outPath += SillyHatGeneratorFileUtils.getDirPath("service");
            outPath += SillyHatGeneratorFileUtils.getDirPath(SillyHatGeneratorFileUtils.packageToPath(packageName))
                    + SillyHatGeneratorFileUtils.getDirPath(moduelName)
                    + SillyHatGeneratorFileUtils.getDirPath(SillyHatGeneratorFileUtils.packageToPath("service"));
        }else{
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath);
        }
        createdFile(outPath,entityName+"Service.java",fileReplaceSign,SillyHatGeneratorConstants.TEMPLATE_PATH_NAMT_SERVICE);
        logger.info("..........创建Service成功..........");
    }

    private void writeServiceImpl(){
        String[] fileReplaceSign = new String[] {
                author,packageName,moduelName,entityName,SillyHatGeneratorStringUtils.toLowerCaseSrc(entityName)
        };
        /*******输出文件*******/
        String outPath;
        if(usePackagePath){
            //拼写输出路径
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath)
                    + SillyHatGeneratorFileUtils.getDirPath(moduelName);
            if(!createdFieldMerge) outPath += SillyHatGeneratorFileUtils.getDirPath("impl");
            outPath += SillyHatGeneratorFileUtils.getDirPath(SillyHatGeneratorFileUtils.packageToPath(packageName))
                    + SillyHatGeneratorFileUtils.getDirPath(moduelName)
                    + SillyHatGeneratorFileUtils.getDirPath(SillyHatGeneratorFileUtils.packageToPath("service.impl"));
        }else{
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath);
        }
        createdFile(outPath,entityName+"ServiceImpl.java",fileReplaceSign,SillyHatGeneratorConstants.TEMPLATE_PATH_NAMT_SERVICE_IMPL);
        logger.info("..........创建ServiceImpl成功..........");
    }

    private void writeMapper(){
        String[] fileReplaceSign = new String[] {
                author,packageName,moduelName,entityName,SillyHatGeneratorCache.getInstance().getCreatedDate()
        };
        /*******输出文件*******/
        String outPath;
        if(usePackagePath){
            //拼写输出路径
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath)
                    + SillyHatGeneratorFileUtils.getDirPath(moduelName);
            if(!createdFieldMerge) outPath += SillyHatGeneratorFileUtils.getDirPath("mapper");
            outPath += SillyHatGeneratorFileUtils.getDirPath(SillyHatGeneratorFileUtils.packageToPath(packageName))
                    + SillyHatGeneratorFileUtils.getDirPath(moduelName)
                    + SillyHatGeneratorFileUtils.getDirPath(SillyHatGeneratorFileUtils.packageToPath("mapper"));
        }else{
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath);
        }
        createdFile(outPath,entityName+"Mapper.java",fileReplaceSign,SillyHatGeneratorConstants.TEMPLATE_PATH_NAMT_MAPPER);
        logger.info("..........创建Mapper成功..........");
    }


    private void writeMapping(){
        //使用默认模板    {0}:作者; {1}:包名; {2}:模块名; {3}:实体名  {4} 表名 {5} 全列显示(除ID)
        //{6} insert字段 {7} update列 {8}updateByPrimaryKeySelective列{9}分页语句
        String[] fileReplaceSign = new String[] {
            author,packageName,moduelName,entityName,tableName,getALLColumnn(),getInsertField(),getUpdateField(),getUpdateByPrimaryKeySelectivField(),getPageSQL()
        };
        /*******输出文件*******/
        String outPath;
        if(usePackagePath){
            //拼写输出路径
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath)
                    + SillyHatGeneratorFileUtils.getDirPath(moduelName);
            if(!createdFieldMerge) outPath += SillyHatGeneratorFileUtils.getDirPath("mapping");
            outPath += SillyHatGeneratorFileUtils.getDirPath(SillyHatGeneratorFileUtils.packageToPath(packageName))
                    + SillyHatGeneratorFileUtils.getDirPath(moduelName)
                    + SillyHatGeneratorFileUtils.getDirPath(SillyHatGeneratorFileUtils.packageToPath("mapper"));
        }else{
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath);
        }
        createdFile(outPath,entityName+"Mapper.xml",fileReplaceSign,SillyHatGeneratorConstants.TEMPLATE_PATH_NAMT_MAPPING);
        logger.info("..........创建Mapping成功..........");
    }

    private void writeTest(){
        StringBuffer setString = new StringBuffer();
        for (int i = 0; i < entityList.size(); i++) {
            SillyHatGeneratorEntityDTO entityDTO = entityList.get(i);
            setString.append(SillyHatGeneratorStringUtils.getSignX(SillyHatGeneratorConstants.SIGN_TAB, 2));
            setString.append("dto.set").append(entityDTO.getEntityFieldName().substring(0,1).toUpperCase());
            setString.append(entityDTO.getEntityFieldName().substring(1,entityDTO.getEntityFieldName().length()));
            setString.append("(").append(SillyHatGeneratorTypeMappingInterceptor.getTestSetValue(entityDTO.getEntityFieldType())).append(");");
            setString.append(SillyHatGeneratorConstants.SIGN_ANNOTATION).append(entityDTO.getComments());
            setString.append(SillyHatGeneratorConstants.SIGN_ENTER);
        }
        String[] fileReplaceSign = new String[] {
                author,packageName,moduelName,entityName,SillyHatGeneratorStringUtils.toLowerCaseSrc(entityName),SillyHatGeneratorCache.getInstance().getCreatedDate(),setString.toString()
        };
        /*******输出文件*******/
        String outPath;
        if(usePackagePath){
            //拼写输出路径
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath) + SillyHatGeneratorFileUtils.getDirPath(moduelName);
        }else{
            outPath = SillyHatGeneratorFileUtils.getDirPath(outFilePath);
        }
        createdFile(outPath,entityName+"Test.java",fileReplaceSign,SillyHatGeneratorConstants.TEMPLATE_PATH_NAMT_TEST);
        logger.info("..........创建Test成功..........");
    }

    /**
     * {5} 全列显示(除ID)
     * @return
     */
    private String getALLColumnn(){
        String result = "";
        for (int i = 0; i < entityList.size(); i++) {
            SillyHatGeneratorEntityDTO entityDTO = entityList.get(i);
            result += entityDTO.getColumnName() + ",";
        }
        return result.length() > 0 ? result.substring(0,result.length() - 1) : "";
    }

    /**
     * {6} insert字段
     * @return
     */
    private String getInsertField(){
        String result = "";
        for (int i = 0; i < entityList.size(); i++) {
            SillyHatGeneratorEntityDTO entityDTO = entityList.get(i);
            result += SillyHatGeneratorStringUtils.getSignX(SillyHatGeneratorConstants.SIGN_TAB, 3) + "#{" + entityDTO.getEntityFieldName() + "}," + SillyHatGeneratorConstants.SIGN_ENTER;
        }
        return result.length() > 0 ? result.substring(0,result.length() - 1) : "";
    }

    /**
     * {7} update列
     * @return
     */
    private String getUpdateField(){
        String result = "";
        for (int i = 0; i < entityList.size(); i++) {
            SillyHatGeneratorEntityDTO entityDTO = entityList.get(i);
            result += SillyHatGeneratorStringUtils.getSignX(SillyHatGeneratorConstants.SIGN_TAB, 3) + entityDTO.getColumnName() + " = #{" + entityDTO.getEntityFieldName() + "}," + SillyHatGeneratorConstants.SIGN_ENTER;
        }
        return result.length() > 0 ? result.substring(0,result.length() - 1) : "";
    }

    /**
     * {8}updateByPrimaryKeySelective列
     * @return
     */
    private String getUpdateByPrimaryKeySelectivField(){
        String result = "";
        for (int i = 0; i < entityList.size(); i++) {
            SillyHatGeneratorEntityDTO entityDTO = entityList.get(i);
            result += SillyHatGeneratorStringUtils.getSignX(SillyHatGeneratorConstants.SIGN_TAB, 3) + "<isNotEmpty prepend=\",\" property=\"" + entityDTO.getEntityFieldName() + "\">" + SillyHatGeneratorConstants.SIGN_ENTER;
            result += SillyHatGeneratorStringUtils.getSignX(SillyHatGeneratorConstants.SIGN_TAB, 4) + entityDTO.getColumnName() + " = #{" + entityDTO.getEntityFieldName() + "},"+ SillyHatGeneratorConstants.SIGN_ENTER;
            result += SillyHatGeneratorStringUtils.getSignX(SillyHatGeneratorConstants.SIGN_TAB, 3) + "</isNotEmpty>" + SillyHatGeneratorConstants.SIGN_ENTER;
        }
        return result.length() > 0 ? result.substring(0,result.length() - 1) : "";
    }

    /**
     * {9}分页语句
     * @return
     */
    private String getPageSQL(){
        String result = "";
        if(databaseType == SillyHatGeneratorConstants.DATABASE_TYPE_ORACLE){

        }else if(databaseType == SillyHatGeneratorConstants.DATABASE_TYPE_MYSQL){
            result += "select" + SillyHatGeneratorConstants.SIGN_ENTER;
            for (int i = 0; i < entityList.size(); i++) {
                SillyHatGeneratorEntityDTO entityDTO = entityList.get(i);
                result += SillyHatGeneratorStringUtils.getSignX(SillyHatGeneratorConstants.SIGN_TAB, 3) + entityDTO.getColumnName() + " as " + entityDTO.getEntityFieldName() + "," + SillyHatGeneratorConstants.SIGN_ENTER;
            }
            result = result.length() > 0 ? result.substring(0,result.length() - 1) : "";
            result += "from " + tableName + " t" + SillyHatGeneratorConstants.SIGN_ENTER;
            result += "limit #{startIndex},#{pageSize}" + SillyHatGeneratorConstants.SIGN_ENTER;
        }
        return result;
    }


    private void createdFile(String outFilePath,String outFileName,String[] fileReplaceSign,String templatePathNamt){
        try {
            List<String> resultLines = new ArrayList<String>();
            String result = SillyHatGeneratorFileUtils.readText(templatePathNamt);
            result = MessageFormat.format(result, fileReplaceSign);
            resultLines.add(result);
            File file = new File(outFilePath + outFileName);
            SillyHatGeneratorFileUtils.touch(file);
            SillyHatGeneratorFileUtils.writeLines(file , resultLines);
        } catch (IOException e) {
            logger.error("文件处理发生异常",e);
        }
    }

    private void executeSQL(){
        String sql = getSql();
        SillyHatGeneratorDBUtils dbUtils = SillyHatGeneratorDBUtils.getInstance();
        Connection connection = dbUtils.getConnection(databaseType,databaseDriverClassName,databaseUrl,databaseUserName,databasePassword);
        entityList = dbUtils.query(connection,sql);
    }

    private String getSql(){
        if(databaseType == SillyHatGeneratorConstants.DATABASE_TYPE_MYSQL){
            return MessageFormat.format(SillyHatGeneratorFileUtils.readText(SillyHatGeneratorConstants.TEMPLATE_PATH_DATABASE_MYSQL), tableName);
        }else if(databaseType == SillyHatGeneratorConstants.DATABASE_TYPE_ORACLE){
            return MessageFormat.format(SillyHatGeneratorFileUtils.readText(SillyHatGeneratorConstants.TEMPLATE_PATH_DATABASE_ORACLE), tableName);
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
    private boolean createdCheck(){
        boolean check = false;
        if(outFilePath == null || "".equals(outFilePath)){
            logger.error("outFilePath");
        }else if(author  == null || "".equals(author)){
            logger.error("author");
        }else if(tableName  == null || "".equals(tableName)){
            logger.error("tableName必填");
        }else if(databaseType != SillyHatGeneratorConstants.DATABASE_TYPE_MYSQL && databaseType!= SillyHatGeneratorConstants.DATABASE_TYPE_ORACLE){
            logger.error("不支持的数据库类型");
        }else{
            check = true;
        }
        return check;
    }

    private String databaseDriverClassName;

    private String databaseUrl;

    private String databaseUserName;

    private String databasePassword;

    private int databaseType;

    private String outFilePath;

    private String author;

    private String tableName;

    private String packageName;

    private String moduelName;

    private String entityName;

    private boolean usePackagePath;

    private boolean createdFieldMerge;

    private List<SillyHatGeneratorTemplatePathDTO> templateList;

    private List<SillyHatGeneratorEntityDTO> entityList;

}
