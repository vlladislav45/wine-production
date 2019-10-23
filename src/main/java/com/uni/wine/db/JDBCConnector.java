package com.uni.wine.db;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;


public class JDBCConnector {
    //    private static final Logger LOGGER = Logger.getLogger(JDBCConnector.class);
    private Connection conn;

    public JDBCConnector(String jdbcDriver,
                         String dbUrl,
                         String user,
                         String password) throws SQLException, ClassNotFoundException {

        Class.forName(jdbcDriver);

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("ssl", "true");
        conn = DriverManager.getConnection(dbUrl, props);

    }

    //TODO: move this logic outside of this class
    public void initTables() {
        try (Statement stmt = conn.createStatement()) {
            String createUserRolesQuery =
                    "CREATE TABLE IF NOT EXISTS USER_ROLES (" +
                            "id_role INT PRIMARY KEY AUTO_INCREMENT, " +
                            "user_role VARCHAR(15) NOT NULL UNIQUE )";

            String createVarietyQuery =
                    "CREATE TABLE IF NOT EXISTS VARIETIES (" +
                            "id_variety INT PRIMARY KEY AUTO_INCREMENT, " +
                            "variety_name VARCHAR(30) NOT NULL UNIQUE )";

            String createBottlesQuery =
                    "CREATE TABLE IF NOT EXISTS BOTTLES (" +
                            "bottle_volume INT PRIMARY KEY , " +
                            "bottle_quantity INT UNSIGNED NOT NULL )";

            String createWineTypesQuery =
                    "CREATE TABLE IF NOT EXISTS WINE_TYPES (" +
                            "id_type INT PRIMARY KEY AUTO_INCREMENT, " +
                            "type_name VARCHAR(30) NOT NULL UNIQUE )";

            String createUsersQuery =
                    "CREATE TABLE IF NOT EXISTS USERS(" +
                            "id_user INT PRIMARY KEY AUTO_INCREMENT," +
                            "username VARCHAR(30) NOT NULL UNIQUE ," +
                            "user_pass VARCHAR(30) NOT NULL, " +
                            "id_role INT NOT NULL," +
                            "FOREIGN KEY (id_role) REFERENCES USER_ROLES(id_role))";

            String createGrapesQuery =
                    "CREATE TABLE IF NOT EXISTS GRAPES (" +
                            "id_grape INT PRIMARY KEY AUTO_INCREMENT, " +
                            "id_variety INT NOT NULL, " +
                            "grape_quantity FLOAT(6,2) NOT NULL, " +
                            "id_user INT NOT NULL," +
                            "FOREIGN KEY(id_variety) REFERENCES VARIETIES(id_variety)," +
                            "FOREIGN KEY(id_user) REFERENCES USERS(id_user))";

            String createWinesQuery =
                    "CREATE TABLE IF NOT EXISTS WINES(" +
                            "id_wine INT PRIMARY KEY AUTO_INCREMENT," +
                            "wine_name VARCHAR(30) NOT NULL, " +
                            "id_type INT NOT NULL," +
                            "id_grape INT NOT NULL," +
                            "FOREIGN KEY(id_grape) REFERENCES GRAPES(id_grape)," +
                            "FOREIGN KEY(id_type) REFERENCES WINE_TYPES(id_type))";

            String createBottledWineQuery =
                    "CREATE TABLE IF NOT EXISTS BOTTLED_WINE(" +
                            "id_wine INT NOT NULL," +
                            "bottle_type INT NOT NULL," +
                            "id_user INT NOT NULL, " +
                            "quantity_bottled INT NOT NULL ," +
                            "FOREIGN KEY(id_wine) REFERENCES WINES(id_wine),"+
                            "FOREIGN KEY(bottle_type) REFERENCES BOTTLES(bottle_volume),"+
                            "FOREIGN KEY(id_user) REFERENCES USERS(id_user))";


            stmt.executeUpdate(createUserRolesQuery);
            System.out.println("CREATED USER ROLES SUCCESSFULLY");

            stmt.executeUpdate(createWineTypesQuery);
            System.out.println("CREATED WINE TYPES TABLE SUCCESSFULLY");

            stmt.executeUpdate(createVarietyQuery);
            System.out.println("CREATED VARIETY TABLE SUCCESSFULLY");

            stmt.executeUpdate(createBottlesQuery);
            System.out.println("CREATE TABLE BOTTLES SUCCESSFULLY");

            stmt.executeUpdate(createUsersQuery);
            System.out.println("CREATED USERS SUCCESSFULLY");

            stmt.executeUpdate(createGrapesQuery);
            System.out.println("CREATE TABLE GRAPES SUCCESSFULLY");

            stmt.executeUpdate(createWinesQuery);
            System.out.println("CREATED WINES SUCCESSFULLY");

            stmt.executeUpdate(createBottledWineQuery);
            System.out.println("CREATE TABLE BOTTLED WINE SUCCESSFULLY");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("FAILED TO CREATE TABLES ");
        }

    }

    /**
     * example INSERT INTO users(username, password) VALUES(?,?)
     * wildcard params are the params for the query ("?")
     *
     * @param query          the query
     * @param wildcardParams the params for the wildcard
     */
    public void executeQuery(String query, Object... wildcardParams) {
        //Intellij suggested naming 'ignored'
        try (Statement ignored = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 0; i < wildcardParams.length; i++) {
                /*
                 For some reason LocalDateTime
                 is not accepted for {@link PreparedStatement#setObject(int, Object)}
                 */
                if (wildcardParams[i] instanceof LocalDateTime)
                    pstmt.setTimestamp(i + 1, Timestamp.valueOf((LocalDateTime) wildcardParams[i]));
                else
                    pstmt.setObject(i + 1, wildcardParams[i]);
            }

            pstmt.executeUpdate();
//            LOGGER.info("Successfully executed query: " + pstmt);
        } catch (SQLException e) {
            e.printStackTrace();
//            LOGGER.error("Failed to executeUpdate query: " + query +
//                    "With values " +
//                    Arrays.toString(wildcardParams), e);
        }
    }

    /**
     * Executes a SELECT query with multiple results
     * every entity is represented as Map<String, Object>
     * where String is the column name, Object is the column value
     * if there are no entities from the result return empty List
     *
     * @param query the query
     * @return list of entities represented as described above
     */
    public List<Map<String, Object>> executeQueryWithMultipleResult(String query) {
        List<Map<String, Object>> objectsList = new ArrayList<>();
        //Intellij suggested naming 'ignored'
        try (Statement stmt = conn.createStatement();
             PreparedStatement ignored = conn.prepareStatement(query)) {
            ResultSet r = stmt.executeQuery(query);
            objectsList = parseResultSetToMap(r);
//            LOGGER.info("Successfully fetched query: " + query);
        } catch (SQLException e) {
//            LOGGER.error("Failed to execute query: " + query, e);
        }
        return objectsList;
    }

    /**
     * Get count of all rows of given table
     *
     * @param tableName   the table name
     * @param whereClause where clause for the count query (optional)
     * @return the number of rows
     */
    public int getCount(String tableName, String whereClause) {
        String query = "SELECT COUNT(*) FROM " + tableName +
                (whereClause == null ? "" : " " + whereClause);
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
//            LOGGER.error("Error executing query " + query, e);
        }
        return -1;
    }

    /**
     * execute a SELECT query where the result is only 1 entity
     *
     * @param query the query
     * @return a key - value pair -> column name - column value, empty map if no entity is found
     */
    public Map<String, Object> executeQueryWithSingleResult(String query) {
        Map<String, Object> resultColumnsMap = new HashMap<>();

        try (Statement stmt = conn.createStatement();
             PreparedStatement ignored = conn.prepareStatement(query)) {
            ResultSet r = stmt.executeQuery(query);
            //Since we are sure it will be only 1 entity, and parseResultSetToMap returns List<Map>
            //we get the first element of the List
            //if the result returned no values, it will throw IndexOutOfBoundException
            //and will return empty Map
            resultColumnsMap = parseResultSetToMap(r).get(0);
            System.out.println("Successfully fetched query: " + query);
            return resultColumnsMap;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException iob) {
            //Thrown when #parseResultSetToMap returns empty list ( ResultSet was empty)
            System.out.println("No results found for query " + query);
//            LOGGER.info("No results found for query: " + query);
        }

        return resultColumnsMap;
    }

    private List<Map<String, Object>> parseResultSetToMap(ResultSet r) throws SQLException {
        ResultSetMetaData metadata = r.getMetaData();
        int columnCount = metadata.getColumnCount();

        List<Map<String, Object>> entitiesList = new ArrayList<>();

        int index = 0;
        //For each entity (r.next) iterate all its columns
        //and put them in a map where key is column name
        //and value is column value
        while (r.next()) {
            entitiesList.add(new HashMap<>());

            for (int i = 1; i <= columnCount; i++) {
                String col = metadata.getColumnName(i);
                Object value = r.getObject(col);
                entitiesList.get(index).put(col, value);
            }
            index++;
        }
        return entitiesList;
    }
}
