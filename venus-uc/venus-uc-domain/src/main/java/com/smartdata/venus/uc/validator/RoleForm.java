package com.smartdata.venus.uc.validator;

import com.smartdata.venus.uc.domain.Role;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author khlu
 * @date 2018/8/14
 */
@Data
public class RoleForm extends Role implements Serializable {
    private Object entity;
    @NotEmpty(message = "角色编号不能为空")
    private String name;
    @NotEmpty(message = "角色名称不能为空")
    private String title;
}
