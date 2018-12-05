package com.smartdata.venus.uc.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="sys_action_log")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ActionLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Byte type;
    private String clazz;
    private String method;
    private String model;
    private Long recordId;
    @Lob @Column(columnDefinition="TEXT")
    private String message;
    @CreatedDate
    private Date createDate;
    @CreatedBy
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="create_by")
    @JsonIgnore
    private User createBy;

    public ActionLog(){}
    /**
     * 封装日志对象
     * @param name 日志名称
     * @param message 日志消息
     */
    public ActionLog(String name, String message){
        this.name = name;
        this.message = message;
    }
}
