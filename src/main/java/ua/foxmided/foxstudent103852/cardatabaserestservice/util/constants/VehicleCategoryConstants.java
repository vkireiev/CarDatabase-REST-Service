package ua.foxmided.foxstudent103852.cardatabaserestservice.util.constants;

public class VehicleCategoryConstants {
    public static final String GET_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": {
                "id": 2,
                "name": "Sedan"
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
                    "name": "SUV"
                  },
                  {
                    "id": 2,
                    "name": "Sedan"
                  },
                  {
                    "id": 3,
                    "name": "Coupe"
                  },
                  {
                    "id": 4,
                    "name": "Pickup"
                  },
                  {
                    "id": 5,
                    "name": "Hatchback"
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
                "totalPages": 2,
                "totalElements": 9,
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
                "id": 2,
                "name": "Sedan (update)"
              }
            }
            """;
    public static final String POST_REQUEST_BODY_JSON_EXAMPLE = """
            {
              "name": "Sedan (new)"
            }
            """;
    public static final String PUT_REQUEST_BODY_JSON_EXAMPLE = """
            {
              "name": "Sedan (update)"
            }
            """;

    private VehicleCategoryConstants() {
    }

}
