package com.smartdata.venus.uc.system.service;

import com.smartdata.core.enums.uc.StatusEnum;
import com.smartdata.venus.uc.domain.Dict;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author khlu
 * @date 2018/8/14
 */
public interface DictService {

    Dict getName(String name);

    Page<Dict> getPageList(Example<Dict> example, Integer pageIndex, Integer pageSize);

    Dict getId(Long id);

    Dict save(Dict dict);

    @Transactional
    Integer updateStatus(StatusEnum statusEnum, List<Long> idList);
}
