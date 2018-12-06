package com.smartdata.devtools.code.template;

import com.smartdata.core.utils.ToolUtil;
import com.smartdata.devtools.code.domain.Generate;
import com.smartdata.devtools.code.utils.GenerateUtil;
import com.smartdata.devtools.code.utils.TemplateUtil;

import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.List;

/**
 * @author khlu
 * @date 2018/10/25
 */
public class AddHtmlTemplate {

    /**
     * 生成页面
     */
    private static String genHtmlBody(Generate generate) {
        // 构建数据
        String var = ToolUtil.lowerFirst(generate.getBasic().getTableEntity());
        String filePath =  AddHtmlTemplate.class.getSimpleName() + ".code";

        // 提取html页面
        String htmlTarget = TemplateUtil.getTemplate(filePath, "html");

        // 遍历字段
        String fieldTarget = TemplateUtil.getTemplate(filePath, "field");
        StringBuilder fieldBuilder = new StringBuilder();
        String[] ignore = {"id", "remark", "createDate", "updateDate", "createBy", "updateBy", "status"};
        List<String> ignoreList = Arrays.asList(ignore);
        final boolean[] fieldRemark = {false};
        generate.getFields().forEach(field -> {
            if(!ignoreList.contains(field.getName())){
                String temp = fieldTarget;
                temp = temp.replace("#{field.title}", field.getTitle());
                temp = temp.replace("#{field.name}", field.getName());
                fieldBuilder.append(temp);
            }
            if(field.getName().equals("remark")){
                fieldRemark[0] = true;
            }
        });
        htmlTarget = htmlTarget.replace(fieldTarget, fieldBuilder);

        // 判断是否需要remark字段
        String remarkTarget = TemplateUtil.getTemplate(filePath, "remark");
        if (!fieldRemark[0]){
            htmlTarget = htmlTarget.replace(remarkTarget, "");
        }

        // 替换基本数据
        htmlTarget = htmlTarget.replace("#{var}", var);
        return htmlTarget;
    }

    /**
     * 生成列表页面模板
     */
    public static String generate(Generate generate) {
        String filePath = GenerateUtil.getHtmlFilePath(generate, "add");
        String content = AddHtmlTemplate.genHtmlBody(generate);
        try {
            GenerateUtil.generateFile(filePath, content);
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        }
        return filePath;
    }
    
    public InputStream getJarInputStream(String filePath, String name)
            throws Exception {
        URL url = new URL("jar:file:" + filePath + "!/" + name);
        JarURLConnection jarConnection = (JarURLConnection) url
                .openConnection();
        InputStream in = jarConnection.getInputStream();

        return in;
    }
    
    public static void main(String[] args) {
		String path="file:/home/lkh/.m2/repository/com/smartdata/venus-devtools/0.0.1-SNAPSHOT/venus-devtools-0.0.1-SNAPSHOT.jar!/com/smartdata/devtools/code/template/RepositoryTemplate.code";
		TemplateUtil.getTemplate(path, "remark");
    }
}
