package com.tys.util.custom;

import org.springframework.security.core.authority.GrantedAuthorityImpl;

@SuppressWarnings("deprecation")
public class CustomGrantedAuthorityImpl extends GrantedAuthorityImpl implements Comparable<Object>{

	private static final long serialVersionUID = 165060534618193280L;

	public CustomGrantedAuthorityImpl(String role) {
		super(role);
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
