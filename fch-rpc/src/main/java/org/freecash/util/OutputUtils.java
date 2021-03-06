package org.freecash.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.freecash.core.util.StringUtils;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;


import org.freecash.core.util.CollectionUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OutputUtils {

	private static final int LINE_SEPARATOR_LENGTH = 89;
	
	
	public static void printResult(String methodName, String[] paramNames, Object[] paramValues,
			Object result) {
		List<Object> printables = new ArrayList<Object>();
		printables.add(methodName);
		if(!(paramNames == null || paramValues == null)) {
			printables.addAll(CollectionUtils.mergeInterlaced(Arrays.asList(paramNames), 
					Arrays.asList(paramValues)));
		}
		printables.add(result);
//		System.out.printf("'freecash' response for JSON-RPC API call '%s(" + StringUtils.repeat(
//				"%s=%s, ", (printables.size() - 2)/2).replaceAll(", $", "") + ")' was: '%s'\n",
//				printables.toArray());
	}
	
	public static void printSeparator() {
		printSeparator(LINE_SEPARATOR_LENGTH);
	}
	
	public static void printSeparator(int length) {
		System.out.println(StringUtils.repeat('-', length));
	}
}