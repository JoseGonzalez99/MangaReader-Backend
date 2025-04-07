package com.hotbox.jaitymangareader.core.error;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final BaseErrorCode error;

    public ApiException(BaseErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    public ApiException(BaseErrorCode error, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
    }

    public static ApiException of(BaseErrorCode error) {
        return new ApiException(error);
    }
}
