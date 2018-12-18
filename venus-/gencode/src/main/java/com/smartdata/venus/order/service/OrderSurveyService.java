package com.smartdata.venus.order.service;

import com.smartdata.core.enums.uc.StatusEnum;
import com.smartdata.venus.order.domain.OrderSurvey;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xuliang
 * @date 2018/12/18
 */
public interface OrderSurveyService {

    Page<OrderSurvey> getPageList(Example<OrderSurvey> example, Integer pageIndex, Integer pageSize);

    OrderSurvey getId(Long id);

    OrderSurvey save(OrderSurvey orderSurvey);

    @Transactional
    Integer updateStatus(StatusEnum statusEnum, List<Long> idList);
}

