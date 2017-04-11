package com.sillyhat.generator.utils;

/**
 * SillyHatGeneratorConstants
 *
 * @author 徐士宽
 * @date 2017/4/11 15:40
 */
public class SillyHatGeneratorConstants {

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String TEMPLATE_PATH_NAMT_DTO = "com/sillyhat/generator/template/dto-template.txt";

    public static final String TEMPLATE_PATH_NAMT_SERVICE = "com/sillyhat/generator/template/service-template.txt";

    public static final String TEMPLATE_PATH_NAMT_SERVICE_IMPL = "com/sillyhat/generator/template/service-impl-template.txt";

    public static final String TEMPLATE_PATH_NAMT_MAPPER = "com/sillyhat/generator/template/mapper-template.txt";

    public static final String TEMPLATE_PATH_NAMT_MAPPING = "com/sillyhat/generator/template/mapping-template.txt";


    public static final String DATABASE_ORCLE_SQL = "";

    public static final String DATABASE_MYSQL_SQL = "SELECT column_name,data_type,column_key,column_comment FROM information_schema.columns WHERE table_name = ''{0}'' order by ordinal_position";

}
