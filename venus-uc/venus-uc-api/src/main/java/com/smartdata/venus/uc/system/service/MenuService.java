package com.smartdata.venus.uc.system.service;

import com.smartdata.core.enums.uc.StatusEnum;
import com.smartdata.venus.uc.domain.Menu;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author khlu
 * @date 2018/8/14
 */
public interface MenuService {

    @Transactional
    List<Menu> getIdList(List<Long> ids);

    List<Menu> getList(Example<Menu> example, Sort sort);

    Menu getId(Long id);

    Menu getUrl(String url);

    List<Menu> getList(Sort sort);

    Integer getSortMax(Long pid);

    List<Menu> getPid(Long pid, Long notId);

    Menu save(Menu menu);

    List<Menu> save(List<Menu> menuList);

    @Transactional
    Integer updateStatus(StatusEnum statusEnum, List<Long> idList);
}
