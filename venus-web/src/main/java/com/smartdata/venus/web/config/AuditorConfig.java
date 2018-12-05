package com.smartdata.venus.web.config;

import java.util.Optional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.smartdata.venus.uc.domain.User;

/**
 * 审核员自动赋值配置
 * 
 * @author khlu
 * @date 2018/8/14
 */
@Configuration
public class AuditorConfig implements AuditorAware<User> {
	@Override
	public Optional<User> getCurrentAuditor() {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		return Optional.ofNullable(user);
	}
}
