src folder contains files that contain an example for usage of JDBC with sqlite and server and client classes; however, JDBC driver external library is not included.
DBHelper is the file where database is created and queries are found using SQLite.
Server represents a server class with a DBHelper private attribute.
Client represent a client class.
Actions and response are enum classes containing examples of server actions and client responses.
Finally, user is an example class for the users which I create a table for in DBHelper and Server inlcuding the defined getter and setter functions.
