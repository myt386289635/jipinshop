package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/11/10
 * @Describe 添加评论
 */
public class CommentInsertBean {

    /**
     * msg : 添加成功
     * code : 200
     * commentId : f1e7d0ba945343fa8851fddfa0fd311c
     */

    private String msg;
    private int code;
    private String commentId;

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

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
