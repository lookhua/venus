package com.smartdata.venus.order.controller;

import com.smartdata.core.enums.uc.ResultEnum;
import com.smartdata.core.enums.uc.StatusEnum;
import com.smartdata.core.utils.FormBeanUtil;
import com.smartdata.core.utils.ResultVoUtil;
import com.smartdata.core.vo.ResultVo;
import com.smartdata.venus.exception.ResultException;
import com.smartdata.venus.order.domain.OrderSurvey;
import com.smartdata.venus.order.service.OrderSurveyService;
import com.smartdata.venus.order.validator.OrderSurveyForm;
import com.smartdata.venus.uc.core.utils.TimoExample;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuliang
 * @date 2018/12/18
 */
@Controller
@RequestMapping("/orderSurvey")
public class OrderSurveyController {

    @Autowired
    private OrderSurveyService orderSurveyService;

    /**
     * 列表页面
     * @param pageIndex 页码
     * @param pageSize 获取数据长度
     */
    @GetMapping("/index")
    @RequiresPermissions("/orderSurvey/index")
    public String index(Model model, OrderSurvey orderSurvey,
            @RequestParam(value="page",defaultValue="1") int pageIndex,
            @RequestParam(value="size",defaultValue="10") int pageSize){

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching().
				withMatcher("name", match -> match.contains()).
				withMatcher("title", match -> match.contains());

        // 获取订单列表
        Example<OrderSurvey> example = TimoExample.of(orderSurvey, matcher);
        Page<OrderSurvey> list = orderSurveyService.getPageList(example, pageIndex, pageSize);

        // 封装数据
        model.addAttribute("list",list.getContent());
        model.addAttribute("page",list);
        return "/order/orderSurvey/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("/orderSurvey/add")
    public String toAdd(){
        return "/order/orderSurvey/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("/orderSurvey/edit")
    public String toEdit(@PathVariable("id") Long id, Model model){
        OrderSurvey orderSurvey = orderSurveyService.getId(id);
        model.addAttribute("orderSurvey",orderSurvey);
        return "/order/orderSurvey/add";
    }

    /**
     * 保存添加/修改的数据
     * @param orderSurveyForm 表单验证对象
     */
    @PostMapping({"/add","/edit"})
    @RequiresPermissions({"/orderSurvey/add","/orderSurvey/edit"})
    @ResponseBody
    public ResultVo save(@Validated OrderSurveyForm orderSurveyForm){

        // 将验证的数据复制给实体类
        OrderSurvey orderSurvey = new OrderSurvey();
        if(orderSurveyForm.getId() != null){
            orderSurvey = orderSurveyService.getId(orderSurveyForm.getId());
        }
        FormBeanUtil.copyProperties(orderSurveyForm, orderSurvey);

        // 保存数据
        orderSurveyService.save(orderSurvey);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("/orderSurvey/detail")
    public String toDetail(@PathVariable("id") Long id, Model model){
        OrderSurvey orderSurvey = orderSurveyService.getId(id);
        model.addAttribute("orderSurvey",orderSurvey);
        return "/order/orderSurvey/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("/orderSurvey/status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> idList){
        try {
            // 获取状态StatusEnum对象
            StatusEnum statusEnum = StatusEnum.valueOf(param.toUpperCase());
            // 更新状态
            Integer count = orderSurveyService.updateStatus(statusEnum,idList);
            if (count > 0){
                return ResultVoUtil.success(statusEnum.getMessage()+"成功");
            }else{
                return ResultVoUtil.error(statusEnum.getMessage()+"失败，请重新操作");
            }
        } catch (IllegalArgumentException e){
            throw new ResultException(ResultEnum.STATUS_ERROR);
        }
    }

}
