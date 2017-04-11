package com.sillyhat.generator.dto;

import java.io.Serializable;
import java.util.List;

/**
 * CreatedDTO
 *
 * @author 徐士宽
 * @date 2017/4/10 17:04
 */
public class CreatedDTO implements Serializable{

    private static final long serialVersionUID = -8006463195691021662L;
    /**
     * 输出路径
     */
    private String outFilePath;

    /**
     * 数据库表名称
     */
    private String tableName;

    /**
     * 创建人
     */
    private String author;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 模块名
     */
    private String moduelName;

    /**
     * 实体名
     */
    private String entityName;


    private List<TemplatePathDTO> templateList;

    public boolean useDefaultTemplate;

    /**
     * 使用默认自定义模板时
     * @param outFilePath
     * @param author
     * @param tableName
     */
    public CreatedDTO(String outFilePath,String author,String tableName,List<TemplatePathDTO> templateList){
        this.outFilePath = outFilePath;
        this.author = author;
        this.tableName = tableName;
        this.templateList = templateList;
        this.useDefaultTemplate = false;
    }


    /**
     * 使用默认模板
     * @param outFilePath
     * @param author
     * @param tableName
     * @param packageName
     * @param moduelName
     * @param entityName
     */
    public CreatedDTO(String outFilePath,String author,String tableName,String packageName,String moduelName,String entityName){
        this.outFilePath = outFilePath;
        this.author = author;
        this.tableName = tableName;
        this.packageName = packageName;
        this.moduelName = moduelName;
        this.entityName = entityName;
        this.useDefaultTemplate = true;
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public String getTableName() {
        return tableName;
    }

    public String getAuthor() {
        return author;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getModuelName() {
        return moduelName;
    }

    public String getEntityName() {
        return entityName;
    }

    public List<TemplatePathDTO> getTemplateList() {
        return templateList;
    }

    public boolean isUseDefaultTemplate() {
        return useDefaultTemplate;
    }
}
