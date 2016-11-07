package com.chj.weiboone.weibo.entity;

import java.util.List;

/**
 * 粉丝
 *
 * Created by C on 2016/5/3.
 */
public class Friendships {

    /**
     * previous_cursor : 0
     * total_number : 22
     * next_cursor : 0
     * users : [{"verified_source_url":"","block_app":0,"remark":"","location":"上海","verified_type":-1,"verified_reason":"","statuses_count":1,"lang":"zh-cn","verified_source":"","credit_score":80,"city":"1000","id":5325481189,"verified_trade":"","following":false,"favourites_count":0,"idstr":"5325481189","verified":false,"description":"","name":"不着调的宋喵喵","domain":"","province":"31","created_at":"Sat Nov 15 20:20:47 +0800 2014","gender":"f","user_ability":0,"weihao":"","followers_count":82,"online_status":0,"profile_url":"u/5325481189","bi_followers_count":80,"geo_enabled":true,"star":0,"status_id":3888844535422018,"urank":7,"class":1,"mbrank":0,"avatar_hd":"http://tva4.sinaimg.cn/crop.0.0.200.200.1024/005OpaVTjw1ew7wqk4m7wj305k05k0su.jpg","allow_all_comment":true,"allow_all_act_msg":false,"avatar_large":"http://tp2.sinaimg.cn/5325481189/180/5737623548/0","url":"","pagefriends_count":0,"friends_count":1167,"verified_reason_url":"","mbtype":0,"screen_name":"不着调的宋喵喵","block_word":0,"follow_me":true,"profile_image_url":"http://tp2.sinaimg.cn/5325481189/50/5737623548/0","ptype":0}]
     * display_total_number : 5
     */

    private int previous_cursor;
    private int total_number;
    private int next_cursor;
    private int display_total_number;
    /**
     * verified_source_url :
     * block_app : 0
     * remark :
     * location : 上海
     * verified_type : -1
     * verified_reason :
     * statuses_count : 1
     * lang : zh-cn
     * verified_source :
     * credit_score : 80
     * city : 1000
     * id : 5325481189
     * verified_trade :
     * following : false
     * favourites_count : 0
     * idstr : 5325481189
     * verified : false
     * description :
     * name : 不着调的宋喵喵
     * domain :
     * province : 31
     * created_at : Sat Nov 15 20:20:47 +0800 2014
     * gender : f
     * user_ability : 0
     * weihao :
     * followers_count : 82
     * online_status : 0
     * profile_url : u/5325481189
     * bi_followers_count : 80
     * geo_enabled : true
     * star : 0
     * status_id : 3888844535422018
     * urank : 7
     * class : 1
     * mbrank : 0
     * avatar_hd : http://tva4.sinaimg.cn/crop.0.0.200.200.1024/005OpaVTjw1ew7wqk4m7wj305k05k0su.jpg
     * allow_all_comment : true
     * allow_all_act_msg : false
     * avatar_large : http://tp2.sinaimg.cn/5325481189/180/5737623548/0
     * url :
     * pagefriends_count : 0
     * friends_count : 1167
     * verified_reason_url :
     * mbtype : 0
     * screen_name : 不着调的宋喵喵
     * block_word : 0
     * follow_me : true
     * profile_image_url : http://tp2.sinaimg.cn/5325481189/50/5737623548/0
     * ptype : 0
     */

    private List<UsersBean> users;

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

    public int getDisplay_total_number() {
        return display_total_number;
    }

    public void setDisplay_total_number(int display_total_number) {
        this.display_total_number = display_total_number;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        private String verified_source_url;
        private int block_app;
        private String remark;
        private String location;
        private int verified_type;
        private String verified_reason;
        private int statuses_count;
        private String lang;
        private String verified_source;
        private int credit_score;
        private String city;
        private long id;
        private String verified_trade;
        private boolean following;
        private int favourites_count;
        private String idstr;
        private boolean verified;
        private String description;
        private String name;
        private String domain;
        private String province;
        private String created_at;
        private String gender;
        private int user_ability;
        private String weihao;
        private int followers_count;
        private int online_status;
        private String profile_url;
        private int bi_followers_count;
        private boolean geo_enabled;
        private int star;
        private long status_id;
        private int urank;
        private int classX;
        private int mbrank;
        private String avatar_hd;
        private boolean allow_all_comment;
        private boolean allow_all_act_msg;
        private String avatar_large;
        private String url;
        private int pagefriends_count;
        private int friends_count;
        private String verified_reason_url;
        private int mbtype;
        private String screen_name;
        private int block_word;
        private boolean follow_me;
        private String profile_image_url;
        private int ptype;

        public String getVerified_source_url() {
            return verified_source_url;
        }

        public void setVerified_source_url(String verified_source_url) {
            this.verified_source_url = verified_source_url;
        }

        public int getBlock_app() {
            return block_app;
        }

        public void setBlock_app(int block_app) {
            this.block_app = block_app;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getVerified_type() {
            return verified_type;
        }

        public void setVerified_type(int verified_type) {
            this.verified_type = verified_type;
        }

        public String getVerified_reason() {
            return verified_reason;
        }

        public void setVerified_reason(String verified_reason) {
            this.verified_reason = verified_reason;
        }

        public int getStatuses_count() {
            return statuses_count;
        }

        public void setStatuses_count(int statuses_count) {
            this.statuses_count = statuses_count;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getVerified_source() {
            return verified_source;
        }

        public void setVerified_source(String verified_source) {
            this.verified_source = verified_source;
        }

        public int getCredit_score() {
            return credit_score;
        }

        public void setCredit_score(int credit_score) {
            this.credit_score = credit_score;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getVerified_trade() {
            return verified_trade;
        }

        public void setVerified_trade(String verified_trade) {
            this.verified_trade = verified_trade;
        }

        public boolean isFollowing() {
            return following;
        }

        public void setFollowing(boolean following) {
            this.following = following;
        }

        public int getFavourites_count() {
            return favourites_count;
        }

        public void setFavourites_count(int favourites_count) {
            this.favourites_count = favourites_count;
        }

        public String getIdstr() {
            return idstr;
        }

        public void setIdstr(String idstr) {
            this.idstr = idstr;
        }

        public boolean isVerified() {
            return verified;
        }

        public void setVerified(boolean verified) {
            this.verified = verified;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getUser_ability() {
            return user_ability;
        }

        public void setUser_ability(int user_ability) {
            this.user_ability = user_ability;
        }

        public String getWeihao() {
            return weihao;
        }

        public void setWeihao(String weihao) {
            this.weihao = weihao;
        }

        public int getFollowers_count() {
            return followers_count;
        }

        public void setFollowers_count(int followers_count) {
            this.followers_count = followers_count;
        }

        public int getOnline_status() {
            return online_status;
        }

        public void setOnline_status(int online_status) {
            this.online_status = online_status;
        }

        public String getProfile_url() {
            return profile_url;
        }

        public void setProfile_url(String profile_url) {
            this.profile_url = profile_url;
        }

        public int getBi_followers_count() {
            return bi_followers_count;
        }

        public void setBi_followers_count(int bi_followers_count) {
            this.bi_followers_count = bi_followers_count;
        }

        public boolean isGeo_enabled() {
            return geo_enabled;
        }

        public void setGeo_enabled(boolean geo_enabled) {
            this.geo_enabled = geo_enabled;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public long getStatus_id() {
            return status_id;
        }

        public void setStatus_id(long status_id) {
            this.status_id = status_id;
        }

        public int getUrank() {
            return urank;
        }

        public void setUrank(int urank) {
            this.urank = urank;
        }

        public int getClassX() {
            return classX;
        }

        public void setClassX(int classX) {
            this.classX = classX;
        }

        public int getMbrank() {
            return mbrank;
        }

        public void setMbrank(int mbrank) {
            this.mbrank = mbrank;
        }

        public String getAvatar_hd() {
            return avatar_hd;
        }

        public void setAvatar_hd(String avatar_hd) {
            this.avatar_hd = avatar_hd;
        }

        public boolean isAllow_all_comment() {
            return allow_all_comment;
        }

        public void setAllow_all_comment(boolean allow_all_comment) {
            this.allow_all_comment = allow_all_comment;
        }

        public boolean isAllow_all_act_msg() {
            return allow_all_act_msg;
        }

        public void setAllow_all_act_msg(boolean allow_all_act_msg) {
            this.allow_all_act_msg = allow_all_act_msg;
        }

        public String getAvatar_large() {
            return avatar_large;
        }

        public void setAvatar_large(String avatar_large) {
            this.avatar_large = avatar_large;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPagefriends_count() {
            return pagefriends_count;
        }

        public void setPagefriends_count(int pagefriends_count) {
            this.pagefriends_count = pagefriends_count;
        }

        public int getFriends_count() {
            return friends_count;
        }

        public void setFriends_count(int friends_count) {
            this.friends_count = friends_count;
        }

        public String getVerified_reason_url() {
            return verified_reason_url;
        }

        public void setVerified_reason_url(String verified_reason_url) {
            this.verified_reason_url = verified_reason_url;
        }

        public int getMbtype() {
            return mbtype;
        }

        public void setMbtype(int mbtype) {
            this.mbtype = mbtype;
        }

        public String getScreen_name() {
            return screen_name;
        }

        public void setScreen_name(String screen_name) {
            this.screen_name = screen_name;
        }

        public int getBlock_word() {
            return block_word;
        }

        public void setBlock_word(int block_word) {
            this.block_word = block_word;
        }

        public boolean isFollow_me() {
            return follow_me;
        }

        public void setFollow_me(boolean follow_me) {
            this.follow_me = follow_me;
        }

        public String getProfile_image_url() {
            return profile_image_url;
        }

        public void setProfile_image_url(String profile_image_url) {
            this.profile_image_url = profile_image_url;
        }

        public int getPtype() {
            return ptype;
        }

        public void setPtype(int ptype) {
            this.ptype = ptype;
        }
    }
}
