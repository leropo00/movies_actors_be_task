package org.moviesdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseMetadata {

    Integer total;

    @JsonProperty("results_count")
    Integer resultCount;

    @JsonProperty("has_pagination")
    Boolean hasPagination;

    public ResponseMetadata(int total)  {
        this.total = total;
        this.resultCount = total;
        this.hasPagination = false;
    }
}
