package com.viliric.JSONResults;

/**
 * Created by Lenovo on 16.03.2016.
 */
public class Responses {
    public static class Response{
        private int code;
        private String description;

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        private Response(int code, String description) {
            this.code = code;
            this.description = description;
        }
    }


    public static final Response RES_OK = new Response(0,"OK");
    public static final Response RES_FATAL = new Response(-1,"FATAL");
    public static final Response LOGIN_BUSY = new Response(-2,"LOGIN BUSY");
    public static final Response INCORRECT_DATA = new Response(-3, "INCORRECT DATA");


}
