package ua.foxmided.foxstudent103852.cardatabaserestservice.util.constants;

public class VehicleConstants {
    public static final String GET_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": {
                "id": 7,
                "objectId": "ATAeU1NdOi",
                "model": {
                  "id": 7,
                  "name": "3 Series",
                  "make": {
                    "id": 5,
                    "name": "BMW"
                  },
                  "years": [
                    {
                      "id": 7,
                      "year": "2014"
                    },
                    {
                      "id": 8,
                      "year": "2013"
                    },
                    {
                      "id": 15,
                      "year": "2006"
                    },
                    {
                      "id": 1,
                      "year": "2020"
                    },
                    {
                      "id": 2,
                      "year": "2019"
                    }
                  ]
                },
                "year": {
                  "id": 2,
                  "year": "2019"
                },
                "categories": [
                  {
                    "id": 8,
                    "name": "Wagon"
                  },
                  {
                    "id": 2,
                    "name": "Sedan"
                  }
                ]
              }
            }
            """;
    public static final String FINDALL_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": {
                "content": [
                  {
                    "id": 19,
                    "objectId": "aKCqI0lYGI",
                    "model": {
                      "id": 18,
                      "name": "M6",
                      "make": {
                        "id": 5,
                        "name": "BMW"
                      },
                      "years": [
                        {
                          "id": 9,
                          "year": "2012"
                        },
                        {
                          "id": 2,
                          "year": "2019"
                        }
                      ]
                    },
                    "year": {
                      "id": 2,
                      "year": "2019"
                    },
                    "categories": [
                      {
                        "id": 3,
                        "name": "Coupe"
                      }
                    ]
                  },
                  {
                    "id": 46,
                    "objectId": "WpVAtULJ1x",
                    "model": {
                      "id": 18,
                      "name": "M6",
                      "make": {
                        "id": 5,
                        "name": "BMW"
                      },
                      "years": [
                        {
                          "id": 9,
                          "year": "2012"
                        },
                        {
                          "id": 2,
                          "year": "2019"
                        }
                      ]
                    },
                    "year": {
                      "id": 9,
                      "year": "2012"
                    },
                    "categories": [
                      {
                        "id": 6,
                        "name": "Convertible"
                      }
                    ]
                  }
                ],
                "pageable": {
                  "pageNumber": 0,
                  "pageSize": 200,
                  "sort": {
                    "empty": true,
                    "unsorted": true,
                    "sorted": false
                  },
                  "offset": 0,
                  "paged": true,
                  "unpaged": false
                },
                "totalPages": 1,
                "totalElements": 2,
                "last": true,
                "size": 200,
                "number": 0,
                "sort": {
                  "empty": true,
                  "unsorted": true,
                  "sorted": false
                },
                "numberOfElements": 2,
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
                "objectId": "ZRgPP9dBMm",
                "model": {
                  "id": 1,
                  "name": "Q3",
                  "make": {
                    "id": 1,
                    "name": "Audi"
                  },
                  "years": [
                    {
                      "id": 5,
                      "year": "2016"
                    },
                    {
                      "id": 1,
                      "year": "2020"
                    }
                  ]
                },
                "year": {
                  "id": 5,
                  "year": "2016"
                },
                "categories": [
                  {
                    "id": 3,
                    "name": "Coupe"
                  }
                ]
              }
            }
            """;
    public static final String POST_REQUEST_BODY_JSON_EXAMPLE = """
            {
              "objectId": "ZRgPP9dBMm",
              "model": {
                "name": "Q3",
                "make": {
                  "id": 1,
                  "name": "Audi"
                },
                "years": [
                  {
                    "id": 5,
                    "year": "2016"
                  },
                  {
                    "id": 1,
                    "year": "2020"
                  }
                ]
              },
              "year": {
                "id": 1,
                "year": "2020"
              },
              "categories": [
                {
                  "id": 1,
                  "name": "SUV"
                }
              ]
            }
            """;
    public static final String PUT_REQUEST_BODY_JSON_EXAMPLE = """
            {
              "objectId": "ZRgPP9dBMm",
              "model": {
                "name": "Q3",
                "make": {
                  "id": 1,
                  "name": "Audi"
                },
                "years": [
                  {
                    "id": 5,
                    "year": "2016"
                  },
                  {
                    "id": 1,
                    "year": "2020"
                  }
                ]
              },
              "year": {
                "id": 1,
                "year": "2020"
              },
              "categories": [
                {
                  "id": 1,
                  "name": "SUV"
                }
              ]
            }
            """;

    private VehicleConstants() {
    }

}
