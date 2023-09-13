package org.moviesdata.utils;

import jakarta.ws.rs.core.Response;
import org.moviesdata.constants.ErrorResponseCode;
import org.moviesdata.response.ResponseMetadata;
import org.moviesdata.response.errors.PaginationError;

public class ResponseGenerator {

    public static Response pageSizeMissing() {
        return Response.status(Response.Status.BAD_REQUEST).entity(
                new PaginationError(ErrorResponseCode.PAGINATION_SIZE_MISSING,
                        "When supplying page_index parameter, page_size must also be set")).build();
    }

    public static Response paginationOutsideBounds() {
        return Response.status(Response.Status.BAD_REQUEST).entity(
                new PaginationError(ErrorResponseCode.PAGINATION_OUTSIDE_BOUNDARIES,
                        "Parameter page_index is outside possible bounds")).build();
    }
}
