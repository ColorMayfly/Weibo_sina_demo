package com.chj.weiboone.weibo.entity;

import java.util.List;

/**简单微博信息展示
 *
 *
 * Created by C on 2016/5/1.
 */
public class Info {


    /**
     * previous_cursor : 0
     * total_number : 10
     * next_cursor : 0
     * interval : 0
     * statuses : [{"pic_urls":[{"thumbnail_pic":"http://ww2.sinaimg.cn/thumbnail/9d84ac05gw1f3fznof1dhj20qo0zktgs.jpg"},{"thumbnail_pic":"http://ww4.sinaimg.cn/thumbnail/9d84ac05gw1f3fznpnyb2j20qo0zkn2w.jpg"}],"visible":{"type":0,"list_id":0},"textLength":60,"attitudes_count":0,"darwin_tags":[],"isLongText":false,"in_reply_to_screen_name":"","mlevel":0,"source_type":1,"truncated":false,"userType":0,"thumbnail_pic":"http://ww2.sinaimg.cn/thumbnail/9d84ac05gw1f3fznof1dhj20qo0zktgs.jpg","id":3970368925020590,"idstr":"3970368925020590","original_pic":"http://ww2.sinaimg.cn/large/9d84ac05gw1f3fznof1dhj20qo0zktgs.jpg","in_reply_to_status_id":"","reposts_count":0,"created_at":"Sun May 01 16:59:40 +0800 2016","biz_ids":[100101],"comments_count":0,"text":"不想多，那就做多！图书馆，不见不散[拜拜] http://t.cn/R2WJCX5","text_tag_tips":[],"geo":{"type":"Point","coordinates":[31.10248,121.387329]},"source_allowclick":0,"bmiddle_pic":"http://ww2.sinaimg.cn/bmiddle/9d84ac05gw1f3fznof1dhj20qo0zktgs.jpg","hot_weibo_tags":[],"source":"<a href='http://app.weibo.com/t/feed/3o33sO' rel='nofollow'>iPhone 6<\/a>","favorited":false,"biz_feature":4294967300,"in_reply_to_user_id":"","annotations":[{"place":{"poiid":"8008631011200000009","title":"莘庄","lon":121.387329,"type":"checkin","lat":31.10248},"client_mblogid":"iPhone-A4B4BD86-03F7-410A-9816-CC3C3C1C8C63"},{"mapi_request":true}],"mid":"3970368925020590"}]
     */

    private int previous_cursor;
    private int total_number;
    private int next_cursor;
    private int interval;
    /**
     * pic_urls : [{"thumbnail_pic":"http://ww2.sinaimg.cn/thumbnail/9d84ac05gw1f3fznof1dhj20qo0zktgs.jpg"},{"thumbnail_pic":"http://ww4.sinaimg.cn/thumbnail/9d84ac05gw1f3fznpnyb2j20qo0zkn2w.jpg"}]
     * visible : {"type":0,"list_id":0}
     * textLength : 60
     * attitudes_count : 0
     * darwin_tags : []
     * isLongText : false
     * in_reply_to_screen_name :
     * mlevel : 0
     * source_type : 1
     * truncated : false
     * userType : 0
     * thumbnail_pic : http://ww2.sinaimg.cn/thumbnail/9d84ac05gw1f3fznof1dhj20qo0zktgs.jpg
     * id : 3970368925020590
     * idstr : 3970368925020590
     * original_pic : http://ww2.sinaimg.cn/large/9d84ac05gw1f3fznof1dhj20qo0zktgs.jpg
     * in_reply_to_status_id :
     * reposts_count : 0
     * created_at : Sun May 01 16:59:40 +0800 2016
     * biz_ids : [100101]
     * comments_count : 0
     * text : 不想多，那就做多！图书馆，不见不散[拜拜] http://t.cn/R2WJCX5
     * text_tag_tips : []
     * geo : {"type":"Point","coordinates":[31.10248,121.387329]}
     * source_allowclick : 0
     * bmiddle_pic : http://ww2.sinaimg.cn/bmiddle/9d84ac05gw1f3fznof1dhj20qo0zktgs.jpg
     * hot_weibo_tags : []
     * source : <a href='http://app.weibo.com/t/feed/3o33sO' rel='nofollow'>iPhone 6</a>
     * favorited : false
     * biz_feature : 4294967300
     * in_reply_to_user_id :
     * annotations : [{"place":{"poiid":"8008631011200000009","title":"莘庄","lon":121.387329,"type":"checkin","lat":31.10248},"client_mblogid":"iPhone-A4B4BD86-03F7-410A-9816-CC3C3C1C8C63"},{"mapi_request":true}]
     * mid : 3970368925020590
     */

    private List<StatusesBean> statuses;

    public int getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(int previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public int getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(int next_cursor) {
        this.next_cursor = next_cursor;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public List<StatusesBean> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<StatusesBean> statuses) {
        this.statuses = statuses;
    }

    public static class StatusesBean {
        /**
         * type : 0
         * list_id : 0
         */
        private UserLine.StatusesBean retweeted_status;

        public UserLine.StatusesBean getRetweeted_status() {
            return retweeted_status;
        }

        public void setRetweeted_status(UserLine.StatusesBean retweeted_status) {
            this.retweeted_status = retweeted_status;
        }

        private User user;
        private VisibleBean visible;
        private int textLength;
        private int attitudes_count;
        private boolean isLongText;
        private String in_reply_to_screen_name;
        private int mlevel;
        private int source_type;
        private boolean truncated;
        private int userType;
        private String thumbnail_pic;
        private long id;
        private String idstr;
        private String original_pic;
        private String in_reply_to_status_id;
        private int reposts_count;
        private String created_at;
        private int comments_count;
        private String text;
        /**
         * type : Point
         * coordinates : [31.10248,121.387329]
         */

        private GeoBean geo;
        private int source_allowclick;
        private String bmiddle_pic;
        private String source;
        private boolean favorited;
        private long biz_feature;
        private String in_reply_to_user_id;
        private String mid;
        /**
         * thumbnail_pic : http://ww2.sinaimg.cn/thumbnail/9d84ac05gw1f3fznof1dhj20qo0zktgs.jpg
         */

        private List<PicUrlsBean> pic_urls;
        private List<?> darwin_tags;
        private List<Integer> biz_ids;
        private List<?> text_tag_tips;
        private List<?> hot_weibo_tags;
        /**
         * place : {"poiid":"8008631011200000009","title":"莘庄","lon":121.387329,"type":"checkin","lat":31.10248}
         * client_mblogid : iPhone-A4B4BD86-03F7-410A-9816-CC3C3C1C8C63
         */
        public User getUser() {
            return user;
        }
        public void setUser(User user) {
            this.user = user;
        }
        private List<AnnotationsBean> annotations;

        public VisibleBean getVisible() {
            return visible;
        }

        public void setVisible(VisibleBean visible) {
            this.visible = visible;
        }

        public int getTextLength() {
            return textLength;
        }

        public void setTextLength(int textLength) {
            this.textLength = textLength;
        }

        public int getAttitudes_count() {
            return attitudes_count;
        }

        public void setAttitudes_count(int attitudes_count) {
            this.attitudes_count = attitudes_count;
        }

        public boolean isIsLongText() {
            return isLongText;
        }

        public void setIsLongText(boolean isLongText) {
            this.isLongText = isLongText;
        }

        public String getIn_reply_to_screen_name() {
            return in_reply_to_screen_name;
        }

        public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
            this.in_reply_to_screen_name = in_reply_to_screen_name;
        }

        public int getMlevel() {
            return mlevel;
        }

        public void setMlevel(int mlevel) {
            this.mlevel = mlevel;
        }

        public int getSource_type() {
            return source_type;
        }

        public void setSource_type(int source_type) {
            this.source_type = source_type;
        }

        public boolean isTruncated() {
            return truncated;
        }

        public void setTruncated(boolean truncated) {
            this.truncated = truncated;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getThumbnail_pic() {
            return thumbnail_pic;
        }

        public void setThumbnail_pic(String thumbnail_pic) {
            this.thumbnail_pic = thumbnail_pic;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getIdstr() {
            return idstr;
        }

        public void setIdstr(String idstr) {
            this.idstr = idstr;
        }

        public String getOriginal_pic() {
            return original_pic;
        }

        public void setOriginal_pic(String original_pic) {
            this.original_pic = original_pic;
        }

        public String getIn_reply_to_status_id() {
            return in_reply_to_status_id;
        }

        public void setIn_reply_to_status_id(String in_reply_to_status_id) {
            this.in_reply_to_status_id = in_reply_to_status_id;
        }

        public int getReposts_count() {
            return reposts_count;
        }

        public void setReposts_count(int reposts_count) {
            this.reposts_count = reposts_count;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getComments_count() {
            return comments_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public GeoBean getGeo() {
            return geo;
        }

        public void setGeo(GeoBean geo) {
            this.geo = geo;
        }

        public int getSource_allowclick() {
            return source_allowclick;
        }

        public void setSource_allowclick(int source_allowclick) {
            this.source_allowclick = source_allowclick;
        }

        public String getBmiddle_pic() {
            return bmiddle_pic;
        }

        public void setBmiddle_pic(String bmiddle_pic) {
            this.bmiddle_pic = bmiddle_pic;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public boolean isFavorited() {
            return favorited;
        }

        public void setFavorited(boolean favorited) {
            this.favorited = favorited;
        }

        public long getBiz_feature() {
            return biz_feature;
        }

        public void setBiz_feature(long biz_feature) {
            this.biz_feature = biz_feature;
        }

        public String getIn_reply_to_user_id() {
            return in_reply_to_user_id;
        }

        public void setIn_reply_to_user_id(String in_reply_to_user_id) {
            this.in_reply_to_user_id = in_reply_to_user_id;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public List<PicUrlsBean> getPic_urls() {
            return pic_urls;
        }

        public void setPic_urls(List<PicUrlsBean> pic_urls) {
            this.pic_urls = pic_urls;
        }

        public List<?> getDarwin_tags() {
            return darwin_tags;
        }

        public void setDarwin_tags(List<?> darwin_tags) {
            this.darwin_tags = darwin_tags;
        }

        public List<Integer> getBiz_ids() {
            return biz_ids;
        }

        public void setBiz_ids(List<Integer> biz_ids) {
            this.biz_ids = biz_ids;
        }

        public List<?> getText_tag_tips() {
            return text_tag_tips;
        }

        public void setText_tag_tips(List<?> text_tag_tips) {
            this.text_tag_tips = text_tag_tips;
        }

        public List<?> getHot_weibo_tags() {
            return hot_weibo_tags;
        }

        public void setHot_weibo_tags(List<?> hot_weibo_tags) {
            this.hot_weibo_tags = hot_weibo_tags;
        }

        public List<AnnotationsBean> getAnnotations() {
            return annotations;
        }

        public void setAnnotations(List<AnnotationsBean> annotations) {
            this.annotations = annotations;
        }

        public static class VisibleBean {
            private int type;
            private int list_id;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getList_id() {
                return list_id;
            }

            public void setList_id(int list_id) {
                this.list_id = list_id;
            }
        }

        public static class GeoBean {
            private String type;
            private List<Double> coordinates;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Double> getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(List<Double> coordinates) {
                this.coordinates = coordinates;
            }
        }

        public static class PicUrlsBean {
            private String thumbnail_pic;

            public String getThumbnail_pic() {
                return thumbnail_pic;
            }

            public void setThumbnail_pic(String thumbnail_pic) {
                this.thumbnail_pic = thumbnail_pic;
            }
        }

        public static class AnnotationsBean {
            /**
             * poiid : 8008631011200000009
             * title : 莘庄
             * lon : 121.387329
             * type : checkin
             * lat : 31.10248
             */

            private PlaceBean place;
            private String client_mblogid;

            public PlaceBean getPlace() {
                return place;
            }

            public void setPlace(PlaceBean place) {
                this.place = place;
            }

            public String getClient_mblogid() {
                return client_mblogid;
            }

            public void setClient_mblogid(String client_mblogid) {
                this.client_mblogid = client_mblogid;
            }

            public static class PlaceBean {
                private String poiid;
                private String title;
                private double lon;
                private String type;
                private double lat;

                public String getPoiid() {
                    return poiid;
                }

                public void setPoiid(String poiid) {
                    this.poiid = poiid;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public double getLon() {
                    return lon;
                }

                public void setLon(double lon) {
                    this.lon = lon;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }
            }
        }
    }
}
