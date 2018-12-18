package com.smartdata.venus.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartdata.core.enums.uc.StatusEnum;
import com.smartdata.venus.uc.domain.User;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xuliang
 * @date 2018/12/18
 */
@Entity
@Table(name="t_order_survey")
@Data
@EntityListeners(AuditingEntityListener.class)
public class OrderSurvey implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// 预约编号
	private Long fkSubscribeId;
	// 订单名称
	private String name;
	// 数据状态
	private Byte status = StatusEnum.OK.getCode();
	// 订单预付金额
	private Float repayMoney;
	// 订单后付金额
	private Float payMoney;
	// 订单客户方编号
	private Long fkConsumerId;
	// 订单客户方手机
	private String consumerPhone;
	// 订单客户方地址
	private String consumerAddr;
	// 订单服务人员
	private Long fkServicerId;
	// 订单服务人员手机
	private String servicePhone;
	// 订单确认人员
	private Long fkConfirmerId;
	// 标题
	private String title;
	// 备注
	private String remark;
	// 创建时间
	@CreatedDate
	private Date createDate;
	// 更新时间
	@LastModifiedDate
	private Date updateDate;
	// 创建者
	@CreatedBy
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="create_by")
	@JsonIgnore
	private User createBy;
	// 更新者
	@LastModifiedBy
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="update_by")
	@JsonIgnore
	private User updateBy;
	// 记录版本
	private Long versionKey;
	// 分区健
	private Long partionKey;
}
