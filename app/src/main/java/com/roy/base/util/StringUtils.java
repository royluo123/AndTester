package com.roy.base.util;

import android.graphics.Paint;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {
    
    private StringUtils(){
        
    }

    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }
    

    public static boolean isEmpty(StringBuffer stringBuffer){
        return stringBuffer == null || stringBuffer.length() == 0;
    }
    

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }
    

    public static String toEmpty(String text) {
        return text == null ? "" : text;
    }

   
    public static String merge(String... texts) {
        if(texts == null){
            return "";
        }         

        StringBuffer buffer = new StringBuffer(32 * texts.length * 10);
        for(int j = 0; j < texts.length; j++){
            buffer.append(texts[j]);
        }
        return buffer.toString();
    }
    
 
  
    public static int hashCode(String text) {
        int sID = 0;
        int sLength = text.length();
        for (int i = 0; i < sLength; i++) {
            sID = (sID << 5) - sID + text.charAt(i);
        }    
        return sID;
    }
    

    public static String replaceAll(String srcString, String matchingString, String replacement) {
        if (isEmpty(srcString) || isEmpty(matchingString) || isEmpty(replacement)) {
            return null;
        }

        StringBuffer sResult = new StringBuffer();
        int sIndex = 0;
        int sMaxIndex = srcString.length() - 1;
        while ((sIndex = srcString.indexOf(matchingString)) != -1) {
            String sPreStr = srcString.substring(0, sIndex);
            sResult.append(sPreStr).append(replacement);
            srcString = (sIndex < sMaxIndex) ? srcString.substring(sIndex + matchingString.length()) : "";
        }
        sResult.append(srcString);

        return sResult.toString();
    }
    

    public static String replaceAllChars(String srcString, char[] filterChars, char replaceChar){
        if(isEmpty(srcString) || filterChars == null || filterChars.length == 0){
            return srcString;
        }
        for (int i = 0; i < filterChars.length; i++) {
            srcString = srcString.replace(filterChars[i],replaceChar);
        }
        
        int sStartIndex= srcString.indexOf(replaceChar);
        if(sStartIndex != -1){
            StringBuffer sTempSB = new StringBuffer(srcString);
            for (int i = sStartIndex; i < sTempSB.length(); i++) {
                if(sTempSB.charAt(i) == replaceChar && (i-1 > 0 && sTempSB.charAt(i - 1) == replaceChar)){
                    sTempSB.deleteCharAt(i);
                    i--;
                }
            }
            srcString = sTempSB.toString();
        }
        return srcString;
    }
    

    public static String[] split(String original, String regex) {
        return split(original,regex,true);
    }


    public static String[] split(String original, String regex, boolean canNull) {
        if (isEmpty(original)) {
            return new String[0];
        }
        if(isEmpty(regex)){
            return  new String[]{original};
        }
        String[] sTarget = null;
        int sTargetLength = 0;
        int sLength = original.length();
        int sStartIndex = 0;
        int sEndIndex = 0;

        //扫描字符串，确定目标字符串数组的长度
        for (sEndIndex = original.indexOf(regex, 0); sEndIndex != -1 && sEndIndex < sLength;
                         sEndIndex = original.indexOf(regex, sEndIndex)) {
            sTargetLength += (canNull || sStartIndex != sEndIndex) ? 1 : 0;
            sStartIndex = sEndIndex += sEndIndex >= 0 ? regex.length() : 0;
        }

        //如果最后一个标记的位置非字符串的结尾，则需要处理结束串
        sTargetLength += canNull || sStartIndex != sLength ? 1 : 0;

        //重置变量值，根据标记拆分字符串
        sTarget = new String[sTargetLength];
        int sIndex = 0;
        for (sIndex = 0, sEndIndex = original.indexOf(regex, 0), sStartIndex = 0;
                sEndIndex != -1 && sEndIndex < sLength;
                sEndIndex = original.indexOf(regex, sEndIndex)) {
            if (canNull || sStartIndex != sEndIndex) {
                sTarget[sIndex] = original.substring(sStartIndex, sEndIndex);
                ++sIndex;
            }
            sStartIndex = sEndIndex += sEndIndex >= 0 ? regex.length() : 0;
        }

        //取结束的子串
        if (canNull || sStartIndex != sLength) {
            sTarget[sTargetLength - 1] = original.substring(sStartIndex); ;
        }

        return sTarget;
    }

    public static String[] splitAndTrim(String value, String expr){
        value = value.replace(" ", "");
        return value.split(expr);
    }
    

    public static boolean startsWithIgnoreCase(String originalString, String prefixString){
        return (originalString == null && prefixString == null)
                || (isNotEmpty(originalString) && isNotEmpty(prefixString) && regionMatches(true, originalString, 0, originalString.length(), prefixString, 0, prefixString.length()));
    }

    public static boolean endsWithIgnoreCase(String originalString, String suffixString){
        return (originalString == null && suffixString == null) || (isNotEmpty(originalString) && isNotEmpty(suffixString) && 
                regionMatches(true, originalString, originalString.length() - suffixString.length(), originalString.length(), suffixString, 0, suffixString.length()));
    }
    

    public static boolean equalsIgnoreCase(String originalString, String anotherString){
        return originalString != null && anotherString != null && originalString.length() == anotherString.length() 
        && regionMatches(true, originalString, 0, originalString.length(), anotherString, 0, anotherString.length());
    }
    

    public static boolean regionMatches(boolean ignoreCase, String text, int textOffset, int textLength, String otherText, int otherOffset, int otherLength) {
        if(textOffset < 0 || otherOffset <0  
                || textLength < 0 || otherLength < 0
                || textOffset > textLength || otherOffset > otherLength 
                || textLength - textOffset < otherLength - otherOffset) return false;
        char c1;
        char c2; 
        while (otherLength-- > 0) {
            c1 = text.charAt(textOffset++);
            c2 = otherText.charAt(otherOffset++);
            if (c1 == c2 || (ignoreCase && (Character.toLowerCase(c1) == c2 || Character.toUpperCase(c1) == c2)))
                continue;
            
            return false;
        }
        return true;
    }
    
    public static int indexOfIgnoreCase(String originalString, String text, int fromIndex) {
        if (originalString == null || text == null)
            return -1;

        int aTextLength = originalString.length();
        int sOtherTextLength = text.length();
        int sMax = (aTextLength - sOtherTextLength);
        if (fromIndex >= aTextLength) {
            if (aTextLength == 0 && fromIndex == 0 && sOtherTextLength == 0) {
                /* There is an empty string at index 0 in an empty string. */
                return 0;
            }
            /* Note: fromIndex might be near -1>>>1 */
            return -1;
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (sOtherTextLength == 0) {
            return fromIndex;
        }

        char aFirst = Character.toLowerCase(text.charAt(0));
        int i = fromIndex;

        startSearchForFirstChar: while (true) {

            /* Look for first character. */
            while (i <= sMax && Character.toLowerCase(originalString.charAt(i)) != aFirst) {
                i++;
            }
            if (i > sMax) {
                return -1;
            }

            /* Found first character, now look at the rest of v2 */
            int j = i + 1;
            int end = j + sOtherTextLength - 1;
            int k = 1;

            while (j < end) {
                char c1 = Character.toLowerCase(originalString.charAt(j++));
                char c2 = Character.toLowerCase(text.charAt(k++));
                if (c1 != c2) {
                    i++;
                    /* Look for str's first char again. */
                    continue startSearchForFirstChar;
                }
            }
            return i; /* Found whole string. */
        }
    }
    

    public static int parseInt(String value, int defalutValue){
        if (value == null || value.length() == 0)
              return defalutValue;
          int result = defalutValue;
          boolean isHex = false;
          if(isHex = value.startsWith("0x")){
             value = value.substring(2);
          }
          try {
              if (!isHex) {
                  result = Integer.parseInt(value);
              } else {
                  result = (int) Long.parseLong(value, 16);
              }
          }
          catch (Exception ex) {
              //#if (debug == true)
              ex.printStackTrace();
              //#endif 
          }
          return result;
    }

    public static long parseLong(String aValue, long aDefault){
        if (aValue == null || aValue.length() == 0)
              return aDefault;
          long result = aDefault;
          boolean isHex = false;
          if(isHex = aValue.startsWith("0x")){
             aValue = aValue.substring(2);
          }
          try {
              if (!isHex) {
                  result = Long.parseLong(aValue);
              } else {
                  result = Long.parseLong(aValue, 16);
              }
          } catch (Exception ex) {
          }
          return result;
    }

    
    public static long parseLong(String value) {
    	 return parseLong(value, 0);
    }
    

    public static int parseInt(String aValue) {
        return parseInt(aValue, 0);
    }
    

	public static int getTextHeight(Paint paint) {
		return paint == null ? 0 : Math.round(paint.descent() - paint.ascent());
	}
    

    public static boolean isArabicFarsiUrdu(String text){
        for(int i = 0 ; i < text.length() ; i++){
            char aChar = text.charAt(i);
            if(aChar >= 0x621 && aChar <= 0x6FE){
                return true;
            }
        }
        return false;
    }
    
    public static char findCharByText(String srcText, String targetText){
        int sIndex = findIndexByText(srcText, targetText);
        if(sIndex != -1){
            return srcText.toLowerCase().charAt(sIndex);
        }
        return ' ';
    }
    public static int findIndexByText(String srcText, String targetText){
        if(srcText == null || targetText == null){
            return -1;
        }
        srcText = srcText.toUpperCase();
        targetText = targetText.toUpperCase();
        boolean isExist = false;
        for(int j=0; j<srcText.length(); j++){
            isExist = false;
            // 过滤空格
            if(srcText.charAt(j) == ' '){
                continue;
            }
            for(int h=0; h<targetText.length(); h++){
                if(srcText.charAt(j) == targetText.charAt(h)){
                    isExist = true;
                    break;
                }
                
            }
            if(!isExist){
                return j;
            }
        }
        return -1;
        
    }
    
    public final static String utf8ByteToString(byte[] body){
        if(body == null)
            return "";
        return utf8ByteToString(body, 0, body.length);
    }

    public final static String utf8ByteToString(byte[] aData, int aOffset){
        if(aData == null || aOffset < 0)
            return "";
        int utflen = IOUtils.readShort(aData,aOffset);
        return utf8ByteToString(aData,aOffset+2,utflen);
    }
    

    public final static String utf8ByteToString(byte[] aData, int aOffset, int aLength){
        if(aData == null || aOffset < 0 || aLength <= 0)
            return "";

        StringBuffer sStringBuffer = new StringBuffer(aLength>>1);
        utf8ByteToString(aData,aOffset,aLength,sStringBuffer);
        // The number of chars produced may be less than utflen,use new string(stringbuffer) can trim the useless blank chars 
        return new String(sStringBuffer);
    }
    

    public static final int utf8ByteToString(byte[] bytes , int aReadOffset , int aReadSize, StringBuffer aBuffer) {
        if (bytes == null || bytes.length == 0 || aBuffer == null || aReadOffset < 0 || aReadSize <= 0)
            return 0;
        
        int index = aReadOffset;
        aReadSize += aReadOffset;
        do {
            if (index >= aReadSize) {
                break;
            }
            int i;
            switch ((i = bytes[index] & 0xff) >> 4) {
            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 3: // '\003'
            case 4: // '\004'
            case 5: // '\005'
            case 6: // '\006'
            case 7: // '\007'
                index++;
                /* ch[chIndex++] = (char) i; */
                aBuffer.append((char) i);
                break;

            case 12: // '\f'
            case 13: // '\r'
                if ((index += 2) > aReadSize) {
                    return aBuffer.length();
                }
                byte byte0;
                if (((byte0 = bytes[index - 1]) & 0xc0) != 128) {
                    return aBuffer.length();
                }
                /* ch[chIndex++] = (char) ((i & 0x1f) << 6 | byte0 & 0x3f); */
                aBuffer.append((char) ((i & 0x1f) << 6 | byte0 & 0x3f));
                break;

            case 14: // '\016'
                if ((index += 3) > aReadSize) {
                    return aBuffer.length();
                }
                byte byte1 = bytes[index - 2];
                byte byte2 = bytes[index - 1];
                if ((byte1 & 0xc0) != 128 || (byte2 & 0xc0) != 128) {
                    return aBuffer.length();
                }
                /*
                 * ch[chIndex++] = (char) ((i & 0xf) << 12 | (byte1 & 0x3f) << 6
                 * | (byte2 & 0x3f) << 0);
                 */
                aBuffer.append((char) ((i & 0xf) << 12 | (byte1 & 0x3f) << 6 | (byte2 & 0x3f) << 0));
                break;

            case 8: // '\b'
            case 9: // '\t'
            case 10: // '\n'
            case 11: // '\013'
            default:
                return aBuffer.length();
            }
        } while (true);
        return aBuffer.length();
    }
    
    public static final byte[] createUTFByte(String text){
        return createStringByte(text, "UTF-8");
    }
    
    public static final byte[] createStringByte(String sText, String sCharset){
        if (sText == null)
            return null;
        try {
            //默认是发utf-8
            return sText.getBytes( sCharset = isEmpty(sCharset) ? "UTF-8" : sCharset);
        } catch (Exception e) {
            try{
               return sText.getBytes(sCharset.toLowerCase());
            }catch(Exception e1){
            }

            return sText.getBytes();
        }
    }
    
    public static String nullProtected(String text){
        return text == null? "" : text;
    }
    

    public static boolean wildcardMatch(String matchStr, String wildcardStr) {
        if(isEmpty(matchStr) || isEmpty(wildcardStr))
            return false;

        boolean sRet = false;
        String sWildcardStr = wildcardStr.toLowerCase().trim();
        if(sWildcardStr.startsWith("*")) {
            if(matchStr.endsWith(sWildcardStr.substring(1))) {
                sRet = true;
            } else if(sWildcardStr.startsWith("*.")) {//add by liqw 2013-11-14 mantsi:0225101
                if(matchStr.equals(sWildcardStr.substring(2))) {
                    sRet = true;
                }
            }
        } else {
        	
            if(matchStr.equals(sWildcardStr) //全域名匹配
            		|| matchStr.endsWith(sWildcardStr)) { //没有通配符，只有一级域名的匹配
                sRet = true;
            }
        }

        return sRet;
    }
    
    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equals(str2);
    }

    /**
     * @return 是否包含中文
     */
    public static boolean containsChinese(String str) {
    	try{
    		Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
    		Matcher m = p.matcher(str);
    		if (m.find()) {
    			return true;
    		}
    	}catch(Exception e){
			//#if (debug == true)
			e.printStackTrace();
			//#endif
    	}
    	return false;
    }

    public static String subString(String src, String preString){
		String result = src;
		if(isNotEmpty(src) && isNotEmpty(preString) && src.length() > preString.length()
				&& src.indexOf(preString) > -1){
			result = src.substring(preString.length());
		}
		return result;
	}

    public static String getStringAndReplaceFromXML(String source, String... args) {
        if(source!=null)
        {
            for (int i=0;i<args.length;i++) {
                source=source.replace("[spstr"+(i+1)+"]", args[i]+"");
            }
        }
        return source;
    }

    public static boolean startWithIgnoreCase(String oriString, String startString) {
        if (oriString == null || startString == null) return false;

        int length = startString.length();
        if(length > oriString.length()) {
            return false;
        }
        if (startString.equalsIgnoreCase(oriString.substring(0, length))) {
            return true;
        } else {
            return false;
        }
    }

}
