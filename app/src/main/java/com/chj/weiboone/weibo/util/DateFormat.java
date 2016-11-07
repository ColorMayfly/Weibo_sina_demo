package com.chj.weiboone.weibo.util;

/**
 * 发表微博时间格式
 * Created by C on 2016/5/1.
 */
public class DateFormat {

    private String month, day, timeOfDay;

    public String format(String stringDate) {
        String ret = "";
        String s = stringDate.replaceAll(" ", "-");
        String[] split = s.split("-");
        String str = split[1];
        day = split[2];
        timeOfDay = split[3];
        switch (str) {
            case "Jan":
                month = "01";
                break;
            case "Feb":
                month = "02";
                break;
            case "Mar":
                month = "03";
                break;
            case "Apr":
                month = "04";
                break;
            case "May":
                month = "05";
                break;
            case "Jun":
                month = "06";
                break;
            case "Jul":
                month = "07";
                break;
            case "Aug":
                month = "08";
                break;
            case "Sep":
                month = "09";
                break;
            case "Oct":
                month = "10";
                break;
            case "Nov":
                month = "11";
                break;
            case "Dec":
                month = "12";
                break;
            default:
                break;
        }

        ret = month + "-" + day + " " + timeOfDay;

        return ret;
    }
}
