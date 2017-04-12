package com.sillyhat.generator.interceptor;

import com.sillyhat.generator.cache.SillyHatGeneratorCache;
import com.sillyhat.generator.dto.SillyHatGeneratorEntityDTO;
import com.sillyhat.generator.utils.SillyHatGeneratorConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SillyHatGeneratorCreatedMain
 *
 * @author 徐士宽
 * @date 2017/4/10 17:01
 */
public class SillyHatGeneratorTypeMappingInterceptor {

    private static Logger logger = LoggerFactory.getLogger(SillyHatGeneratorTypeMappingInterceptor.class);

    /**
     * 数据库类型转换为实体类型
     * @param dto
     * @return
     */
    public static String databaseTypeToEntityType(SillyHatGeneratorEntityDTO dto){
        int databaseType = SillyHatGeneratorCache.getInstance().getDatabaseType();
        if(databaseType == SillyHatGeneratorConstants.DATABASE_TYPE_ORACLE){
            return "";
        }else if(databaseType == SillyHatGeneratorConstants.DATABASE_TYPE_MYSQL){
            String dataType = dto.getDataType();
            String entityFieldName = dto.getEntityFieldName();
            boolean isKey = dto.isKey();
            if(isKey){
                return "Long";//主键为Long类型
            }else{
                if(entityFieldName.equals("createdUser") || entityFieldName.equals("updatedUser")){
                    return "Long";
                }
                if(dataType.equals("int")){
                    return "int";
                }else if(dataType.equals("tinyint")){
                    return "int";
                }else if(dataType.equals("datetime")){
                    return "Date";
                }else if(dataType.equals("data")){
                    return "Date";
                }else{
                    return "String";
                }
            }
        }else{
            logger.error("不支持的数据库类型");
            return "";
        }
    }


    /**
     * 根据实体字段类型，得到导入相关包
     * @param entityFieldType
     * @return
     */
    public static String getImportPackage(String entityFieldType,String entityFieldName){
        if(entityFieldType.equals("Date")){
            return "java.util.Date";
        } else if(entityFieldType.equals("BigDecimal")) {
            return "java.math.BigDecimal";
        } else{
            return "";
        }
    }

    public static String getTestSetValue(String entityFieldType){
        if(entityFieldType.equals("Date")){
            return "null";
        } else if(entityFieldType.equals("BigDecimal")) {
            return "new BigDecimal(0)";
        } else if(entityFieldType.equals("int")) {
            return "0";
        } else if(entityFieldType.equals("Long") || entityFieldType.equals("long")){
            return "0l";
        } else{
            return "\"\"";
        }
    }
}
