package ua.foxmided.foxstudent103852.cardatabaserestservice.util.constants;

public class ModelYearConstants {
    public static final String GET_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": {
                "id": 7,
                "year": "2024"
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
                    "year": "2020"
                  },
                  {
                    "id": 2,
                    "year": "2019"
                  },
                  {
                    "id": 3,
                    "year": "2018"
                  },
                  {
                    "id": 4,
                    "year": "2017"
                  },
                  {
                    "id": 5,
                    "year": "2016"
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
                "totalElements": 30,
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
                "id": 7,
                "year": "1991"
              }
            }
            """;
    public static final String POST_REQUEST_BODY_JSON_EXAMPLE = """
            {
              "year": 2025
            }
            """;
    public static final String PUT_REQUEST_BODY_JSON_EXAMPLE = """
            {
              "year": 1991
            }
            """;

    private ModelYearConstants() {
    }

}
