package com.smartdata.core.jpa;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public class NativeSqlExecutorImpl implements NativeSqlExecutor {

	@Autowired
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public <V> List<V> querySqlList(String sql, Class<V> clasz, Object... values) {
		Assert.notNull(sql, "sql不能为空");
		Assert.notNull(clasz, "clasz不能为空");
		NativeQuery<V> query = createSqlQuery(sql);
		setSqlParameters(query, values);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(new SmartJPATransformer(clasz));
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <V> List<V> querySqlList(String sql, Class<V> clasz, Map<String, Object> mapValues) {
		Assert.notNull(sql, "sql不能为空");
		Assert.notNull(clasz, "clasz不能为空");
		NativeQuery<V> query = createSqlQuery(sql);
		setSqlParameters(query, mapValues);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(new SmartJPATransformer(clasz));
		return query.list();
	}

	@Override
	public <V> Page<V> querySqlPage(Pageable pageable, String sql, Class<V> clasz, Map<String, Object> mapValues) {
		Assert.notNull(pageable, "pageable不能为空");
		Assert.notNull(sql, "sql不能为空");
		Assert.notNull(clasz, "clasz不能为空");
		long totalCount = countSqlResult(sql, mapValues, clasz);
		List<V> data = queryDataResult(pageable, sql, clasz, mapValues);
		return new PageImpl<V>(data, pageable, totalCount);
	}

	@Override
	public <V> Page<V> querySqlPage(Pageable pageable, String sql, Class<V> clasz, Object... values) {
		Assert.notNull(pageable, "pageable不能为空");
		Assert.notNull(sql, "sql不能为空");
		Assert.notNull(clasz, "clasz不能为空");
		long totalCount = countSqlResult(sql, values);
		List<V> data = queryDataResult(pageable, sql, clasz, values);
		return new PageImpl<V>(data, pageable, totalCount);
	}

	@SuppressWarnings("unchecked")
	public <V> List<V> queryDataResult(Pageable pageable, String sql, Class<V> clasz, Map<String, Object> mapValues) {
		NativeQuery<V> query = createSqlQuery(sql);
		setSqlParameters(query, mapValues);
		setSqlPageInfo(query, pageable, sql, clasz);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public <V> List<V> queryDataResult(Pageable pageable, String sql, Class<V> clasz, Object... values) {
		NativeQuery<V> query = createSqlQuery(sql);
		setSqlParameters(query, values);
		setSqlPageInfo(query, pageable, sql, clasz);
		return query.list();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public long countSqlResult(String sql, Map<String, Object> mapValues) {
		Long count = 0L;
		try {
			NativeQuery query = createSqlQuery(changeToCountSql(sql));
			setSqlParameters(query, mapValues);
			Object obj = query.uniqueResult();
			if (obj instanceof BigDecimal) {
				count = ((BigDecimal) obj).longValue();
			} else if (obj instanceof BigInteger) {
				count = ((BigInteger) obj).longValue();
			} else {
				count = ((Integer) obj).longValue();
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("hql can't be auto count, sql is:" + sql, e);
		}
		return count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public long countSqlResult(String sql, Object... values) {
		Long count = 0L;
		try {
			NativeQuery query = createSqlQuery(changeToCountSql(sql));
			setSqlParameters(query, values);
			Object obj = query.uniqueResult();
			if (obj instanceof BigDecimal) {
				count = ((BigDecimal) obj).longValue();
			} else if (obj instanceof BigInteger) {
				count = ((BigInteger) obj).longValue();
			} else {
				count = ((Integer) obj).longValue();
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("hql can't be auto count, sql is:" + sql, e);
		}
		return count;
	}

	@SuppressWarnings("rawtypes")
	public NativeQuery createSqlQuery(String queryString) {
		Session session = entityManager.unwrap(org.hibernate.Session.class);
		NativeQuery query = session.createSQLQuery(queryString);
		return query;
	}

	public <V> void setSqlParameters(NativeQuery<V> query, Map<String, Object> mapValues) {
		if (!CollectionUtils.isEmpty(mapValues)) {
			for (Map.Entry<String, Object> entry : mapValues.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}

	public <V> void setSqlParameters(NativeQuery<V> query, Object... values) {
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
	}

	public <V> void setSqlPageInfo(NativeQuery<V> query, Pageable pageable, String sql, Class<V> clasz) {
		query.setFirstResult(pageable.getPageNumber() - 1);
		query.setMaxResults(pageable.getPageSize());
		query.unwrap(NativeQueryImpl.class).setResultTransformer(new SmartJPATransformer(clasz));
	}

	public String changeToCountSql(String fromSql) {
		fromSql = " from " + StringUtils.substringAfter(fromSql, "from");
		fromSql = StringUtils.substringBefore(fromSql, "order by");
		if (fromSql.contains("group by")) {
			return "select count(*) from ( select " + StringUtils.substringAfter(fromSql, "group by") + fromSql + " )";
		} else {
			return "select count(*) " + fromSql;
		}
	}

}
