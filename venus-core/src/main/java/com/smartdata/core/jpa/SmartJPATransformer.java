package com.smartdata.core.jpa;


import com.google.common.base.CaseFormat;
import org.hibernate.HibernateException;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyMapImpl;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.math.BigDecimal;
import java.util.Arrays;

@SuppressWarnings("rawtypes")
public class SmartJPATransformer extends AliasedTupleSubsetResultTransformer {

	// IMPL NOTE : due to the delayed population of setters (setters cached
	// 		for performance), we really cannot properly define equality for
	// 		this transformer

	/**
	 * Comment for <code>serialVersionUID</code>,｛该处说明该变量的含义及作用｝
	 */
	private static final long serialVersionUID = 1L;
	private final Class resultClass;
	private boolean isInitialized;
	private String[] aliases;
	private Setter[] setters;

	
	public SmartJPATransformer(Class resultClass) {
		if ( resultClass == null ) {
			throw new IllegalArgumentException( "resultClass cannot be null" );
		}
		isInitialized = false;
		this.resultClass = resultClass;
	}

	@Override
	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;

		try {
			if ( ! isInitialized ) {
				initialize( aliases );
			}
			else {
				check( aliases );
			}
			
			result = resultClass.newInstance();

			for ( int i = 0; i < aliases.length; i++ ) {
				if ( setters[i] != null ) {
					Object value = tuple[i];
					//System.out.println(result.getClass().getName()+"========="+tuple[i].getClass().getName());
					if(value instanceof BigDecimal ) {
						Object longValue = ((BigDecimal) value).longValue();
						setters[i].set( result, longValue, null );
					}else {
						setters[i].set( result, tuple[i], null );
					}					
				}
			}
		}
		catch ( InstantiationException e ) {
			throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
		}
		catch ( IllegalAccessException e ) {
			throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
		}

		return result;
	}

	private void initialize(String[] aliases) {
		PropertyAccessStrategyChainedImpl propertyAccessStrategy = new PropertyAccessStrategyChainedImpl(
				PropertyAccessStrategyBasicImpl.INSTANCE,
				PropertyAccessStrategyFieldImpl.INSTANCE,
				PropertyAccessStrategyMapImpl.INSTANCE
		);
		this.aliases = new String[ aliases.length ];
		setters = new Setter[ aliases.length ];
		for ( int i = 0; i < aliases.length; i++ ) {
			String alias = aliases[ i ];
			if ( alias != null ) {
				this.aliases[ i ] = alias;
				String cam = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,alias);
				setters[ i ] = propertyAccessStrategy.buildPropertyAccess( resultClass,cam).getSetter();
			}
		}
		isInitialized = true;
	}
	
	/*public static String camelName(String name) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,name);
	}*/  

	private void check(String[] aliases) {
		if ( ! Arrays.equals( aliases, this.aliases ) ) {
			throw new IllegalStateException(
					"aliases are different from what is cached; aliases=" + Arrays.asList( aliases ) +
							" cached=" + Arrays.asList( this.aliases ) );
		}
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}

		SmartJPATransformer that = ( SmartJPATransformer ) o;

		if ( ! resultClass.equals( that.resultClass ) ) {
			return false;
		}
		if ( ! Arrays.equals( aliases, that.aliases ) ) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = resultClass.hashCode();
		result = 31 * result + ( aliases != null ? Arrays.hashCode( aliases ) : 0 );
		return result;
	}
}
