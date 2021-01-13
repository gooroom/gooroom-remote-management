package kr.gooroom.gpms.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @Class Name : MessageSourceHelper.java
 * @Description : MessageSourceHelper Class
 * @Modification Information
 * @
 * @ 수정일       수정자     수정내용
 * @ ---------- --------- -------------------------------
 * @ 2017.05.08 LDH     최초생성
 *
 * @since 2017.05.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by Hancom All right reserved.
 */
public class MessageSourceHelper {

	private static final Logger logger = LoggerFactory.getLogger(MessageSourceHelper.class);

	private static MessageSource messageSource = null;

	private MessageSourceHelper(MessageSource messageSource) {
		MessageSourceHelper.messageSource = messageSource;
	}

	/**
	 * @return the messageSource
	 */
	public static MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	public static void setMessageSource(MessageSource messageSource) {
		MessageSourceHelper.messageSource = messageSource;
	}

	/**
	 * I18n Support
	 * 
	 * @param code
	 *            message code
	 * @return message or code if none defined
	 */
	public static String getMessage(String code) {
		try {
			return messageSource == null ? code : messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
		} catch (Exception e) {
			logger.error(e.toString());
			return code;
		}
	}

	/**
	 * I18n Support
	 * 
	 * @param msr
	 *            message source resolvable
	 * @return message or code if none defined
	 */
	public static String getMessage(MessageSourceResolvable msr) {
		return messageSource == null ? msr.getDefaultMessage()
				: messageSource.getMessage(msr, LocaleContextHolder.getLocale());
	}

	public static String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}
	
	public static String getMessage(String code, String args) {
		return messageSource.getMessage(code, new String[]{args}, LocaleContextHolder.getLocale());
	}
}
