package org.leesia.concurrent.vo;

@Deprecated
public class Result {

    private String result;

    private int num;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "{result: " + result + ", num: " + num + "}";
    }
}
