package pl.ppsoft.crypto_calc.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class AppError {

    private int code;
    private String message;

    public AppError() {
    }

    public AppError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
