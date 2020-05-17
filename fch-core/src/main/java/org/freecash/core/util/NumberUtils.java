package org.freecash.core.util;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.freecash.core.common.Defaults;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumberUtils {
	
	public static boolean isEven(int integer) {
		if(integer % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static <T> Map<T, BigDecimal> setValueScale(Map<T, BigDecimal> pairs, int newScale) {
		Iterator<Map.Entry<T, BigDecimal>> iterator = pairs.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<T, BigDecimal> pair = iterator.next();
			pair.setValue(pair.getValue().setScale(newScale, Defaults.ROUNDING_MODE));
		}
		return pairs;
	}
}