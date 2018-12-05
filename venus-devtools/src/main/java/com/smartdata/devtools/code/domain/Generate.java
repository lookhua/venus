package com.smartdata.devtools.code.domain;

import java.util.List;

import lombok.Data;

/**
 * 封装生成数据
 * @author khlu
 * @date 2018/10/23
 */
@Data
public class Generate {
    private Basic basic = new Basic();
    private List<Field> fields;
    private Template template;
}
