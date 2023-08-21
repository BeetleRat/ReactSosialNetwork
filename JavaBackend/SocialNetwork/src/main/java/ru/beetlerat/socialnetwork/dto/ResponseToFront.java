package ru.beetlerat.socialnetwork.dto;

public class ResponseToFront {
    public enum Message {
        NOT_AUTHORIZED("You are not authorized."),
        NOT_FOUND("User is not found."),
        ARE_YOU_ROBOT("Are you robot?");
        private final String message;

        Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    public enum Code {
        AUTHORIZED_AND_COMPLETED(0),
        NEW_TOKEN_RECEIVED(1),
        NOT_AUTHORIZED(2),
        NOT_VALID(3),
        NOT_FOUND(4),
        CAPCHA_REQUIRED(10),
        EXCEPTION(99);
        private final int code;

        Code(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    protected String message;
    protected int resultCode;

    public ResponseToFront() {

    }

    protected ResponseToFront(String message, int resultCode) {
        this.message = message;
        this.resultCode = resultCode;
    }

    public static ResponseToFront Ok() {
        return new ResponseToFront("", Code.AUTHORIZED_AND_COMPLETED.getCode());
    }

    public static ResponseToFront FromMessageAndResultCode(String message, int resultCode) {
        return new ResponseToFront(message, resultCode);
    }

    public static ResponseToFront BadCredentials() {
        return FromMessageAndResultCode("Incorrect username or password", Code.NOT_FOUND.getCode());
    }

    public static ResponseToFront NotAuthorized() {
        ResponseToFront responseToFront = new ResponseToFront();
        responseToFront.setMessage(Message.NOT_AUTHORIZED.getMessage());
        responseToFront.setResultCode(Code.AUTHORIZED_AND_COMPLETED.getCode());

        return responseToFront;
    }

    public static ResponseToFront NotFound() {
        ResponseToFront responseToFront = new ResponseToFront();
        responseToFront.setMessage(Message.NOT_FOUND.getMessage());
        responseToFront.setResultCode(Code.NOT_AUTHORIZED.getCode());

        return responseToFront;
    }

    public static ResponseToFront FromExceptionMessage(String exceptionMessage) {
        ResponseToFront responseToFront = new ResponseToFront();
        responseToFront.setMessage(exceptionMessage);
        responseToFront.setResultCode(Code.EXCEPTION.getCode());

        return responseToFront;
    }

    public String getMessage() {
        return message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
