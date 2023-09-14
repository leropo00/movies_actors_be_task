package org.moviesdata.response.errors;

import org.moviesdata.constants.ErrorResponseCode;

public class ErrorResponse {
    protected ErrorResponseCode code;
    protected String description;

    public ErrorResponse() {}

    public ErrorResponse(ErrorResponseCode code, String description) {
        this.code = code;
        this.description = description;
    }

    public ErrorResponseCode getCode() {
        return code;
    }

    public void setCode(ErrorResponseCode code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
