package com.emxcel.dms.core.modules.utils;

public interface Encryption {
	
	public String encrypt(String value) throws Exception;
	
	public String decrypt(String value) throws Exception;

}
