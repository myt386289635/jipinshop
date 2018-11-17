package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/17
 * @Describe 个人主页
 */
public class UserPageBean {

    /**
     * msg : success
     * code : 200
     * list : [{"articleId":"6e7df44a0f344fa89e1a09a578a68e80","img":"http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a","visitCount":"54","title":"主标题","smallTitle":"副标题","publishTime":"2018-11-17 15:00:06","showTime":null,"commentNum":null},{"articleId":"1434775b509b431e8b0155e3e3f3210b11","img":"http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a","visitCount":"223","title":"主标题","smallTitle":"副标题","publishTime":"2018-11-17 15:00:06","showTime":null,"commentNum":null},{"articleId":"8a210c14f2004b47a1113959cb2b1843","img":"http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a","visitCount":"544","title":"主标题","smallTitle":"副标题","publishTime":"2018-11-17 15:00:06","showTime":null,"commentNum":null},{"articleId":"66344026-f60b-4c24-a63c-f815ac3a9d98","img":"http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a","visitCount":"33","title":"主标题","smallTitle":"副标题","publishTime":"2018-11-17 15:00:06","showTime":null,"commentNum":null},{"articleId":"1434775b509b431e8b0155e3e3f3210b","img":"http://pi6611u5d.bkt.clouddn.com/293b98cdfbc9437caf936ae48884a4c9","visitCount":"11","title":"主标题","smallTitle":"副标题","publishTime":"2018-11-17 15:00:06","showTime":null,"commentNum":null},{"articleId":"2a343b92c9ae4bc5b01a16ca48b0df2b","img":"http://pi6611u5d.bkt.clouddn.com/d61bf54391b64b289e72208654168bd1","visitCount":"232","title":"梵蒂冈给对方代发电饭锅地方金刚骷髅岛发价格扣了多少梵蒂冈给对方代发电饭锅地方金刚骷髅岛发价格扣了多少","smallTitle":"0","publishTime":"2018-11-17 11:54:21","showTime":"6小时前","commentNum":5},{"articleId":"cc898e31a8ca45239d42f261d222046e","img":"http://pi6611u5d.bkt.clouddn.com/d61bf54391b64b289e72208654168bd1","visitCount":"55","title":"看啊看看啊看看倒计时看风景的空间管控 发的鬼地方过水电费跟得上梵蒂冈给对方代发电饭锅地方金刚骷髅岛发价格扣了多少","smallTitle":"0","publishTime":"2018-11-16 11:54:27","showTime":"","commentNum":1}]
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
         * articleId : 6e7df44a0f344fa89e1a09a578a68e80
         * img : http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a
         * visitCount : 54
         * title : 主标题
         * smallTitle : 副标题
         * publishTime : 2018-11-17 15:00:06
         * showTime : null
         * commentNum : null
         */

        private String articleId;
        private String img;
        private String visitCount;
        private String title;
        private String smallTitle;
        private String publishTime;
        private String showTime;
        private String commentNum;

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getVisitCount() {
            return visitCount;
        }

        public void setVisitCount(String visitCount) {
            this.visitCount = visitCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSmallTitle() {
            return smallTitle;
        }

        public void setSmallTitle(String smallTitle) {
            this.smallTitle = smallTitle;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }
    }
}
