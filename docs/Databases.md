# Entity Relationship Diagram (ERD)

![](https://i.postimg.cc/m21kqVKy/DER-Proyecto-Integrador-Imagen.png)

The Entity-Relationship diagram was created, which includes the entities with their respective attributes and corresponding relationships. 

![](https://i.postimg.cc/7LjTt6Nm/tipo-de-dato.png)

The data types for each attribute are included.

The deliverable for the First Sprint for databases is to create a MySQL database with the table 'categories' to maintain the data for our system. 

In order to accomplish the above, we followed the following steps:

The connection to the remote database corresponding to our team has been established, in order to review the database the credentials are provided below:

Database endpoint --> db.ctd.academy:3306
User: 0523TDPRON2C03LAED1021PT_GRUPO10
Password: ooBuaf6k

Once the proper connection to the database was established, we defined the structure of the "Category" table, including the column names and their respective data types.

![](https://i.postimg.cc/D0rjyRZ3/creaci-n-tablas.png)

We designated a primary key for the "Category" table to uniquely identify each record. Additionally, we applied any necessary constraints, such as NOT NULL, to ensure data integrity.

We executed the necessary SQL statements to create the "Category" table in the database.

![](https://i.postimg.cc/yY7nyQxW/ejemplo.png)

Finally, we checked the database to ensure that the "Category" table was successfully created.

We also followed the same steps mentioned above to create the "Tour" table, which has a direct relationship to display the products within each category.
