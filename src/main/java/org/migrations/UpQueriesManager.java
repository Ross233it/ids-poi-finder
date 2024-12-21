package org.migrations;

public class UpQueriesManager extends QueriesManager{

    @Override
    protected void buildQueriesList(){
        //Users
        this.queries.add("CREATE TABLE IF NOT EXISTS users ("+
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) NOT NULL UNIQUE," +
                "email VARCHAR(100) NOT NULL UNIQUE," +
                "password VARCHAR(25) NOT NULL," +
                "role ENUM('platformAdmin', 'contributor', 'authContributor', 'tourist', 'animator') NOT NULL," +
                "access_token VARCHAR(255) DEFAULT NULL" +
                ");"
        );

        //todo cript password
//        String hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt(saltRounds));
        //utenti di esempio
        this.queries.add("INSERT INTO users (username, email, password, role, access_token)"+
        "VALUES"+
            "('user1', 'user1@example.com', 'password', 'platformAdmin', NULL),"+
            "('user2', 'user2@example.com', 'password', 'contributor', NULL),"+
            "('user3', 'user3@example.com', 'password', 'authContributor', NULL),"+
            "('user4', 'user4@example.com', 'password', 'animator', NULL),"+
            "('user5', 'user5@example.com', 'password', 'tourist', NULL);"
        );

        //Poi Categories
        this.queries.add("CREATE TABLE IF NOT EXISTS categories ("+
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) UNIQUE NOT NULL,"+
                "description TEXT"+
                ");"
        );

        //Tags
        this.queries.add("CREATE TABLE IF NOT EXISTS tags ("+
                        "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(255) UNIQUE NOT NULL,"+
                        "description TEXT"+
                        ");"
        );

        //Geolocations
        this.queries.add("CREATE TABLE IF NOT EXISTS  geolocations (" +
                " id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                " latitude DOUBLE NOT NULL," +
                " longitude DOUBLE NOT NULL," +
                " address VARCHAR(255) NOT NULL," +
                " number VARCHAR(20)," +
                " cap VARCHAR(10)" +
                ")"
        );

        //Municipalities
        //                "FOREIGN KEY (geolocation_id) REFERENCES geolocations(id) ON DELETE SET NULL" +
        this.queries.add("CREATE TABLE IF NOT EXISTS municipalities (" +
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "province VARCHAR(255) NOT NULL, " +
                "region VARCHAR(255) NOT NULL, " +
                "geolocation_id INT UNSIGNED " +
                ");"
        );
        //Roles
        this.queries.add("CREATE TABLE IF NOT EXISTS roles (" +
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "description VARCHAR(255) NOT NULL" +
                ");"
        );

        //Pois
        //                "FOREIGN KEY (municipality_id) REFERENCES municipality(id) ON DELETE CASCADE," +
//                "FOREIGN KEY (geolocation_id) REFERENCES geolocations(id) ON DELETE SET NULL" +
        this.queries.add("CREATE TABLE IF NOT EXISTS pois ("+
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"+
                "name VARCHAR(255) NOT NULL,"+
                "description TEXT," +
                "type VARCHAR(100)," +
                "is_logical BOOLEAN NOT NULL DEFAULT 0,"+
                "municipality_id INT UNSIGNED," +
                "geolocation_id INT UNSIGNED," +
                "author_id INT UNSIGNED," +
                "publisher_id INT UNSIGNED" +
                ");"
        );
    }


}
