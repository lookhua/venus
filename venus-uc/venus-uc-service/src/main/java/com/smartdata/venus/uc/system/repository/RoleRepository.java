package com.smartdata.venus.uc.system.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;

import com.smartdata.venus.uc.domain.Role;


public interface RoleRepository extends BaseRepository<Role,Long> {

    /**
     * 查找状态正常的角色
     * @param sort 排序对象
     */
    public List<Role> findAllByStatus(Sort sort, Byte status);

    /**
     * 查找多个角色
     * @param ids id列表
     */
    public List<Role> findByIdInAndStatus(List<Long> ids, Byte status);

    /**
     * 查询指定用户的角色列表
     * @param id 用户ID
     * @param status 角色状态
     */
    public Set<Role> findByUsers_IdAndStatus(Long id, Byte status);
}
