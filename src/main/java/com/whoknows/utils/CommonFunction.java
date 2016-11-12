package com.whoknows.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CommonFunction {

	public static String getUserName(String first_name, String last_name, String email) {
		String name = new String();
		if (last_name != null && StringUtils.isNotEmpty(last_name)) {
			name += last_name;
		}
		if (first_name != null && StringUtils.isNotEmpty(first_name)) {
			name += first_name;
		}
		if (StringUtils.isNotEmpty(email)) {
			name = email;
		}
		return name;
	}
}
