package org.moviesdata.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.panache.common.Page;
import lombok.Data;

@Data
public class ResponseMetadata {
    Integer total;

    @JsonProperty("results_count")
    Integer resultCount;

    @JsonProperty("has_pagination")
    Boolean hasPagination;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("current_page_index")
    // page indexing starts with 0
    Integer currentPageIndex;

    @JsonProperty("page_size")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer pageSize;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("has_previous_page")
    Boolean hasPreviousPage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("previous_page_index")
    Integer previousPageIndex;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("has_next_page")
    Boolean hasNextPage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("next_page_index")
    Integer nextPageIndex;

    public ResponseMetadata(int total) {
        this.total = total;
        this.resultCount = total;
        this.hasPagination = false;
    }

    public ResponseMetadata(int total, int resultCount, Page page) {
        this.total = total;
        this.resultCount = resultCount;
        this.hasPagination = true;

        this.currentPageIndex = page.index;
        this.pageSize = page.size;
        this.hasPreviousPage = this.currentPageIndex > 0;
        if(this.hasPreviousPage) {
            this.previousPageIndex = this.currentPageIndex - 1;
        }
        this.hasNextPage = true;
        if(this.hasPreviousPage) {
            this.nextPageIndex = this.currentPageIndex + 1;
        }
    }
}
