package com.smartdata.venus.uc.validator;

import com.smartdata.venus.uc.domain.ActionLog;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class ActionLogForm extends ActionLog implements Serializable {
    @NotEmpty(message = "标题不能为空")
    private String title;
}
