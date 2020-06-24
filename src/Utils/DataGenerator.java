package Utils;

public class DataGenerator {
    public boolean populateAllTables() {
        return populateCountryTable() &&
                populateCityTable() &&
                populateAddressTable() &&
                populateCustomerTable() &&
                //populateUserTable() &&
                populateAppointmentsTable();
    }

    public boolean populateCountryTable() {
        String query = "INSERT INTO country VALUES(country, createDate, createdBy, lastUpdateBy) \n" +
                "('US','2020-06-24 00:00:00','admin', 'admin'),\n" +
                "('Canada','2020-06-24 00:00:00','admin', 'admin'),\n" +
                "('Brazil','2020-06-24 00:00:00','admin', 'admin');";
        int result = SqlHelper.getInstance().update(query);
        return result > 0;

    }

    public boolean populateCityTable() {
        String query = "INSERT INTO city(city, countryId, createDate, createdBy, lastUpdateBy) VALUES \n" +
                "('New York', '1', '2020-06-24 00:00:00','admin', 'admin'),\n" +
                "('Los Angeles', '1', '2020-06-24 00:00:00','admin', 'admin'),\n" +
                "('Toronto', '2', '2020-06-24 00:00:00','admin', 'admin'),\n" +
                "('Vancouver', '2', '2020-06-24 00:00:00','admin', 'admin')";
        int result = SqlHelper.getInstance().update(query);
        return result > 0;
    }

    public boolean populateAddressTable() {
        String query = "INSERT INTO `address` VALUES \n" +
                "(1,'123 Main','',1,'11111','555-1212','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(2,'123 Elm','',3,'11112','555-1213','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(3,'123 Oak','',5,'11113','555-1214','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
        int result = SqlHelper.getInstance().update(query);
        return result > 0;
    }

    public boolean populateCustomerTable() {
        String query = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES \n" +
                "('John Doe',1,1,'2019-01-01 00:00:00','test','test'),\n" +
                "('Alfred E Newman',2,1,'2019-01-01 00:00:00','test','test'),\n" +
                "('Ina Prufung',3,1,'2019-01-01 00:00:00','test','test');";
        int result = SqlHelper.getInstance().update(query);
        return result > 0;
    }

    /*public boolean populateUserTable() {
        String query = "INSERT INTO `user` VALUES \n" +
                "(1,'test','test',1,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
        int result = SqlHelper.getInstance().update(query);
        return result > 0;
    }*/

    public boolean populateAppointmentsTable() {
        String query = "SET FOREIGN_KEY_CHECKS=0;";
        int result = SqlHelper.getInstance().update(query);

        String query1 = "INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy) VALUES \n" +
                "('1','1','not needed','not needed','not needed','not needed','Presentation','not needed','2019-01-01 00:00:00','2019-01-01 00:00:00','2019-01-01 00:00:00','test','test'),\n" +
                "('2','1','not needed','not needed','not needed','not needed','Scrum','not needed','2019-01-01 00:00:00','2019-01-01 00:00:00','2019-01-01 00:00:00','test','test');";
        result = SqlHelper.getInstance().update(query1);
        return result > 0;
    }
}

