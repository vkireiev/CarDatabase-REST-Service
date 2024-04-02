package ua.foxmided.foxstudent103852.cardatabaserestservice.util.constants;

public class VehicleMakeConstants {
    public static final String GET_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": {
                "id": 3,
                "name": "Cadillac"
              }
            }
            """;
    public static final String GETALL_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": {
                "content": [
                  {
                    "id": 1,
                    "name": "Audi"
                  },
                  {
                    "id": 2,
                    "name": "Chevrolet"
                  },
                  {
                    "id": 3,
                    "name": "Cadillac"
                  },
                  {
                    "id": 4,
                    "name": "Acura"
                  },
                  {
                    "id": 5,
                    "name": "BMW"
                  }
                ],
                "pageable": {
                  "pageNumber": 0,
                  "pageSize": 5,
                  "sort": {
                    "empty": false,
                    "sorted": true,
                    "unsorted": false
                  },
                  "offset": 0,
                  "paged": true,
                  "unpaged": false
                },
                "totalPages": 6,
                "totalElements": 26,
                "last": false,
                "size": 5,
                "number": 0,
                "sort": {
                  "empty": false,
                  "sorted": true,
                  "unsorted": false
                },
                "numberOfElements": 5,
                "first": true,
                "empty": false
              }
            }
            """;
    public static final String PUT_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": {
                "id": 3,
                "name": "Cadillac (update)"
              }
            }
            """;
    public static final String POST_REQUEST_JSON_BODY_EXAMPLE = """
            {
              "name": "Cadillac (new)"
            }
            """;
    public static final String PUT_REQUEST_JSON_BODY_EXAMPLE = """
            {
              "name": "Cadillac (update)"
            }
            """;

    private VehicleMakeConstants() {
    }

}
