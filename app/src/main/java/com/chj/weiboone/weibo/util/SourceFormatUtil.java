package com.chj.weiboone.weibo.util;

/**
 * Created by C on 2016/5/14.
 */
public class SourceFormatUtil {
    /**
     * 微博来源格式转换(设备)
     *
     * @param source
     * @return
     */
    public static String format(String source) {
        String newSource;
        int start = source.indexOf("\">", 0);
        int end = source.indexOf("</a>", 0);
        if (start + 2 < end) {
            newSource = source.substring(start + 2, end);
        } else {
            newSource = "";
        }
        return newSource;
    }
}
