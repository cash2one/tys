package com.tys.util.custom;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	public static final String DS_BSLV3 = "ds_bslv3";
	public static final String DS_BSLV3_BAK = "ds_bslv3_bak";

	//利用ThreadLocal解决线程安全问题
	private static String type = DS_BSLV3;

	public static void setCustomerType(String customerType) {
		type = customerType;
	}
	

	public static String getCustomerType() {
		return type;
	}

	public static void clearCustomerType() {
		type = DS_BSLV3;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return getCustomerType();
	}


	@Override
	protected DataSource determineTargetDataSource() {
		LazyConnectionDataSourceProxy ds = (LazyConnectionDataSourceProxy) super.determineTargetDataSource();
		
		try {
			if(ds.getConnection() == null){
				//切换数据源
				if(DynamicDataSource.DS_BSLV3.equals(DynamicDataSource.getCustomerType()))
					DynamicDataSource.setCustomerType(DynamicDataSource.DS_BSLV3_BAK);
				else
					DynamicDataSource.setCustomerType(DynamicDataSource.DS_BSLV3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			//切换数据源
			if(DynamicDataSource.DS_BSLV3.equals(DynamicDataSource.getCustomerType()))
				DynamicDataSource.setCustomerType(DynamicDataSource.DS_BSLV3_BAK);
			else
				DynamicDataSource.setCustomerType(DynamicDataSource.DS_BSLV3);
		}
		
		return ds; 
	}
	
	
	

	

}
