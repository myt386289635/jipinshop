package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/11/17
 * @Describe
 */
public class FindDetailBean {

    /**
     * msg : success
     * code : 200
     * GoodsFindGoods : {"findgoodsId":"8a210c14f2004b47a1113959cb2b1843","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"主标题","smallTitle":"副标题","imgId":"http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a","publishTime":"2018-11-17 15:00:06","showTime":null,"visitCount":"544","commentNum":null,"content":"<!DOCTYPE html><html><head><meta charset='utf-8' /><style type=\"text/css\"> img{ width:100% } <\/style><\/head><body><script language=\"JavaScript\">function buy(id){window.location.href='http://qualityshop?url='+id}<\/script>\n<p><br /><\/p><p><strong><span style=\"font-size:19px;\"><\/span><span style=\"font-size:19px;\"> 布景秋装 翻领民族绣花中长款衬衫宽松棉打底衬衣<\/span><\/strong><\/p><p><img src=\"http://img.alicdn.com/bao/uploaded/i1/2455486615/TB1XMhyagsSMeJjSspdXXXZ4pXa_!!0-item_pic.jpg\" alt=\"\" title=\"\" width=\"391\" height=\"391\" align=\"\" /><\/p><div class=\"meta-tags\" style=\"display:inline-block;line-height:12px;margin-right:10px;background:#f5f5f5;margin-bottom:10px;border-radius:20px;color:#666;overflow:visible;border:1px solid #ddd;\"><a style=\"text-decoration:none;color:#666;padding:5px 10px;display:inline-block;\">成人<\/a> <\/div><div class=\"meta-tags\" style=\"display:inline-block;line-height:12px;margin-right:10px;background:#f5f5f5;margin-bottom:10px;border-radius:20px;color:#666;overflow:visible;border:1px solid #ddd;\"><a style=\"text-decoration:none;color:#666;padding:5px 10px;display:inline-block;\">小资<\/a> <\/div><div class=\"goods-price es-style-price\"><span style=\"background-color:#FFFFFF;color:#E53333;\"><strong>券后价：￥<\/strong><\/span><span style=\"background-color:#FFFFFF;color:#E53333;\"><strong>179<\/strong><\/span><span style=\"font-size:0.34rem;\"><span style=\"background-color:#FFFFFF;color:#E53333;\"><\/span> <\/span><span style=\"color:#999CA7;\"><span class=\"label label-success\">淘宝<\/span>：￥199<span style=\"background-color:#E53333;\"><span style=\"background-color:#000000;\"><span style=\"background-color:#FFFFFF;\"><\/span><\/span><\/span><\/span><span onclick=\"buy(&quot;https://uland.taobao.com/coupon/edetail?e=ITyMJEulhvoN%2BoQUE6FNzL9LZuZAJrjlhJAq7F1uY%2FXVhkcIv6TcbyCBjFWwUyoK5nwMTMh13cEtoRgTxpIU1oPpxSZZduT22IaVVct%2FH6e%2FbDB7vEbF3Q%3D%3D&af=1&pid=mm_187680147_154700100_44963000223&quot;);\" class=\"card-btn-deep res_smzdm\" style=\"background-color:#f04848;color:#fff;float:right;width:68px;height:28px;margin-left:10px;border-radius:3px;line-height:28px;text-align:center;font-size:12px;\">去购买<\/span><\/div><\/body><\/html>","createTime":"2018-11-17 15:00:06","categoryId":"06d488224d5a4850a8c652a24eb5ef38","status":1,"concernNum":null,"userShopmember":null}
     */

    private String msg;
    private int code;
    private GoodsFindGoodsBean GoodsFindGoods;

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

    public GoodsFindGoodsBean getGoodsFindGoods() {
        return GoodsFindGoods;
    }

    public void setGoodsFindGoods(GoodsFindGoodsBean GoodsFindGoods) {
        this.GoodsFindGoods = GoodsFindGoods;
    }

    public static class GoodsFindGoodsBean {
        /**
         * findgoodsId : 8a210c14f2004b47a1113959cb2b1843
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * title : 主标题
         * smallTitle : 副标题
         * imgId : http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a
         * publishTime : 2018-11-17 15:00:06
         * showTime : null
         * visitCount : 544
         * commentNum : null
         * content : <!DOCTYPE html><html><head><meta charset='utf-8' /><style type="text/css"> img{ width:100% } </style></head><body><script language="JavaScript">function buy(id){window.location.href='http://qualityshop?url='+id}</script>
         <p><br /></p><p><strong><span style="font-size:19px;"></span><span style="font-size:19px;"> 布景秋装 翻领民族绣花中长款衬衫宽松棉打底衬衣</span></strong></p><p><img src="http://img.alicdn.com/bao/uploaded/i1/2455486615/TB1XMhyagsSMeJjSspdXXXZ4pXa_!!0-item_pic.jpg" alt="" title="" width="391" height="391" align="" /></p><div class="meta-tags" style="display:inline-block;line-height:12px;margin-right:10px;background:#f5f5f5;margin-bottom:10px;border-radius:20px;color:#666;overflow:visible;border:1px solid #ddd;"><a style="text-decoration:none;color:#666;padding:5px 10px;display:inline-block;">成人</a> </div><div class="meta-tags" style="display:inline-block;line-height:12px;margin-right:10px;background:#f5f5f5;margin-bottom:10px;border-radius:20px;color:#666;overflow:visible;border:1px solid #ddd;"><a style="text-decoration:none;color:#666;padding:5px 10px;display:inline-block;">小资</a> </div><div class="goods-price es-style-price"><span style="background-color:#FFFFFF;color:#E53333;"><strong>券后价：￥</strong></span><span style="background-color:#FFFFFF;color:#E53333;"><strong>179</strong></span><span style="font-size:0.34rem;"><span style="background-color:#FFFFFF;color:#E53333;"></span> </span><span style="color:#999CA7;"><span class="label label-success">淘宝</span>：￥199<span style="background-color:#E53333;"><span style="background-color:#000000;"><span style="background-color:#FFFFFF;"></span></span></span></span><span onclick="buy(&quot;https://uland.taobao.com/coupon/edetail?e=ITyMJEulhvoN%2BoQUE6FNzL9LZuZAJrjlhJAq7F1uY%2FXVhkcIv6TcbyCBjFWwUyoK5nwMTMh13cEtoRgTxpIU1oPpxSZZduT22IaVVct%2FH6e%2FbDB7vEbF3Q%3D%3D&af=1&pid=mm_187680147_154700100_44963000223&quot;);" class="card-btn-deep res_smzdm" style="background-color:#f04848;color:#fff;float:right;width:68px;height:28px;margin-left:10px;border-radius:3px;line-height:28px;text-align:center;font-size:12px;">去购买</span></div></body></html>
         * createTime : 2018-11-17 15:00:06
         * categoryId : 06d488224d5a4850a8c652a24eb5ef38
         * status : 1
         * concernNum : null
         * userShopmember : null
         */

        private String findgoodsId;
        private String userId;
        private String title;
        private String smallTitle;
        private String imgId;
        private String publishTime;
        private Object showTime;
        private String visitCount;
        private Object commentNum;
        private String content;
        private String createTime;
        private String categoryId;
        private int status;
        private Object concernNum;
        private Object userShopmember;

        public String getFindgoodsId() {
            return findgoodsId;
        }

        public void setFindgoodsId(String findgoodsId) {
            this.findgoodsId = findgoodsId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public Object getShowTime() {
            return showTime;
        }

        public void setShowTime(Object showTime) {
            this.showTime = showTime;
        }

        public String getVisitCount() {
            return visitCount;
        }

        public void setVisitCount(String visitCount) {
            this.visitCount = visitCount;
        }

        public Object getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(Object commentNum) {
            this.commentNum = commentNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getConcernNum() {
            return concernNum;
        }

        public void setConcernNum(Object concernNum) {
            this.concernNum = concernNum;
        }

        public Object getUserShopmember() {
            return userShopmember;
        }

        public void setUserShopmember(Object userShopmember) {
            this.userShopmember = userShopmember;
        }
    }
}
