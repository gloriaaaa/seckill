package com.seckill.result;

public class CodeMsg {
    private int code;
    private String Msg;
    //通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
    //登录模块 5002xx

    //商品模块 5003xx

    //订单模块 5004xx

    //秒杀模块 5004xx

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.Msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
