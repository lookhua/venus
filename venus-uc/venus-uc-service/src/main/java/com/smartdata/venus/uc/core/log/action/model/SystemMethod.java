package com.smartdata.venus.uc.core.log.action.model;

import com.smartdata.core.enums.uc.ActionLogEnum;
import lombok.Getter;

/**
 * @author khlu
 * @date 2018/10/15
 */
@Getter
public class SystemMethod extends BusinessMethod{
    // 日志类型
    protected Byte type = ActionLogEnum.SYSTEM.getCode();

    public SystemMethod(String method) {
        super(method);
    }

    public SystemMethod(String name, String method) {
        super(name, method);
    }
}
