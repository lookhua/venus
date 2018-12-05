package com.smartdata.venus.uc.system.repository;

import com.smartdata.core.jpa.BaseRepository;
import com.smartdata.venus.uc.domain.User;

import java.util.List;

/**
 * @author khlu
 * @date 2018/8/14
 */
public interface UserRepository extends BaseRepository<User, Long> {
    /**
     * 根据用户名查询用户数据
     *
     * @param username 用户名
     * @return 用户数据
     */
    public User findByUsernameAndStatusIn(String username, Byte[] status);

    /**
     * 查找ID列表且状态正常
     *
     * @param ids     ID列表
     * @param status 状态
     */
    public List<User> findByIdInAndStatus(List<Long> ids, Byte status);

}
