package com.smartdata.venus.order.validator;

import com.smartdata.venus.order.domain.OrderSurvey;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author xuliang
 * @date 2018/12/18
 */
@Data
public class OrderSurveyForm extends OrderSurvey implements Serializable {
	@NotNull(message = "预约编号不能为空")
	private Long fkSubscribeId;
	@NotNull(message = "订单客户方编号不能为空")
	private Long fkConsumerId;
	@NotEmpty(message = "订单客户方手机不能为空")
	private String consumerPhone;
	@NotEmpty(message = "订单客户方地址不能为空")
	private String consumerAddr;
	@NotEmpty(message = "标题不能为空")
	private String title;
}
