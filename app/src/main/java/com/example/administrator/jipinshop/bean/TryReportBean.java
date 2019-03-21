package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/21
 * @Describe
 */
public class TryReportBean {
    /**
     * msg : success
     * code : 0
     * data : [{"articleId":"ee96a97a21c54215b360bb5997d48f77","userId":"d02400b817d0408b84e53e20c4d42231","user":{"userId":"d02400b817d0408b84e53e20c4d42231","nickname":"豪迈","gender":"女","avatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","authentication":0,"fansCount":null,"follow":null},"goodsId":"9e187764d29d419296c5f77028732d4b","img":"http://jipincheng.cn/5674666419804b958c87e39fe18f08e0-findgoods_list_img","title":"牙齿变色，原来是这样","content":"<p>牙齿的颜色发生变化也不容小觑，<\/p><p>下面给大家介绍一下牙齿变色的原因。<\/p><p><img src=\"http://jipincheng.cn/a07fb2bbdf2c456bba483ea11196cc87\" alt=\"\" /><\/p><p>病理因素<\/p><p>牙髓坏死引起的牙变色，如牙齿受外伤引起牙髓出血、坏死、牙齿就会变为暗红色、褐色或青灰色。病理性牙齿变色，最好的方式就是安装假牙，因为这个时候的牙齿已经没有多少保留的必要了，尤其是伤及到牙髓的牙齿，更加需要注意。<\/p><p><img src=\"http://jipincheng.cn/6ab00673951043d0baf864d2e396533c\" alt=\"\" /><\/p><p>营养因素<\/p><p>牙齿在发育过程中由于营养不好或生过病，会使牙齿的钙化受到影响，牙齿会黄而松脆。这种原因造成的牙齿变色问题，相对来说药物美白实现的可能性不是很大。所以通常会采用牙贴面的方式进行牙齿的美白修复，如果想要让牙齿显得更加洁白亮丽，可以考虑使用美容冠或者全瓷牙等技术。<\/p><p><img src=\"http://jipincheng.cn/d6a53b9db78a49dfba281b9942429874\" alt=\"\" /><\/p><p>水质因素<\/p><p>有些地区，特别是有些山区，由于水中含氟量高，过多的饮用氟牙齿会发黄，而且不是1-2个牙齿，是满口大黄牙，这种牙齿被称之为氟斑牙。氟有防龋的作用，所以对于氟斑牙患者来说一般是不容易蛀牙的。不过氟斑牙缺失会影响我们的个人形象，需要及时的进行牙齿美白。因为这种牙齿染色是牙釉质染色，所以想要进行美白的话，最好是使用美容冠，不过对于一些中轻度的氟斑牙，建议采用纳米蓝光美白等方式进行牙齿的美白工作。<\/p><p><img src=\"http://jipincheng.cn/b256e5284e6f4a16b9e246acca7ea471\" alt=\"\" /><\/p><p>药物因素<\/p><p>现在大都数人都知道了四环素类药物可导致黄牙的发生，特别是在儿童(5岁前)服用影响更大。不过现在已经很少会有这种牙齿病症出现了，主要是因为目前的医院已经很少会使用四环素药物。对于儿童和孕妇更是严令禁止这些药物的使用。<\/p><p><br/><\/p><p>外在色素堆积<\/p><p>通常是因为没有养成良好的刷牙习惯，其次则是因为长期的吸烟或者饮茶、咖啡等造成的牙齿变色，通常这种原因导致牙齿变黄相对较为容易解决。对于不是很严重的外源性牙齿染色，可以使用洁牙或者纳米蓝光美白的方式解决。这里需要提醒一下，在进行过牙齿美白后，最好是能够减少吸烟或者饮茶的频率，这样才能够有效的保证牙齿美白后的效果。<\/p><p><br/><\/p>","contentType":1,"type":null,"size":null,"pv":20,"shareTitle":null,"shareContent":null,"shareImg":null,"shareUrl":null,"createTime":"2018.12.11","createTimeStr":"2018-12-11 16:19","vote":null,"voteCount":null,"collect":null,"relatedArticleList":null,"relatedGoodsList":null}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * articleId : ee96a97a21c54215b360bb5997d48f77
         * userId : d02400b817d0408b84e53e20c4d42231
         * user : {"userId":"d02400b817d0408b84e53e20c4d42231","nickname":"豪迈","gender":"女","avatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","authentication":0,"fansCount":null,"follow":null}
         * goodsId : 9e187764d29d419296c5f77028732d4b
         * img : http://jipincheng.cn/5674666419804b958c87e39fe18f08e0-findgoods_list_img
         * title : 牙齿变色，原来是这样
         * content : <p>牙齿的颜色发生变化也不容小觑，</p><p>下面给大家介绍一下牙齿变色的原因。</p><p><img src="http://jipincheng.cn/a07fb2bbdf2c456bba483ea11196cc87" alt="" /></p><p>病理因素</p><p>牙髓坏死引起的牙变色，如牙齿受外伤引起牙髓出血、坏死、牙齿就会变为暗红色、褐色或青灰色。病理性牙齿变色，最好的方式就是安装假牙，因为这个时候的牙齿已经没有多少保留的必要了，尤其是伤及到牙髓的牙齿，更加需要注意。</p><p><img src="http://jipincheng.cn/6ab00673951043d0baf864d2e396533c" alt="" /></p><p>营养因素</p><p>牙齿在发育过程中由于营养不好或生过病，会使牙齿的钙化受到影响，牙齿会黄而松脆。这种原因造成的牙齿变色问题，相对来说药物美白实现的可能性不是很大。所以通常会采用牙贴面的方式进行牙齿的美白修复，如果想要让牙齿显得更加洁白亮丽，可以考虑使用美容冠或者全瓷牙等技术。</p><p><img src="http://jipincheng.cn/d6a53b9db78a49dfba281b9942429874" alt="" /></p><p>水质因素</p><p>有些地区，特别是有些山区，由于水中含氟量高，过多的饮用氟牙齿会发黄，而且不是1-2个牙齿，是满口大黄牙，这种牙齿被称之为氟斑牙。氟有防龋的作用，所以对于氟斑牙患者来说一般是不容易蛀牙的。不过氟斑牙缺失会影响我们的个人形象，需要及时的进行牙齿美白。因为这种牙齿染色是牙釉质染色，所以想要进行美白的话，最好是使用美容冠，不过对于一些中轻度的氟斑牙，建议采用纳米蓝光美白等方式进行牙齿的美白工作。</p><p><img src="http://jipincheng.cn/b256e5284e6f4a16b9e246acca7ea471" alt="" /></p><p>药物因素</p><p>现在大都数人都知道了四环素类药物可导致黄牙的发生，特别是在儿童(5岁前)服用影响更大。不过现在已经很少会有这种牙齿病症出现了，主要是因为目前的医院已经很少会使用四环素药物。对于儿童和孕妇更是严令禁止这些药物的使用。</p><p><br/></p><p>外在色素堆积</p><p>通常是因为没有养成良好的刷牙习惯，其次则是因为长期的吸烟或者饮茶、咖啡等造成的牙齿变色，通常这种原因导致牙齿变黄相对较为容易解决。对于不是很严重的外源性牙齿染色，可以使用洁牙或者纳米蓝光美白的方式解决。这里需要提醒一下，在进行过牙齿美白后，最好是能够减少吸烟或者饮茶的频率，这样才能够有效的保证牙齿美白后的效果。</p><p><br/></p>
         * contentType : 1
         * type : null
         * size : null
         * pv : 20
         * shareTitle : null
         * shareContent : null
         * shareImg : null
         * shareUrl : null
         * createTime : 2018.12.11
         * createTimeStr : 2018-12-11 16:19
         * vote : null
         * voteCount : null
         * collect : null
         * relatedArticleList : null
         * relatedGoodsList : null
         */

        private String articleId;
        private String userId;
        private UserBean user;
        private String goodsId;
        private String img;
        private String title;
        private String content;
        private int contentType;
        private String type;
        private String size;
        private int pv;
        private String shareTitle;
        private String shareContent;
        private String shareImg;
        private String shareUrl;
        private String createTime;
        private String createTimeStr;
        private String vote;
        private String voteCount;
        private String collect;
        private String relatedArticleList;
        private String relatedGoodsList;

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getPv() {
            return pv;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public String getShareContent() {
            return shareContent;
        }

        public void setShareContent(String shareContent) {
            this.shareContent = shareContent;
        }

        public String getShareImg() {
            return shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public String getVote() {
            return vote;
        }

        public void setVote(String vote) {
            this.vote = vote;
        }

        public String getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(String voteCount) {
            this.voteCount = voteCount;
        }

        public String getCollect() {
            return collect;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }

        public String getRelatedArticleList() {
            return relatedArticleList;
        }

        public void setRelatedArticleList(String relatedArticleList) {
            this.relatedArticleList = relatedArticleList;
        }

        public String getRelatedGoodsList() {
            return relatedGoodsList;
        }

        public void setRelatedGoodsList(String relatedGoodsList) {
            this.relatedGoodsList = relatedGoodsList;
        }

        public static class UserBean {
            /**
             * userId : d02400b817d0408b84e53e20c4d42231
             * nickname : 豪迈
             * gender : 女
             * avatar : http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0
             * authentication : 0
             * fansCount : null
             * follow : null
             */

            private String userId;
            private String nickname;
            private String gender;
            private String avatar;
            private int authentication;
            private String fansCount;
            private String follow;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getAuthentication() {
                return authentication;
            }

            public void setAuthentication(int authentication) {
                this.authentication = authentication;
            }

            public String getFansCount() {
                return fansCount;
            }

            public void setFansCount(String fansCount) {
                this.fansCount = fansCount;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }
        }
    }
}
