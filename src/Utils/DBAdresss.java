package Utils;

public class DBAdresss {

    public boolean addToAddressTable(String address, String postalCode, String phoneNumber){
        String query = "INSERT INTO `address` VALUES \n" +
                "(4," +"'" + address + "'," + "'',1,'" + postalCode + "'," + "'" + phoneNumber + "'," + "'2019-01-01 00:00:00','admin','2019-01-01 00:00:00','admin');";
        int result = SqlHelper.getInstance().update(query);
        return result > 0;

    }
}
