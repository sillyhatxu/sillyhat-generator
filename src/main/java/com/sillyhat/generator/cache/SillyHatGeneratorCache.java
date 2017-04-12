package com.sillyhat.generator.cache;

import com.sillyhat.generator.dto.SillyHatGeneratorTemplatePathDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * SillyHatGeneratorCache
 *
 * @author 徐士宽
 * @date 2017/4/12 14:27
 */
public class SillyHatGeneratorCache {

    private volatile static SillyHatGeneratorCache instance;

    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    private SillyHatGeneratorCache(){
        this.createdDate = sdfDate.format(new Date());
    }

    public static SillyHatGeneratorCache getInstance() {
        if (instance == null) {
            synchronized (SillyHatGeneratorCache.class) {
                if (instance == null) instance = new SillyHatGeneratorCache();
            }
        }
        return instance;
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

    private String createdDate;

    private List<SillyHatGeneratorTemplatePathDTO> templateList;

    public static void setInstance(SillyHatGeneratorCache instance) {
        SillyHatGeneratorCache.instance = instance;
    }

    public String getDatabaseDriverClassName() {
        return databaseDriverClassName;
    }

    public void setDatabaseDriverClassName(String databaseDriverClassName) {
        this.databaseDriverClassName = databaseDriverClassName;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseUserName() {
        return databaseUserName;
    }

    public void setDatabaseUserName(String databaseUserName) {
        this.databaseUserName = databaseUserName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public int getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(int databaseType) {
        this.databaseType = databaseType;
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModuelName() {
        return moduelName;
    }

    public void setModuelName(String moduelName) {
        this.moduelName = moduelName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<SillyHatGeneratorTemplatePathDTO> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<SillyHatGeneratorTemplatePathDTO> templateList) {
        this.templateList = templateList;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
