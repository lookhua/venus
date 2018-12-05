package com.smartdata.devtools.code.domain;

import lombok.Data;

/**
 * @author khlu
 * @date 2018/10/21
 */
@Data
public class Basic {
    private String projectPath;
    private String packagePath;
    private String author;
    private String genTitle;
    private String genModule;
    private Long genPMenu;
    private String tablePrefix;
    private String tableName;
    private String tableEntity;
}
