package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/26
 * @Describe 公共类
 */
public class SucBean<T> {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"eed32b0aae6f449f81e99cef113366d5","trialId":"9e187764d29d419296c5f77028732d4b","userId":"2","voteCount":51451,"userNickname":"编辑","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":2,"remark":null},{"id":"2301b19320924ef9aa819b9d0f94069c","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":5949,"userNickname":"测试21号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":1,"remark":null},{"id":"c8b5a286d4dc426ebca5dbe0047a3aa1","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":5415,"userNickname":"测试9号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":1,"remark":null},{"id":"ea17502ff2df44049a305270ff9338c8","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":415,"userNickname":"测试5号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":1,"remark":null},{"id":"7a603d8ac09c4b0bb9cc4f0a7d162c02","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":146,"userNickname":"测试14号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":1,"remark":null},{"id":"62f544600fbb4466a1c5a9f1edd7bab0","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":122,"userNickname":"测试16号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":2,"remark":null},{"id":"1","trialId":"9e187764d29d419296c5f77028732d4b","userId":"1","voteCount":100,"userNickname":"1","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":-1,"remark":null},{"id":"0dd3c4aa1ccf488c8c332e5bc75a31ec","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":100,"userNickname":"测试22号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":1,"remark":null},{"id":"797b61353c4149cbbf604d1fd18133ee","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":23,"userNickname":"测试15号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":-1,"remark":null},{"id":"5185ccbc4d0647f4843cda3f8bdf1e7a","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":0,"userNickname":"测试18号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":3,"remark":null}]
     */

    private String msg;
    private int code;
    private List<T> data;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
