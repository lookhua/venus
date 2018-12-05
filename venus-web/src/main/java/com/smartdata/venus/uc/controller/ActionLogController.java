package com.smartdata.venus.uc.controller;

import com.smartdata.core.utils.ResultVoUtil;
import com.smartdata.core.vo.ResultVo;
import com.smartdata.venus.uc.core.utils.TimoExample;
import com.smartdata.venus.uc.domain.ActionLog;
import com.smartdata.venus.uc.system.service.ActionLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/actionLog")
public class ActionLogController {

    @Autowired
    private ActionLogService actionLogService;

    /**
     * 列表页面
     * @param pageIndex 页码
     * @param pageSize 获取数据长度
     */
    @GetMapping("/index")
    @RequiresPermissions("/actionLog/index")
    public String index(Model model, ActionLog actionLog,
                        @RequestParam(value="page",defaultValue="1") int pageIndex,
                        @RequestParam(value="size",defaultValue="10") int pageSize){

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取日志列表
        Example<ActionLog> example = TimoExample.of(actionLog, matcher);
        Page<ActionLog> list = actionLogService.getPageList(example, pageIndex, pageSize);

        // 封装数据
        model.addAttribute("list",list.getContent());
        model.addAttribute("page",list);
        return "/system/actionLog/index";
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("/actionLog/detail")
    public String toDetail(@PathVariable("id") Long id, Model model){
        ActionLog actionLog = actionLogService.getId(id);
        model.addAttribute("actionLog",actionLog);
        return "/system/actionLog/detail";
    }

    /**
     * 删除指定的日志
     */
    @RequestMapping("/status/delete")
    @RequiresPermissions("/actionLog/delete")
    @ResponseBody
    public ResultVo delete(
            @RequestParam(value = "ids", required = false) Long id){
        if (id != null){
            actionLogService.deleteId(id);
            return ResultVoUtil.success("删除日志成功");
        }else {
            actionLogService.emptyLog();
            return ResultVoUtil.success("清空日志成功");
        }
    }
}
