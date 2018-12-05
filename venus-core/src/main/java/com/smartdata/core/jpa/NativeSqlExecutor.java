package com.smartdata.core.jpa;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface NativeSqlExecutor {
	
	public <V> List<V> querySqlList(String sql, Class<V> cl, Object... values);

	public <V> List<V> querySqlList(String sql, Class<V> cl, Map<String, Object> values);

	public <V> Page<V> querySqlPage(Pageable pageable, String sql, Class<V> cl, Object... values);

	public <V> Page<V> querySqlPage(Pageable pageable, String sql, Class<V> cl, Map<String, Object> values);

}
