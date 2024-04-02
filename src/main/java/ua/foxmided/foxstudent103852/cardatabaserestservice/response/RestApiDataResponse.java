package ua.foxmided.foxstudent103852.cardatabaserestservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestApiDataResponse extends RestApiSimpleResponse {

    @JsonProperty("data")
    protected Object data;

    public RestApiDataResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

}
