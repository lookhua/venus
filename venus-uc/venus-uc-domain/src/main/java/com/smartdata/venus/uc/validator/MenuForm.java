package com.smartdata.venus.uc.validator;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.smartdata.venus.uc.domain.Menu;

import lombok.Data;

/**
 * @author khlu
 * @date 2018/8/14
 */
@Data
public class MenuForm extends Menu implements Serializable {
    private Object entity;
    @NotEmpty(message = "标题不能为空")
    private String title;
    @NotNull(message = "父级菜单不能为空")
    private Long pid;
    @NotEmpty(message = "url地址不能为空")
    private String url;
    @NotNull(message = "菜单类型不能为空")
    private Byte type;
}
