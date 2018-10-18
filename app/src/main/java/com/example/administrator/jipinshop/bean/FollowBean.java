package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/10
 * @Describe
 */
public class FollowBean {

    /**
     * msg : success
     * code : 200
     * list : [{"concernId":"12","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"55","isConcern":55,"concernCount":55,"create_time":"2018-11-01T03:03:23.000+0000","from_thumb_img":"112","from_nickname":"22"},{"concernId":"1776","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"777","isConcern":777,"concernCount":777,"create_time":"2018-09-27T02:57:02.000+0000","from_thumb_img":"7777","from_nickname":"7777"},{"concernId":"12341234","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"12341234","isConcern":12341234,"concernCount":12341234,"create_time":"2018-09-27T02:45:31.000+0000","from_thumb_img":"12341234","from_nickname":"12341234"},{"concernId":"1236263","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"123123123","isConcern":1231231231,"concernCount":123123123,"create_time":"2018-09-27T02:42:10.000+0000","from_thumb_img":"123123","from_nickname":"1231231"},{"concernId":"3332","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"332","isConcern":332,"concernCount":332,"create_time":"2018-09-27T02:41:10.000+0000","from_thumb_img":"332","from_nickname":"552"},{"concernId":"17727","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"777","isConcern":777,"concernCount":777,"create_time":"2018-09-27T02:36:07.000+0000","from_thumb_img":"7777","from_nickname":"7777"},{"concernId":"222","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"222","isConcern":322,"concernCount":422,"create_time":"2018-09-27T02:29:34.000+0000","from_thumb_img":"622","from_nickname":"722"},{"concernId":"333","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"33","isConcern":33,"concernCount":33,"create_time":"2018-09-27T02:24:25.000+0000","from_thumb_img":"33","from_nickname":"55"},{"concernId":"3","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"3","isConcern":3,"concernCount":3,"create_time":"2018-09-26T09:41:22.000+0000","from_thumb_img":null,"from_nickname":null},{"concernId":"55","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"55","isConcern":55,"concernCount":55,"create_time":"2018-09-26T07:13:00.000+0000","from_thumb_img":null,"from_nickname":null},{"concernId":"444","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"44","isConcern":44,"concernCount":44,"create_time":"2018-09-26T07:11:17.000+0000","from_thumb_img":null,"from_nickname":null},{"concernId":"1244","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"33","isConcern":33,"concernCount":33,"create_time":"2018-09-26T06:49:42.000+0000","from_thumb_img":null,"from_nickname":null},{"concernId":"1","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"2","isConcern":2,"concernCount":2,"create_time":"2018-09-26T05:08:22.000+0000","from_thumb_img":null,"from_nickname":null},{"concernId":"124","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"3","isConcern":33,"concernCount":33,"create_time":null,"from_thumb_img":null,"from_nickname":null}]
     */

    private String msg;
    private int code;
    private List<ListBean> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * concernId : 12
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * attentionUserId : 55
         * isConcern : 55
         * concernCount : 55
         * create_time : 2018-11-01T03:03:23.000+0000
         * from_thumb_img : 112
         * from_nickname : 22
         */

        private String concernId;
        private String userId;
        private String attentionUserId;
        private int isConcern;
        private int concernCount;
        private String create_time;
        private String from_thumb_img;
        private String from_nickname;

        public String getConcernId() {
            return concernId;
        }

        public void setConcernId(String concernId) {
            this.concernId = concernId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAttentionUserId() {
            return attentionUserId;
        }

        public void setAttentionUserId(String attentionUserId) {
            this.attentionUserId = attentionUserId;
        }

        public int getIsConcern() {
            return isConcern;
        }

        public void setIsConcern(int isConcern) {
            this.isConcern = isConcern;
        }

        public int getConcernCount() {
            return concernCount;
        }

        public void setConcernCount(int concernCount) {
            this.concernCount = concernCount;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getFrom_thumb_img() {
            return from_thumb_img;
        }

        public void setFrom_thumb_img(String from_thumb_img) {
            this.from_thumb_img = from_thumb_img;
        }

        public String getFrom_nickname() {
            return from_nickname;
        }

        public void setFrom_nickname(String from_nickname) {
            this.from_nickname = from_nickname;
        }
    }
}
