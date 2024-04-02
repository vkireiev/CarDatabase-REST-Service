package ua.foxmided.foxstudent103852.cardatabaserestservice.response;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseEntityHelper {

    public static HttpEntity<RestApiSimpleResponse> of(HttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestApiSimpleResponse(httpStatus.value(), httpStatus.name()));
    }

    public static HttpEntity<RestApiSimpleResponse> of(HttpStatus httpStatus, Object item) {
        return ResponseEntity
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestApiDataResponse(
                        httpStatus.value(),
                        httpStatus.name(),
                        item));
    }

}
