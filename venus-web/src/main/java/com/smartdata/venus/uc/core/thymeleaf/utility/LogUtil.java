package com.smartdata.venus.uc.core.thymeleaf.utility;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.Table;

import com.smartdata.core.utils.ReflexBeanUtil;
import com.smartdata.venus.uc.core.utils.SpringContextUtil;
import com.smartdata.venus.uc.domain.ActionLog;
import com.smartdata.venus.uc.system.service.ActionLogService;

/**
 * @author khlu
 * @date 2018/10/16
 */
public class LogUtil {

    /**
     * 获取实体对象的日志
     * @param entity 实体对象
     */
    public List<ActionLog> entityList(Object entity){
        ActionLogService actionLogService = SpringContextUtil.getBean(ActionLogService.class);
        Table table = entity.getClass().getAnnotation(Table.class);
        String tableName = table.name();
        try {
            Object object = ReflexBeanUtil.getField(entity, "id");
            Long entityId = Long.valueOf(String.valueOf(object));
            return actionLogService.getDataLogList(tableName, entityId);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
