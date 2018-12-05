package com.smartdata.venus.uc.core.log.action.model;

import com.smartdata.core.enums.uc.ActionLogEnum;
import lombok.Getter;

/**
 * @author khlu
 * @date 2018/10/15
 */
@Getter
public class LoginType extends BusinessType{
    // 日志类型
    protected Byte type = ActionLogEnum.LOGIN.getCode();

    public LoginType(String message) {
        super(message);
    }

    public LoginType(String name, String message) {
        super(name, message);
    }
}
