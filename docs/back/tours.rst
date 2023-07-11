========================
    Resource Tours
========================


POST resource
------------

    .. http:post:: /tours

    Create a tour

    * **Petition example**

        .. host:: http

            POST /tours
            
            Content-Type: json

            {
                "tourImageURL":"https://urlDeLaImagen",
                "tourName":"nombreDelTour",
                "tourDescription":"descripcionDelTour",
                "tourClassification":"clasificacionDelTour",
                "tourCapacity":5,
                "tourAvailability":true,
                "tourPrice":2.0,
                "tourScore":4,
                "tourCategory":{
                    "id_category":2
                }
            } 

    * **Response examples**

        .. host:: http

            HTTP/1.1 200 OK
            Content-Type: json
            
                {
                    "id_tour": 1,
                    "tourImageURL": "https://urlDeLaImagen",
                    "tourName": "nombreDelTour",
                    "tourDescription": "descripcionDelTour",
                    "tourClassification": "clasificacionDelTour",
                    "tourCapacity": 5,
                    "tourAvailability": true,
                    "tourPrice": 2.0,
                    "tourScore": 4,
                    "tourCategory": {
                        "id_category": 2,
                        "categoryName": "nombreCategoria",
                        "categoryDescription": "descripcionCategoria",
                        "categoryImageURL": "Https://urlDeLaImagen"
                    }
                }
            

            HTTP/1.1 400 BAD_REQUEST
            Content-Type: json

            {
                "timestamp": "2023-05-19T00:42:19.981+00:00",
                "status": 400,
                "error": "Bad Request",
                "path": "/tours"
            }

            HTTP/1.1 500Internal Server Error
            Content-Type: json

            {
                "timestamp": "2023-05-19T00:41:26.467+00:00",
                "status": 500,
                "error": "Internal Server Error",
                "path": "/tours"
             }



GET Resource
-----------
    .. http:get:: /tours

    Get the list of all tours

    * **Petition example**

        .. host:: http

            GET /tours
            Content-Type: None

    * **Response example**

        .. host:: http

            HTTP/1.1 200 OK
            Content-Type: json

            [ 
                {
                    "id_tour": 1,
                    "tourImageURL": "https://urlDeLaImagen",
                    "tourName": "nombreDelTour",
                    "tourDescription": "descripcionDelTour",
                    "tourClassification": "clasificacionDelTour",
                    "tourCapacity": 5,
                    "tourAvailability": true,
                    "tourPrice": 2.0,
                    "tourScore": 4,
                    "tourCategory": {
                        "id_category": 2,
                        "categoryName": "nombreCategoria",
                        "categoryDescription": "descripcionCategoria",
                        "categoryImageURL": "Https://urlDeLaImagen"
                    },
                    "id_tour": 1,
                    "tourImageURL": "https://urlDeLaImagen",
                    "tourName": "nombreDelTour",
                    "tourDescription": "descripcionDelTour",
                    "tourClassification": "clasificacionDelTour",
                    "tourCapacity": 5,
                    "tourAvailability": true,
                    "tourPrice": 2.0,
                    "tourScore": 4,
                    "tourCategory": {
                        "id_category": 2,
                        "categoryName": "nombreCategoria",
                        "categoryDescription": "descripcionCategoria",
                        "categoryImageURL": "Https://urlDeLaImagen"
                }
            ]


GET By Id Resource
-----------
    .. http:get:: /tours/{id}

    Get a tour by id

    * **Petition example**

        .. host:: http

            GET /tours/1
            Content-Type: None

    * **Response example**

        .. host:: http

            HTTP/1.1 200 OK
            Content-Type: json

            {
                "id_tour": 1,
                "tourImageURL": "https://urlDeLaImagen",
                "tourName": "nombreDelTour",
                "tourDescription": "descripcionDelTour",
                "tourClassification": "clasificacionDelTour",
                "tourCapacity": 5,
                "tourAvailability": true,
                "tourPrice": 2.0,
                "tourScore": 4,
                "tourCategory": {
                    "id_category": 2,
                    "categoryName": "nombreCategoria",
                    "categoryDescription": "descripcionCategoria",
                    "categoryImageURL": "Https://urlDeLaImagen"
                }
            }            


DELETE resource
--------------

    .. http:delete:: /api/tours/{id}

    Delete a previously created tour


    * **Petition example**

        .. host:: http

            DELETE /tours/1
            Content-Type: None

    * **Response example**

        .. host:: http

            HTTP/1.1 200OK
            Content-Type: Text

            
            Ha eliminado el tour de manera exitosa.
            

            HTTP/1.1 404 NOT FOUND
            Content-Type: Text

            
            El tour con id: 1 no fue encontrado  
