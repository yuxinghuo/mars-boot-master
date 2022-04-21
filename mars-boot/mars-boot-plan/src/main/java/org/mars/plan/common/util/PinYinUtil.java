package org.mars.plan.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {

    /**
     * 汉语拼音转首字母缩略词
     * 
     * @param hanyuPinyin
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String HanyuPinyin2Acronym(String hanyuPinyin) throws BadHanyuPinyinOutputFormatCombination {
        if (null==hanyuPinyin || "".equals(hanyuPinyin)) {
            return null;
        }
        String acronym = "";
        char tempChar;
        int length = hanyuPinyin.length();
        String[] tempResult;
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        for (int i = 0; i < length; i++) {
            tempChar = hanyuPinyin.charAt(i);
            if (String.valueOf(tempChar).matches("[\\u4E00-\\u9FA5]+")) {
                // 如果是汉字字符，则转为汉语拼音
                tempResult = PinyinHelper.toHanyuPinyinStringArray(tempChar, outputFormat);
                acronym += tempResult[0].charAt(0);
            } else {
                acronym += String.valueOf(tempChar);
            }
        }
        return acronym.toUpperCase();
    }
}