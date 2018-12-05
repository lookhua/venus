package com.smartdata.venus.uc.core.log.action.model;

import com.smartdata.core.enums.uc.ActionLogEnum;
import lombok.Getter;

/**
 * @author khlu
 * @date 2018/10/15
 */
@Getter
public class LoginMethod extends BusinessMethod{
    // 日志类型
    protected Byte type = ActionLogEnum.LOGIN.getCode();

    public LoginMethod(String method) {
        super(method);
    }

    public LoginMethod(String name, String method) {
        super(name, method);
    }
}
