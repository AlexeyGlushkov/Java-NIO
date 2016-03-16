package com.viliric.JSONResults;

/**
 * Created by Lenovo on 15.03.2016.
 */
public class Request {
    private boolean result = false;

    public Responses.Response getResponceStatus() {
        return responceStatus;
    }

    private Responses.Response responceStatus;

    public void setResponceStatus(Responses.Response responceStatus) {
        this.responceStatus = responceStatus;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
