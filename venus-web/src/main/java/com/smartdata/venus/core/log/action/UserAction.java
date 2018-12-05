package com.smartdata.venus.core.log.action;

import java.util.List;

import javax.persistence.Table;

import com.smartdata.core.utils.FormBeanUtil;
import com.smartdata.venus.core.log.action.base.ActionMap;
import com.smartdata.venus.core.log.action.base.ResetLog;
import com.smartdata.venus.uc.core.log.action.model.BusinessMethod;
import com.smartdata.venus.uc.core.log.action.model.LoginMethod;
import com.smartdata.venus.uc.core.utils.SpringContextUtil;
import com.smartdata.venus.uc.domain.ActionLog;
import com.smartdata.venus.uc.domain.User;
import com.smartdata.venus.uc.system.service.ActionLogService;
import com.smartdata.venus.uc.system.service.UserService;

/**
 * 用户日志行为
 * @author khlu
 * @date 2018/10/14
 */
public class UserAction extends ActionMap {

    public static final String USER_LOGIN = "user_login";
    public static final String USER_SAVE = "user_save";
    public static final String EDIT_PWD = "edit_pwd";
    public static final String EDIT_ROLE = "edit_role";

    @Override
    public void init() {
        // 用户登录行为
        putMethod(USER_LOGIN, new LoginMethod("用户登录","userLogin"));
        // 保存用户行为
        putMethod(USER_SAVE, new BusinessMethod("用户管理","userSave"));
        // 修改用户密码行为
        putMethod(EDIT_PWD, new BusinessMethod("用户密码","editPwd"));
        // 角色分配行为
        putMethod(EDIT_ROLE, new BusinessMethod("角色分配","editRole"));
    }

    // 用户登录行为方法
    public void userLogin(ResetLog resetLog){
        boolean success = resetLog.isSuccess();
        if (resetLog.isSuccessRecord()){
            ActionLog actionLog = resetLog.getActionLog();
            actionLog.setMessage("后台登录成功");
        }
    }

    // 保存用户行为方法
    public void userSave(ResetLog resetLog){
        resetLog.getActionLog().setMessage("用户成功：${username}");
        SaveAction.defaultMethod(resetLog);
    }

    // 修改用户密码行为方法
    @SuppressWarnings("unchecked")
    public void editPwd(ResetLog resetLog){
        List<Long> idList = (List<Long>) resetLog.getParam("idList");
        UserService userService = SpringContextUtil.getBean(UserService.class);
        List<User> userList = userService.getIdList(idList);
        Table table = User.class.getAnnotation(Table.class);
        String message = "修改用户密码成功";
        if(!resetLog.isSuccess()){
            message = "修改用户密码失败";
        }
        ActionLogService actionLogService =
                (ActionLogService) SpringContextUtil.getBean(ActionLogService.class);

        String finalMessage = message;
        userList.forEach(user -> {
            ActionLog actionLog = new ActionLog();
            FormBeanUtil.copyProperties(resetLog.getActionLog(), actionLog);
            actionLog.setModel(table.name());
            actionLog.setRecordId(user.getId());
            actionLog.setMessage(finalMessage + user.getUsername());

            // 保存日志
            actionLogService.save(actionLog);
        });
        resetLog.setRecord(false);
    }

    // 角色分配行为方法
    public void editRole(ResetLog resetLog){
        Long id = (Long) resetLog.getParam("id");
        UserService userService = SpringContextUtil.getBean(UserService.class);
        User user = userService.getId(id);
        Table table = User.class.getAnnotation(Table.class);
        resetLog.getActionLog().setModel(table.name());
        resetLog.getActionLog().setRecordId(user.getId());
        if (resetLog.isSuccess()){
            resetLog.getActionLog().setMessage("角色分配成功："+user.getUsername());
        }else {
            resetLog.getActionLog().setMessage("角色分配失败："+user.getUsername());
        }
    }
}
