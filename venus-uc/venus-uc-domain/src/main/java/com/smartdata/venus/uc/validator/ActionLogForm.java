package com.smartdata.venus.uc.validator;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.smartdata.venus.uc.domain.ActionLog;

import lombok.Data;

@Data
public class ActionLogForm extends ActionLog implements Serializable {
    @NotEmpty(message = "标题不能为空")
    private String title;
}
