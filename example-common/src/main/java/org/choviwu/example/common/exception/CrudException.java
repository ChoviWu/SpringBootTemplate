package org.choviwu.example.common.exception;

/**
 * Created by ChoviWu on 2018/04/14
 * Description:
 */
public class CrudException extends RuntimeException{

    private String message;

    private String code;

    public CrudException() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CrudException(String message, String code) {
        super(message);
        this.code = code;
    }

    public CrudException(String message) {
        this.message = message;
    }

    public CrudException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
