package org.moviesdata.response.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.moviesdata.constants.ErrorResponseCode;

public class EntityError extends ErrorResponse {

    @JsonProperty("entity_id")
    private String entityId;

    public EntityError() {}
    public EntityError(ErrorResponseCode code, String description) {
        super(code, description);
    }

    public EntityError(ErrorResponseCode code, String description, String entityId) {
        super(code, description);
        this.entityId = entityId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
