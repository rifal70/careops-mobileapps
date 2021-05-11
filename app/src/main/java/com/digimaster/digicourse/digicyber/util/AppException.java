package com.digimaster.digicourse.digicyber.util;

/**
 * Created by Teke on 05/02/2018.
 */

public class AppException extends Exception {
    private String code;
    private String message;

    public AppException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
