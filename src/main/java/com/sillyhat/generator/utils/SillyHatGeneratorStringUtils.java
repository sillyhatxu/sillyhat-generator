package com.sillyhat.generator.utils;

/**
 *
 * Created by ${XUSHIKUAN} on 2017-04-11.
 */
public class SillyHatGeneratorStringUtils {


    /**
     * <p>Title: getEntityField</p>
     * <p>Description: </p>根据数据库字段，得到实体字段(驼峰样式)
     * @author 徐士宽
     * @date 2015-12-21
     * @return:String
     */
    private static String getEntityField(String dbField){
        String field = "";
        String [] src = dbField.split("_");
        for (int i = 0; i < src.length; i++) {
            if(i == 0){
                field += src[i].toLowerCase();
            }else{
                field += src[i].substring(0, 1).toUpperCase() + src[i].substring(1, src[i].length()).toLowerCase();
            }
        }
        return field;
    }

    /**
     *
     * @return
     */
    public static String getDirPath(String path){
        String last = path.substring(path.length() - 1 ,path.length());
        if(last.indexOf("\\") == -1 && last.indexOf("/") == -1){
            return path + "\\";
        }else{
            return path;
        }
    }
}
