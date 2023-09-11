package org.moviesdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseMetadata {

    Integer total;

    @JsonProperty("results_count")
    Integer resultCount;
}
