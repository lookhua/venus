package com.smartdata.venus.uc.validator;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.smartdata.venus.uc.domain.User;

import lombok.Data;

/**
 * @author khlu
 * @date 2018/8/14
 */
@Data
public class UserForm extends User implements Serializable {
    private Object entity;
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "用户昵称不能为空")
    @Size(min = 2, message = "用户昵称：请输入至少2个字符")
    private String nickname;
    private String confirm;
}
