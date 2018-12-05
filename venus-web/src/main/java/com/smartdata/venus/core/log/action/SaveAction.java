package com.smartdata.venus.core.log.action;

import com.smartdata.core.utils.ReflexBeanUtil;
import com.smartdata.venus.core.log.action.base.ActionMap;
import com.smartdata.venus.core.log.action.base.ResetLog;
import com.smartdata.venus.uc.domain.ActionLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.persistence.Table;
import java.lang.reflect.InvocationTargetException;

/**
 * 通用：记录保存数据的行为
 *
 * @author khlu
 * @date 2018/10/14
 */
@Slf4j
public class SaveAction extends ActionMap {

    @Override
    public void init() {
        // 记录数据保存日志
        putMethod("default", "defaultMethod");
    }

    /**
     * 重新包装保存的数据行为方法
     *
     * @param resetLog ResetLog对象数据
     */
    public static void defaultMethod(ResetLog resetLog) {
        ActionLog actionLog = resetLog.getActionLog();
        Object validated = resetLog.getValidated();
        Assert.notNull(validated, "未发现@Validated注解参数，将不做数据日志记录");

        try {
            Object vEntity = resetLog.getValidatedEntity(validated);
            Assert.notNull(vEntity, "验证类中没有发现entity成员属性，无法使用本行为");
            // 获取实体类的@Table表名
            Table table = vEntity.getClass().getAnnotation(Table.class);
            actionLog.setModel(table.name());
            // 获取实体对象数据ID
            Object entityId = ReflexBeanUtil.getField(vEntity, "id");
            actionLog.setRecordId(Long.valueOf(String.valueOf(entityId)));
            // 日志消息
            Object validatedId = ReflexBeanUtil.getField(validated, "id");
            String message = "数据成功";
            if (!actionLog.getMessage().isEmpty()) {
                message = resetLog.fillRule(vEntity, actionLog.getMessage());
            }
            if (validatedId == null) {
                actionLog.setMessage("添加" + message);
            } else {
                actionLog.setMessage("更新" + message);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
