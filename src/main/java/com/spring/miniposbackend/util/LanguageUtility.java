package com.spring.miniposbackend.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LanguageUtility {

	@Autowired
	private MessageSource messageSource;

	public String getLanaugeCode() {
		return getLocale().getLanguage();
	}

	private Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}

	public String getMessage(String message, String[] params) {
		return messageSource.getMessage(message, params, getLocale());
	}
}
