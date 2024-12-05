package com.anoop.assignment.model;

public class ErrorResponse {
    private ErrorCode code;
    private String message;

    public ErrorResponse(ErrorCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(ErrorCode code) {
        this(code, code.getDescription());
    }

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
        this.message = code.getDescription();
    }

    public String getMessage() {
        return message;
    }

    public static String getMessage(ErrorCode code) {
        return code.getDescription();
    }

}