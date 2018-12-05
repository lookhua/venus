package com.smartdata.venus.uc.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartdata.venus.uc.domain.ActionLog;

public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {

    /**
     * 根据模型和数据ID查询日志列表
     * @param model 模型（表名）
     * @param recordId 数据ID
     */
    public List<ActionLog> findByModelAndRecordId(String model, Long recordId);
}
