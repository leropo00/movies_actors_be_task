package org.moviesdata.response.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.moviesdata.constants.ErrorResponseCode;

public class PaginationError extends ErrorResponse {
    @JsonProperty("max_allowed_page_index")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer maxPageIndex;

    public PaginationError() {}

    public PaginationError(ErrorResponseCode code, String description) {
        super(code, description);
    }

    public PaginationError(ErrorResponseCode code, String description, Integer maxPageIndex) {
        super(code, description);
        this.maxPageIndex = maxPageIndex;
    }

    public Integer getMaxPageIndex() {
        return maxPageIndex;
    }

    public void setMaxPageIndex(Integer maxPageIndex) {
        this.maxPageIndex = maxPageIndex;
    }
}
