package com.example.zuulserver.commons;

import java.io.UnsupportedEncodingException;

public class StringUtils {
	public static final String EMPTY = "";

	  public static boolean isNotNULL(String str)
	  {
	    return str != null;
	  }

	  public static boolean isNULL(String str)
	  {
	    return str == null;
	  }

	  public static boolean isEmpty(String str)
	  {
	    return (str == null) || (str.length() == 0);
	  }

	  public static boolean isNotEmpty(String str)
	  {
	    return !isEmpty(str);
	  }

	  public static boolean isBlank(String str)
	  {
	    int strLen;
	    if ((str == null) || ((strLen = str.length()) == 0))
	      return true;

	    for (int i = 0; i < strLen; i++) {
	      if (!Character.isWhitespace(str.charAt(i))) {
	        return false;
	      }
	    }
	    return true;
	  }

	  public static boolean isNotBlank(String str)
	  {
	    return !isBlank(str);
	  }

	  public static String trim(String str)
	  {
	    return str == null ? null : str.trim();
	  }

	  public static String trimToNull(String str)
	  {
	    String ts = trim(str);
	    return isEmpty(ts) ? null : ts;
	  }

	  public static String trimToEmpty(String str)
	  {
	    return str == null ? "" : str.trim();
	  }

	  public static byte[] getBytes(String content, String charset)
	  {
	    if (isNULL(content)) {
	      content = "";
	    }
	    if (isBlank(charset))
	      throw new IllegalArgumentException("charset can not null");
	    try
	    {
	      return content.getBytes(charset); } catch (UnsupportedEncodingException e) {
	    }
	    throw new RuntimeException("charset is not valid,charset is:" + charset);
	  }

	  public static byte[] getBytes(String content)
	  {
	    if (isNULL(content)) {
	      content = "";
	    }
	    return content.getBytes();
	  }

	  public static String getString(byte[] binaryData, String charset) {
	    try {
	      return new String(binaryData, charset); } catch (UnsupportedEncodingException e) {
	    }
	    throw new RuntimeException("charset is not valid,charset is:" + charset);
	  }
}
