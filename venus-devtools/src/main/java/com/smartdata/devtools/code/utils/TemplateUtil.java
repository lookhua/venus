package com.smartdata.devtools.code.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.smartdata.devtools.code.domain.Generate;

/**
 * 标准模板
 * @author khlu
 * @date 2018/10/23
 */
public class TemplateUtil {

    /**
     * 模板路径
     */
    public static String getPath(Generate generate){
        return generate.getBasic().getPackagePath() + "." + generate.getBasic().getGenModule();
    }

    /**
     * 类包路径
     * @param layer 业务层包名
     */
    public static String getPackage(Generate generate, String layer){
        return "package " + getPath(generate) + "."+ layer +";";
    }

    /**
     * 作者信息
     */
    public static void genAuthor(Generate generate){
        CodeUtil.lineNo("/**");
        CodeUtil.lineNo(" * @author " + generate.getBasic().getAuthor());
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        CodeUtil.lineNo(" * @date " + df.format(new Date()));
        CodeUtil.lineNo(" */");
    }

    /**
     * 获取模板文件的类和方法模板
     * @param path 文件路径
     * @param target 类或方法
     */
    public static String getTemplate(String path, String target){
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(path);
            isr = new InputStreamReader(fis, "UTF-8");
            br = new BufferedReader(isr);
            String firstLine = br.readLine();
            int indexOf = firstLine.indexOf(target);
            if (indexOf != -1){
                int indexEnd = firstLine.indexOf("]", indexOf);
                String[] split = firstLine.substring(indexOf + target.length() + 1, indexEnd).split("-");
                int lineNumber = 1;
                String line = "";
                StringBuilder builder = new StringBuilder();
                while ((line = br.readLine()) != null){
                    lineNumber++;
                    if(lineNumber >= Integer.valueOf(split[0]) && lineNumber <= Integer.valueOf(split[1])){
                        builder.append(line).append(CodeUtil.lineBreak);
                    }
                }
                return builder.toString();
            }
        } catch (IOException e) {
            try {
                if(fis != null) fis.close();
                if(isr != null) isr.close();
                if(br != null) br.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return "";
    }
}
