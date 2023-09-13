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
        // next page is not possible, when sum of all results displayed, counted from start page is equal or less than total counts
        // formula for counting results from start page is following:  (currentPageIndex + 1) * pageSize
        this.hasNextPage = ((this.currentPageIndex + 1) * this.pageSize) < this.total;
        if(this.hasNextPage) {
            this.nextPageIndex = this.currentPageIndex + 1;
        }
    }

    public boolean outsidePaginationBoundaries() {
        return this.hasPagination && this.total > 0 && this.resultCount == 0;
    }

    public int calculateMaxPageIndex() {
        if(!this.hasPagination) return 0;

        // only one page is possible
        if(this.pageSize >= this.total) return 0;

        // number of pages that are filled to max possible size
        int numberOfFilledPages = this.total / this.pageSize;

        //  integer division in java discards the remainder
        int remainder  = this.total % this.pageSize;

        // if remainder of division is greater than 0,
        // this means last page will have number of results equal to remained
        int numberOfPages = numberOfFilledPages + (remainder > 0 ? 1 : 0);

        // -1 is substracted because pageIndex starts with 0
        return numberOfPages - 1;
    }
}
