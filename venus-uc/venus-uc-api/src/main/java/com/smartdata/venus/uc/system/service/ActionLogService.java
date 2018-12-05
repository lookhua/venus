package com.smartdata.venus.uc.system.service;

import com.smartdata.venus.uc.domain.ActionLog;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActionLogService {

    Page<ActionLog> getPageList(Example<ActionLog> example, Integer pageIndex, Integer pageSize);

    ActionLog getId(Long id);

    List<ActionLog> getDataLogList(String model, Long recordId);

    ActionLog save(ActionLog actionLog);

    @Transactional
    void deleteId(Long id);

    @Transactional
    void emptyLog();
}
