package org.moviesdata.response.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.moviesdata.constants.ErrorResponseCode;

import java.util.List;

public class EntityError extends ErrorResponse {

    @JsonProperty("entity_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String entityId;

    @JsonProperty("entity_ids")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> entityIds;

    public EntityError() {}
    public EntityError(ErrorResponseCode code, String description) {
        super(code, description);
    }

    public EntityError(ErrorResponseCode code, String description, String entityId) {
        super(code, description);
        this.entityId = entityId;
    }

    public EntityError(ErrorResponseCode code, String description, List<String> entityIds) {
        super(code, description);
        this.entityIds = entityIds;
    }


    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<String> getEntityIds() {
        return entityIds;
    }

    public void setEntityIds(List<String> entityIds) {
        this.entityIds = entityIds;
    }

}
