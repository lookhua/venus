package com.smartdata.devtools.code.template;

import com.smartdata.core.jpa.BaseRepository;
import com.smartdata.devtools.code.domain.Generate;
import com.smartdata.devtools.code.utils.CodeUtil;
import com.smartdata.devtools.code.utils.GenerateUtil;
import com.smartdata.devtools.code.utils.TemplateUtil;

import java.nio.file.FileAlreadyExistsException;

/**
 * @author khlu
 * @date 2018/10/25
 */
public class RepositoryImplTemplate {

    /**
     * 生成需要导入的包
     */
    private static void genImport(Generate generate) {
        String tableEntity = generate.getBasic().getTableEntity();
        CodeUtil.importLine(TemplateUtil.getPath(generate) + ".domain." + tableEntity);
        CodeUtil.importLine(TemplateUtil.getPath(generate) + ".repository." + tableEntity + "Impl");
        CodeUtil.importLine("com.smartdata.core.jpa.NativeSqlExecutorImpl");
        CodeUtil.importLine(BaseRepository.class);
    }

    /**
     * 生成类字段
     */
    private static void genClazzBody(Generate generate) {
        // 构建数据
        String obj = generate.getBasic().getTableEntity();
        String filePath =  RepositoryImplTemplate.class.getSimpleName() + ".code";

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
        CodeUtil.setPackageName(TemplateUtil.getPackage(generate, "repository.impl"));
        TemplateUtil.genAuthor(generate);
        RepositoryImplTemplate.genImport(generate);
        RepositoryImplTemplate.genClazzBody(generate);

        // 生成文件
        String filePath = GenerateUtil.getJavaFilePath(generate, "repository.impl", "RepositoryImpl");
        try {
            GenerateUtil.generateFile(filePath, CodeUtil.save());
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        }
        return filePath;
    }
}
