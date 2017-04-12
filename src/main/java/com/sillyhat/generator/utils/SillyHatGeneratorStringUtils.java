package com.sillyhat.generator.utils;

import com.sillyhat.generator.cache.SillyHatGeneratorCache;

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
    public static String getEntityField(String dbField){
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
     * <p>Title: getSignX</p>
     * <p>Description: </p>得到x个符号
     * @author 徐士宽
     * @date 2015-12-3
     */
    public static String getSignX(String signType,int x){
        String sign = "";
        for (int i = 0; i < x; i++) {
            sign += signType;
        }
        return sign;
    }


    public static String getFieldGetMethod(String sign,String field,String fieldType,String comment){
        String getMeghod = "get" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
        StringBuffer src = new StringBuffer();
        src.append(sign).append("/**                             ").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" * <p>Title: ").append(getMeghod).append("</p>").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" * <p>Description: </p>").append(comment).append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" * @author ").append(SillyHatGeneratorCache.getInstance().getAuthor()).append("").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" * @date ").append(SillyHatGeneratorCache.getInstance().getCreatedDate()).append("              ").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" * @return:").append(fieldType).append("               ").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" */                             ").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append("public ").append(fieldType).append(" ").append(getMeghod).append("(){").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(SillyHatGeneratorConstants.SIGN_TAB).append("return  ").append(field).append(";").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append("}").append(getSignX(SillyHatGeneratorConstants.SIGN_ENTER, 3));
        return src.toString();
    }

    public static String getFieldSetMethod(String sign,String field,String fieldType,String comment){
        String setMeghod = "set" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
        StringBuffer src = new StringBuffer();
        src.append(sign).append("/**                             ").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" * <p>Title: ").append(setMeghod).append("</p>").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" * <p>Description: </p>").append(comment).append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" * @param ").append(field).append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" * @author ").append(SillyHatGeneratorCache.getInstance().getAuthor()).append("").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" * @date ").append(SillyHatGeneratorCache.getInstance().getCreatedDate()).append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" * @return:void                 ").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(" */                             ").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append("public void ").append(setMeghod).append("(").append(fieldType).append(" ").append(field).append("){").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append(SillyHatGeneratorConstants.SIGN_TAB).append("this.").append(field).append(" = ").append(field).append(";").append(SillyHatGeneratorConstants.SIGN_ENTER);
        src.append(sign).append("}").append(getSignX(SillyHatGeneratorConstants.SIGN_ENTER, 3));
        return src.toString();
    }


    /**
     * 得到第一个字母小写的字符串
     * @param entityName
     * @return
     */
    public static String toLowerCaseSrc(String entityName){
        String result = entityName.substring(0,1).toLowerCase() + entityName.substring(1, entityName.length());
        return result;
    }
}
