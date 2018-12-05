package com.smartdata.venus.uc.core.log.action.model;

import com.smartdata.core.enums.uc.ActionLogEnum;
import lombok.Getter;

/**
 * @author khlu
 * @date 2018/10/15
 */
@Getter
public class SystemType extends BusinessType{
    // 日志类型
    protected Byte type = ActionLogEnum.SYSTEM.getCode();

    public SystemType(String message) {
        super(message);
    }

    public SystemType(String name, String message) {
        super(name, message);
    }
}
