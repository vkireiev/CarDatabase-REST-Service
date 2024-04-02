package ua.foxmided.foxstudent103852.cardatabaserestservice.util.constants;

public class VehicleModelConstants {
    public static final String GET_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": {
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
                  {
                    "id": 2,
                    "name": "Malibu",
                    "make": {
                      "id": 2,
                      "name": "Chevrolet"
                    },
                    "years": [
                      {
                        "id": 1,
                        "year": "2020"
                      }
                    ]
                  },
                  {
                    "id": 3,
                    "name": "Escalade ESV",
                    "make": {
                      "id": 3,
                      "name": "Cadillac"
                    },
                    "years": [
                      {
                        "id": 1,
                        "year": "2020"
                      }
                    ]
                  },
                  {
                    "id": 4,
                    "name": "Corvette",
                    "make": {
                      "id": 2,
                      "name": "Chevrolet"
                    },
                    "years": [
                      {
                        "id": 1,
                        "year": "2020"
                      }
                    ]
                  },
                  {
                    "id": 5,
                    "name": "RLX",
                    "make": {
                      "id": 4,
                      "name": "Acura"
                    },
                    "years": [
                      {
                        "id": 1,
                        "year": "2020"
                      }
                    ]
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
                "totalPages": 12,
                "totalElements": 59,
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
    public static final String GETALL_BY_MAKE_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": {
                "content": [
                  {
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
                  {
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
                  {
                    "id": 19,
                    "name": "Z4",
                    "make": {
                      "id": 5,
                      "name": "BMW"
                    },
                    "years": [
                      {
                        "id": 2,
                        "year": "2019"
                      }
                    ]
                  },
                  {
                    "id": 49,
                    "name": "Z8",
                    "make": {
                      "id": 5,
                      "name": "BMW"
                    },
                    "years": [
                      {
                        "id": 18,
                        "year": "2001"
                      }
                    ]
                  },
                  {
                    "id": 52,
                    "name": "Z3",
                    "make": {
                      "id": 5,
                      "name": "BMW"
                    },
                    "years": [
                      {
                        "id": 22,
                        "year": "1997"
                      }
                    ]
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
                "totalPages": 1,
                "totalElements": 5,
                "last": true,
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
    public static final String GETALL_YEARS_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": [
                {
                  "id": 5,
                  "year": "2016"
                },
                {
                  "id": 1,
                  "year": "2020"
                }
              ]
            }
            """;
    public static final String POST_YEAR_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": [
                {
                  "id": 5,
                  "year": "2016"
                },
                {
                  "id": 1,
                  "year": "2020"
                },
                {
                  "id": 3,
                  "year": "2018"
                }
              ]
            }
            """;
    public static final String DELETE_YEAR_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": [
                {
                  "id": 5,
                  "year": "2016"
                },
                {
                  "id": 1,
                  "year": "2020"
                }
              ]
            }
            """;
    public static final String PUT_RESPONSE_JSON_EXAMPLE = """
            {
              "status": 200,
              "message": "OK",
              "data": {
                "id": 5,
                "name": "Q3 (update)",
                "make": {
                  "id": 1,
                  "name": "Audi"
                },
                "years": [
                  {
                    "id": 7,
                    "year": "2014"
                  },
                  {
                    "id": 1,
                    "year": "2020"
                  }
                ]
              }
            }
            """;
    public static final String POST_BY_MAKE_REQUEST_BODY_JSON_EXAMPLE = """
            {
              "name": "Q3 (new)",
              "years": [
                {
                  "id": 1,
                  "year": "2020"
                }
              ]
            }
            """;
    public static final String PUT_REQUEST_BODY_JSON_EXAMPLE = """
            {
              "name": "Q3 (update)",
              "make": {
                "id": 1,
                "name": "Audi"
              },
              "years": [
                {
                  "id": 1,
                  "year": "2020"
                },
                {
                  "id": 7,
                  "year": "2014"
                }
              ]
            }
            """;

    private VehicleModelConstants() {
    }

}
