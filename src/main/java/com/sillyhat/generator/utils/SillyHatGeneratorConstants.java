package com.sillyhat.generator.utils;

/**
 * SillyHatGeneratorConstants
 *
 * @author 徐士宽
 * @date 2017/4/11 15:40
 */
public class SillyHatGeneratorConstants {

    /**
     * Oracle数据类型
     */
    public static final int DATABASE_TYPE_ORACLE = 1;

    /**
     * Mysql数据类型
     */
    public static final int DATABASE_TYPE_MYSQL = 2;

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String TEMPLATE_PATH_NAMT_DTO = "com/sillyhat/generator/template/dto-template.txt";

    public static final String TEMPLATE_PATH_NAMT_SERVICE = "com/sillyhat/generator/template/service-template.txt";

    public static final String TEMPLATE_PATH_NAMT_SERVICE_IMPL = "com/sillyhat/generator/template/service-impl-template.txt";

    public static final String TEMPLATE_PATH_NAMT_MAPPER = "com/sillyhat/generator/template/mapper-template.txt";

    public static final String TEMPLATE_PATH_NAMT_MAPPING = "com/sillyhat/generator/template/mapping-template.txt";

    public static final String TEMPLATE_PATH_NAMT_TEST = "com/sillyhat/generator/template/test-template.txt";


    public static final String TEMPLATE_PATH_DATABASE_ORACLE = "com/sillyhat/generator/template/oracle-sql.txt";

    public static final String TEMPLATE_PATH_DATABASE_MYSQL = "com/sillyhat/generator/template/mysql-sql.txt";



    /**
     * @Fields STEREOTYPE_SERVICE : @Service
     */
    public static final String STEREOTYPE_SERVICE = "@Service";

    /**
     * @Fields STEREOTYPE_DAO : @Repository
     */
    public static final String STEREOTYPE_DAO = "@Repository";

    /**
     * @Fields STEREOTYPE_AUTOWIRED : @Autowired
     */
    public static final String STEREOTYPE_AUTOWIRED = "@Autowired";

    /**
     * @Fields SIGN_ENTER : 回车
     */
    public static final String SIGN_ENTER = "\r\n";

    /**
     * @Fields SIGN_TAB : tab
     */
    public static final String SIGN_TAB = "\t";

    /**
     * @Fields SIGN_AT : at
     */
    public static final String SIGN_AT = "@";

    /**
     * @Fields SIGN_ANNOTATION : 注释
     */
    public static final String SIGN_ANNOTATION = "//";

}
