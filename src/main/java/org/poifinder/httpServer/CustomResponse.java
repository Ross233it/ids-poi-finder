package org.poifinder.httpServer;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class CustomResponse<T> {

    private T data;

    private String type;

    private String message;

    private int status;

    public CustomResponse(T data){
        this.data = data;
        this.status = 200;
    }

    public CustomResponse(String type, String message, int status) {
        this.type = type;
        this.message = message;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
