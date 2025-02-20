package org.poifinder.migrations;

import org.poifinder.httpServer.auth.AuthUtilities;
import org.springframework.stereotype.Service;

//@Service("upQueryManager")
public class UpQueriesManager extends QueriesManager{

    @Override
    protected void buildQueriesList(){
        //Users
        this.queries.add("CREATE TABLE IF NOT EXISTS users ("+
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) NOT NULL UNIQUE," +
                "email VARCHAR(100) NOT NULL UNIQUE," +
                "password VARCHAR(255) NOT NULL," +
                "role ENUM('platformAdmin', 'contributor', 'authContributor', 'tourist', 'animator') NOT NULL," +
                "salt VARCHAR(255) DEFAULT NULL," +
                "access_token VARCHAR(255) DEFAULT NULL" +
                ");"
        );

        //users data
        String salt = AuthUtilities.generateSalt();
        String password = AuthUtilities.hashPassword("password", salt);

        this.queries.add("INSERT INTO users (username, email, password, role, salt, access_token)"+
        "VALUES"+
            "('user1', 'user1@example.com','"+  password + "','platformAdmin', '"+ salt +"' , NULL),"+
            "('user2', 'user2@example.com','"+  password + "','contributor', '"+ salt +"' , NULL),"+
            "('user3', 'user3@example.com','"+  password + "','authContributor', '"+ salt +"' , NULL),"+
            "('user4', 'user4@example.com','"+  password + "','tourist', '"+ salt +"' , NULL),"+
            "('user5', 'user5@example.com','"+  password + "','animator', '"+ salt +"' , NULL)"
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

        //Geolocations content
        this.queries.add("INSERT INTO geolocations (latitude, longitude, address, number, cap)"+
                "VALUES"+
                "(43.7139, 13.2174, 'Via Roma', '1', '60019')," +
                "(43.6158, 13.5189, 'Via Verdi', '1546', '60121')," +
                "(43.5196, 13.1070, 'Via XX Settembre', '10/A', '60035')," +
                "(43.5875, 12.8903, 'Piazza Garibaldi', '23', '61040')," +
                "(43.6167, 13.3928, 'Via della Tauromachia', '211', '60015')," +
                "(43.9137, 12.9126, 'Via Caduti della Resistenza', '1001', '61121')"
        );

        //Municipalities
        this.queries.add("CREATE TABLE IF NOT EXISTS municipalities (" +
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "province VARCHAR(255) NOT NULL, " +
                "region VARCHAR(255) NOT NULL, " +
                "geolocation_id INT UNSIGNED, " +
                "author_id INT UNSIGNED NOT NULL DEFAULT 0," +
                "approver_id INT UNSIGNED NOT NULL DEFAULT 0" +
                ");"
        );

        //Municipalities content
        this.queries.add("INSERT INTO municipalities (name, province, region, geolocation_id, author_id, approver_id)"+
                "VALUES"+
                "('Comune di Senigallia', 'AN', 'Marche', 1, 1,0)," +
                "('Comune di Ancona', 'AN', 'Marche', 2,1,0)," +
                "('Comune di Jesi', 'AN', 'Marche',3,1,0)," +
                "('Comune di Mondavio', 'PU', 'Marche',4,1,0)," +
                "('Comune di Falconara Marittima','AN', 'Marche', 5,1,0)," +
                "('Comune di Pesaro','PU', 'Marche', 6,1,0)"
        );
        //Roles
        this.queries.add("CREATE TABLE IF NOT EXISTS roles (" +
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "description VARCHAR(255) NOT NULL" +
                ");"
        );

        //Pois
        this.queries.add("CREATE TABLE IF NOT EXISTS pois ("+
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"+
                "name VARCHAR(255) NOT NULL,"+
                "description TEXT," +
                "type VARCHAR(100)," +
                "status ENUM('published', 'pending', 'deleted') NOT NULL," +
                "is_logical BOOLEAN NOT NULL DEFAULT 0,"+
                "municipality_id INT UNSIGNED," +
                "geolocation_id INT UNSIGNED," +
                "author_id INT UNSIGNED  DEFAULT NULL," +
                "approver_id INT UNSIGNED  DEFAULT NULL" +
                ");"
        );

        //Activities
        this.queries.add("CREATE TABLE activities (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "description VARCHAR(255) NOT NULL," +
                "type VARCHAR(30) NOT NULL," +
                "status VARCHAR(30) NOT NULL DEFAULT 'pending'," +
                "author_id INT NOT NULL DEFAULT 0,"+
                "approver_id INT NOT NULL default 0"+
                ");"
        );

        /** Poi Activities pivot **/
        this.queries.add(
                "CREATE TABLE pois_activities ("+
                "id INT AUTO_INCREMENT PRIMARY KEY,"+
                "poi_id INT,"+
                "activity_id INT"+
                ");"
        );
        /** Contests **/
        this.queries.add(
                "CREATE TABLE contests ( " +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "begin_date DATE NOT NULL," +
                "end_date DATE NOT NULL," +
                "rules JSON NOT NULL," +
                "activity_id INT NOT NULL" +
                ")"
        );

        /** Itineraries **/
        this.queries.add(
                "CREATE TABLE itineraries ( " +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "date DATE NOT NULL)"
        );
    }
}
