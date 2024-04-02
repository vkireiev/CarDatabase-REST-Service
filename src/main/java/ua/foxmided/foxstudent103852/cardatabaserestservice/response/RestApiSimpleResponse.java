package ua.foxmided.foxstudent103852.cardatabaserestservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestApiSimpleResponse {

    @JsonProperty("status")
    protected int status;

    @JsonProperty("message")
    protected String message;

}
