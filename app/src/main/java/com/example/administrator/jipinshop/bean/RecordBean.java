package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public class RecordBean {

    /**
     * msg : success
     * code : 200
     * list : [{"id":"4b492e4fd8074efd950abe7f91af20dd","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","pay_name":"极品城","out_biz_no":"1537173961245","order_id":"20181011110070001502700040870608","accountTime":"2018-10-11 21:32:04.0","amount":"0.3"},{"id":"a2a374472da546209a7161abc2a3f7ee","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","pay_name":"极品城","out_biz_no":"1537636071907","order_id":"20181011110070001502700040920870","accountTime":"2018-10-11 21:31:54.0","amount":"0.24"},{"id":"a268f55aa9fb4997a9bafdf2aa95d654","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","pay_name":"极品城","out_biz_no":"1538934465383","order_id":"20181011110070001502700040879737","accountTime":"2018-10-11 21:31:19.0","amount":"1.0"}]
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
         * id : 4b492e4fd8074efd950abe7f91af20dd
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * pay_name : 极品城
         * out_biz_no : 1537173961245
         * order_id : 20181011110070001502700040870608
         * accountTime : 2018-10-11 21:32:04.0
         * amount : 0.3
         */

        private String id;
        private String userId;
        private String pay_name;
        private String out_biz_no;
        private String order_id;
        private String accountTime;
        private String amount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public String getOut_biz_no() {
            return out_biz_no;
        }

        public void setOut_biz_no(String out_biz_no) {
            this.out_biz_no = out_biz_no;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getAccountTime() {
            return accountTime;
        }

        public void setAccountTime(String accountTime) {
            this.accountTime = accountTime;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
