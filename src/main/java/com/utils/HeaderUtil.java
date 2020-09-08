package com.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for creating HTTP headers
 * Very useful for building ResponseEntity objects *
 */
public class HeaderUtil {
	
	private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

	private HeaderUtil() {
	}
	
	public static HttpHeaders createAlert(String message, String param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Maps-alert", message);
		try {
			headers.add("Maps-params", URLEncoder.encode(param, StandardCharsets.UTF_8.toString()));
		} catch(UnsupportedEncodingException e) {
			log.debug("HTTP createAlert UnsupportedEncodingException");
		}
		return headers;
	}

}
