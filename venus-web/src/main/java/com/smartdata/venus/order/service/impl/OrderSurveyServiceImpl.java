package com.smartdata.venus.order.service.impl;

import com.smartdata.core.enums.uc.StatusEnum;
import com.smartdata.venus.order.domain.OrderSurvey;
import com.smartdata.venus.order.repository.OrderSurveyRepository;
import com.smartdata.venus.order.service.OrderSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xuliang
 * @date 2018/12/18
 */
@Service
public class OrderSurveyServiceImpl implements OrderSurveyService {

    @Autowired
    private OrderSurveyRepository orderSurveyRepository;

    /**
     * 根据订单ID查询订单数据
     * @param id 订单ID
     */
    @Override
    @Transactional
    public OrderSurvey getId(Long id) {
        Byte[] status = {StatusEnum.OK.getCode(), StatusEnum.FREEZED.getCode()};
        return orderSurveyRepository.findByIdAndStatusIn(id, status);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @param pageIndex 页码
     * @param pageSize 获取列表长度
     * @return 返回分页数据
     */
    @Override
    public Page<OrderSurvey> getPageList(Example<OrderSurvey> example, Integer pageIndex, Integer pageSize) {
        // 创建分页对象
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        PageRequest page = PageRequest.of(pageIndex-1, pageSize, sort);
        Page<OrderSurvey> list = orderSurveyRepository.findAll(example, page);
        return list;
    }

    /**
     * 保存订单
     * @param orderSurvey 订单实体类
     */
    @Override
    public OrderSurvey save(OrderSurvey orderSurvey){
        return orderSurveyRepository.save(orderSurvey);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Integer updateStatus(StatusEnum statusEnum, List<Long> idList){
        return orderSurveyRepository.updateStatus(statusEnum.getCode(),idList);
    }
}

