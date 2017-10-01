package org.neutrinocms.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.neutrinocms.core.bean.NField;
import org.neutrinocms.core.exception.UtilException;
import org.springframework.stereotype.Component;

@Component
public class NFieldUtil {
	private Logger logger = Logger.getLogger(NFieldUtil.class);
	public Object defaultValueToFieldType(NField nField) throws UtilException{
		try {
			String value = nField.getDefaultValue();
			switch (nField.getType()) {
			case BOOLEAN:
				return Boolean.parseBoolean(value);
			case INTEGER:
				return Integer.parseInt(value);
			case FLOAT:
				return Float.parseFloat(value);
			case DOUBLE:
				return Double.parseDouble(value);
			case DATETIME:
				SimpleDateFormat formatDatetime = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				return formatDatetime.parse(value);
			case DATE:
				SimpleDateFormat formatDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				return formatDate.parse(value);
			case TIME:
				SimpleDateFormat formatTime = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				return formatTime.parse(value);
			default:
				return value;
			}
		} catch (ParseException e) {
			throw new UtilException(e);
		}
		
	}
}
