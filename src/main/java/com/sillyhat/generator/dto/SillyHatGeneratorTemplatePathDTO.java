package com.sillyhat.generator.dto;

import java.io.File;
import java.io.Serializable;

/**
 * 创建文件列表
 *
 * @author 徐士宽
 * @date 2017/4/11 14:09
 */
public class SillyHatGeneratorTemplatePathDTO implements Serializable {

    private static final long serialVersionUID = -7001612925905616936L;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 是否为实体(实体比较特殊，需要读取数据库内容)
     */
    private boolean isEntity;

    /**
     * 替换符号
     */
    private String [] fileReplaceSign;

    /**
     * 模板文件
     */
    private File fileTemplate;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public boolean isEntity() {
        return isEntity;
    }

    public void setEntity(boolean entity) {
        isEntity = entity;
    }

    public String[] getFileReplaceSign() {
        return fileReplaceSign;
    }

    public void setFileReplaceSign(String[] fileReplaceSign) {
        this.fileReplaceSign = fileReplaceSign;
    }

    public File getFileTemplate() {
        return fileTemplate;
    }

    public void setFileTemplate(File fileTemplate) {
        this.fileTemplate = fileTemplate;
    }
}
