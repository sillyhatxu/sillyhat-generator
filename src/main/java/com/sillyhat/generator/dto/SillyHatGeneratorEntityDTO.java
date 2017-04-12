package com.sillyhat.generator.dto;

import com.sillyhat.generator.interceptor.SillyHatGeneratorTypeMappingInterceptor;
import com.sillyhat.generator.utils.SillyHatGeneratorStringUtils;
import java.io.Serializable;

/**
 * SillyHatGeneratorCreatedDTO
 *
 * @author 徐士宽
 * @date 2017/4/10 17:04
 */
public class SillyHatGeneratorEntityDTO implements Serializable{

    private static final long serialVersionUID = -2600394944364055799L;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 类型
     */
    private String dataType;

    /**
     * 描述
     */
    private String comments;

    /**
     * 是否为主键
     */
    private boolean isKey;


    /****************转换获得******************/

    /**
     * 实体字段名称
     */
    private String entityFieldName;

    /**
     * 实体字段类型
     */
    private String entityFieldType;


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        this.entityFieldName = SillyHatGeneratorStringUtils.getEntityField(columnName);
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
        this.entityFieldType = SillyHatGeneratorTypeMappingInterceptor.databaseTypeToEntityType(this);
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setIsKey(boolean key) {
        isKey = key;
    }

    public String getEntityFieldName() {
        return entityFieldName;
    }

    public void setEntityFieldName(String entityFieldName) {
        this.entityFieldName = entityFieldName;
    }

    public String getEntityFieldType() {
        return entityFieldType;
    }

    public void setEntityFieldType(String entityFieldType) {
        this.entityFieldType = entityFieldType;
    }

    @Override
    public String toString() {
        return "SillyHatGeneratorEntityDTO{" +
                "columnName='" + columnName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", comments='" + comments + '\'' +
                ", isKey=" + isKey +
                ", entityFieldName='" + entityFieldName + '\'' +
                ", entityFieldType='" + entityFieldType + '\'' +
                '}';
    }
}
