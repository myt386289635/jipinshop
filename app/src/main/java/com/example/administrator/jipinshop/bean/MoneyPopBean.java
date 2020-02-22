package com.example.administrator.jipinshop.bean;

import java.math.BigDecimal;

/**
 * @author 莫小婷
 * @create 2020/2/22
 * @Describe
 */
public class MoneyPopBean {

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * type : 4
         * popId : 38db83f6be384e37bf8cea4afd8e9282
         * addHongbao : 3
         */

        private int type;
        private String popId;
        private String addHongbao;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPopId() {
            return popId;
        }

        public void setPopId(String popId) {
            this.popId = popId;
        }

        public String getAddHongbao() {
            return new BigDecimal(addHongbao).stripTrailingZeros().toPlainString();
        }

        public void setAddHongbao(String addHongbao) {
            this.addHongbao = addHongbao;
        }
    }
}
