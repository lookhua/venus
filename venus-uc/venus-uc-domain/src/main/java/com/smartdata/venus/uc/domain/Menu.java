package com.smartdata.venus.uc.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartdata.core.enums.uc.StatusEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * @author khlu
 * @date 2018/8/14
 */
@Entity
@Table(name="sys_menu")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Menu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pid;
    private String pids;
    private String title;
    private String url;
    private String icon;
    private Byte type;
    private Integer sort;
    private String remark;
    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date updateDate;
    @CreatedBy
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="create_by")
    @JsonIgnore
    private User createBy;
    @LastModifiedBy
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="update_by")
    @JsonIgnore
    private User updateBy;
    private Byte status = StatusEnum.OK.getCode();

    @ManyToMany(mappedBy = "menus")
    @JsonIgnore
    private Set<Role> roles = new HashSet<>(0);

    @Transient
    @JsonIgnore
    private Map<Long,Menu> children = new HashMap<>();
}
