package com.sillyhat.generator.utils;

import com.sillyhat.generator.main.SillyHatGeneratorCreatedMain;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 名字起长点，防止其他类，引入是显示出来，还得麻烦去选择
 *
 * @author 徐士宽
 * @date 2017/4/10 18:00
 */
public class SillyHatGeneratorFileUtils extends FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(SillyHatGeneratorCreatedMain.class);

    /**
     * 读取TXT文件
     * @param templatePathNamt
     * @return
     */
    public static String readText(String templatePathNamt){
        try {
            return IOUtils.toString(SillyHatGeneratorFileUtils.class.getClassLoader().getResourceAsStream(templatePathNamt), SillyHatGeneratorConstants.ENCODING_UTF8);
        } catch (IOException e) {
            logger.error("读取文件发生异常{}",templatePathNamt,e);
            return null;
        }
    }

    /**
     * 判断路径结尾是否有\\或/符号，如果没有，统一加上\\
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

    /**
     * 包名转路径
     * @param packageName
     * @return
     */
    public static String packageToPath(String packageName){
       return packageName.replace(".","\\\\");
    }
}
