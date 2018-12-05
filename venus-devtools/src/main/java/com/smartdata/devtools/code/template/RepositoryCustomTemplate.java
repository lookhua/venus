package com.smartdata.devtools.code.template;

import com.smartdata.devtools.code.domain.Generate;
import com.smartdata.devtools.code.utils.CodeUtil;
import com.smartdata.devtools.code.utils.GenerateUtil;
import com.smartdata.devtools.code.utils.TemplateUtil;

import java.nio.file.FileAlreadyExistsException;

/**
 * @author khlu
 * @date 2018/10/25
 */
public class RepositoryCustomTemplate {

    /**
     * 生成需要导入的包
     */
    private static void genImport(Generate generate) {
        String tableEntity = generate.getBasic().getTableEntity();
        CodeUtil.importLine(TemplateUtil.getPath(generate) + ".domain." + tableEntity);
        //CodeUtil.importLine(BaseRepository.class);
    }

    /**
     * 生成类字段
     */
    private static void genClazzBody(Generate generate) {
        // 构建数据
        String obj = generate.getBasic().getTableEntity();
        String filePath = RepositoryCustomTemplate.class.getResource("").getPath()
                + RepositoryCustomTemplate.class.getSimpleName() + ".code";

        // 生成Class部分
        String clazzTarget = TemplateUtil.getTemplate(filePath, "Class");
        clazzTarget = clazzTarget.replace("#{obj}", obj);
        CodeUtil.append(clazzTarget).append(CodeUtil.lineBreak);
    }

    /**
     * 生成Dao层模板
     */
    public static String generate(Generate generate) {
        CodeUtil.create();
        CodeUtil.setPackageName(TemplateUtil.getPackage(generate, "repository"));
        TemplateUtil.genAuthor(generate);
        RepositoryCustomTemplate.genImport(generate);
        RepositoryCustomTemplate.genClazzBody(generate);

        // 生成文件
        String filePath = GenerateUtil.getJavaFilePath(generate, "repository", "Repository");
        try {
            GenerateUtil.generateFile(filePath, CodeUtil.save());
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        }
        return filePath;
    }
}
