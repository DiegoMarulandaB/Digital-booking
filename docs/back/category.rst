========================
    Resource Category
========================


POST resource
------------

    .. http:post:: /category

    Create a category

    * **Petition example**

        .. host:: http

            POST /tours
            
            Content-Type: json

            {   
                "categoryName":"categoria_1",
                "categoryDescription":"esto es una categoria",
                "categoryImageURL":"Https://esto es una url",
                "tours":[]
            }

    * **Response examples**

        .. host:: http

            HTTP/1.1 200 OK
            Content-Type: json
            
                {
                    "id_category": 1,
                    "categoryName": "categoria_1",
                    "categoryDescription": "esto es una categoria",
                    "categoryImageURL": "Https://esto es una url"
                }
            

            HTTP/1.1 400 BAD_REQUEST
            Content-Type: json

            {
                "timestamp": "2023-05-19T01:11:18.014+00:00",
                "status": 400,
                "error": "Bad Request",
                "path": "/category"
            }

            HTTP/1.1 500Internal Server Error
            Content-Type: json

            {
                "timestamp": "2023-05-19T01:11:18.014+00:00",
                "status": 500,
                "error": "Internal Server Error",
                "path": "/category"
             }


GET Resource
-----------
    .. http:get:: /category

    Get categorie by id

    * **Petition example**

        .. host:: http

            GET /categroy
            Content-Type: None

    * **Response example**

        .. host:: http

            HTTP/1.1 200 OK
            Content-Type: json

            [  
                {
                    "id_category": 1,
                    "categoryName": "categoria_1",
                    "categoryDescription": "esto es una categoria",
                    "categoryImageURL": "Https://esto es una url"
                },
                {
                    "id_category": 2,
                    "categoryName": "categoria_2",
                    "categoryDescription": "esto es una categoria",
                    "categoryImageURL": "Https://esto es una url"
                }
            ]


GET By Id Resource
-----------
    .. http:get:: /category/{id}

    Get the list of all categories

    * **Petition example**

        .. host:: http

            GET /categroy/1
            Content-Type: None

    * **Response example**

        .. host:: http

            HTTP/1.1 200 OK
            Content-Type: json
  
            {
                "id_category": 1,
                "categoryName": "categoria_1",
                "categoryDescription": "esto es una categoria",
                "categoryImageURL": "Https://esto es una url"
            }
