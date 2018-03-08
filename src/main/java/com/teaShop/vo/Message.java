package com.teaShop.vo;

import com.teaShop.utils.Const;

public class Message {
    private int flag = 0;
    private String message = Const.NORMAL;
    private Object data;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object object) {
        this.data = object;
    }

    public String toString() {
        return "flag:" + this.flag + "   message:" + this.message;
    }
}
