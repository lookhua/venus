package com.smartdata.venus.core.log.action;

import com.smartdata.venus.core.log.action.base.ActionMap;
import com.smartdata.venus.core.log.action.base.ResetLog;
import com.smartdata.venus.uc.core.log.action.model.SystemMethod;
import com.smartdata.venus.uc.domain.ActionLog;
import com.smartdata.venus.uc.domain.User;

/**
 * @author khlu
 * @date 2018/10/19
 */
public class SystemAction extends ActionMap {

    public static final String RUNTIME_EXCEPTION = "runtime_exception";

    @Override
    public void init() {
        // 系统异常行为
        putMethod(RUNTIME_EXCEPTION, new SystemMethod("系统异常","runtimeException"));
    }

    // 系统异常行为方法
    public void runtimeException(ResetLog resetLog){
        RuntimeException runtime = (RuntimeException) resetLog.getParam("e");
        StringBuilder message = new StringBuilder();
        message.append(runtime.toString());
        StackTraceElement[] stackTrace = runtime.getStackTrace();
        for (StackTraceElement stack : stackTrace) {
            message.append("\n\t").append(stack.toString());
        }
        ActionLog actionLog = resetLog.getActionLog();
        if(actionLog.getCreateBy() == null){
            User user = new User();
            user.setNickname("系统");
            actionLog.setCreateBy(user);
        }
        actionLog.setMessage(String.valueOf(message));
    }
}
