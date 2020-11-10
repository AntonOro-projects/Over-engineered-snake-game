package database;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

class DatabaseHelper {
    Connection conn;
    PreparedStatement insertUserStmt;
    PreparedStatement selectUserStmt;
    PreparedStatement selectUserPwStmt;
    PreparedStatement updateUserPwStmt;
    PreparedStatement deleteUserStmt;
    PreparedStatement insertHighscoreStmt;
    PreparedStatement selectUserHighscoreStmt;
    PreparedStatement selectTopHighscoresStmt;
    PreparedStatement dropDatabaseStmt;

    DatabaseHelper(String database) {
        initializeDB(database);
        createConnection(database);
        prepareStatements();
    }

    JSONObject insertUserDB(String username, String password) {
        try {
            insertUserStmt.setString(1, username);
            insertUserStmt.setString(2, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String successMessage = "Successfully inserted user " + username;
        String failureMessage = "The user already exists, try another username";
        return executeQuery(insertUserStmt, successMessage, failureMessage, false);
    }

    JSONObject getUserDB(String username) {
        try {
            selectUserStmt.setString(1, username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String successMessage = "Successfully retrieved user " + username;
        String failureMessage = "Failed to get user " + username + ", user does not exist";
        return executeQuery(selectUserStmt, successMessage, failureMessage, true);
    }

    JSONObject verifyUserExistsDB(String username) {
        try {
            selectUserPwStmt.setString(1, username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String successMessage = "The username and password is correct";
        String failureMessage = "The username does not exist";
        return executeQuery(selectUserPwStmt, successMessage, failureMessage, true);
    }

    JSONObject updateUserPasswordDB(String username, String newPassword) {
        try {
            updateUserPwStmt.setString(1, newPassword);
            updateUserPwStmt.setString(2, username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String successMessage = "Successfully updated password for user " + username;
        String failureMessage = "Failed to update password for user " + username;
        return executeQuery(updateUserPwStmt, successMessage, failureMessage,false);
    }

    JSONObject deleteUserDB(String username) {
        try {
            deleteUserStmt.setString(1, username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String successMessage = "Successfully deleted user " + username;
        String failureMessage = "Failed to delete user " + username + ", user does not exist";
        return executeQuery(deleteUserStmt, successMessage, failureMessage, false);
    }

    JSONObject insertScoreDB(String username, int score) {
        try {
            insertHighscoreStmt.setString(1, username);
            insertHighscoreStmt.setInt(2, score);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String successMessage = "Successfully inserted highscore for user " + username;
        String failureMessage = "The username is incorrect";
        return executeQuery(insertHighscoreStmt, successMessage, failureMessage, false);
    }

    JSONObject getHighscoreDB(String username, int daysBack, int amountOfScores) {
        String successMessage = "Successfully retrieved " + amountOfScores + " highscores";
        String failureMessage = "The username is incorrect";

        try {
            selectTopHighscoresStmt.setInt(1, daysBack);
            selectTopHighscoresStmt.setInt(2, amountOfScores);
            selectUserHighscoreStmt.setString(1, username);
            selectUserHighscoreStmt.setInt(2, amountOfScores);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (!username.equals("")) {
            return executeQuery(selectUserHighscoreStmt, successMessage, failureMessage, true);
        } else {
            return executeQuery(selectTopHighscoresStmt, successMessage, failureMessage, true);
        }
    }

    JSONObject clearDB() {
        String successMessage = "Successfully cleared database ";
        String failureMessage = "Failed to drop database";
        return executeQuery(dropDatabaseStmt, successMessage, failureMessage, false);
    }

    private void createConnection(String database) {
        conn = null;
        try {
            String url1 = "jdbc:mysql://localhost:3306/" + database + "?&serverTimezone=CET";
            String user = "root";
            String password = "snekboys";

            conn = DriverManager.getConnection(url1, user, password);

        } catch (SQLException ex) {
            System.out.println("An error occurred");
            ex.printStackTrace();
        }
    }


    private JSONObject executeQuery(PreparedStatement stmt, String successMessage, String failureMessage, boolean expectingOutput) {
        ResultSet rs = null;
        ArrayList<JSONObject> dataList = new ArrayList<>();
        JSONObject returnMessage = new JSONObject();

        try {
            if (expectingOutput) {
                rs = stmt.executeQuery();
                int numCols = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    JSONObject data = new JSONObject();
                    for (int i = 1; i < numCols + 1; i++) {
                        data.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                    }
                    dataList.add(data);
                }
                if (dataList.isEmpty()) {
                    returnMessage.put("success", false);
                    returnMessage.put("message", "There were no results found");
                }
                else {
                    returnMessage.put("success", true);
                    returnMessage.put("message", successMessage);
                }
            } else {
                stmt.executeUpdate();
                returnMessage.put("success", true);
                returnMessage.put("message", successMessage);

            }
        }
        catch (SQLException ex){
            returnMessage.put("success", false);
            returnMessage.put("message", failureMessage);
            returnMessage.put("data", dataList);
            return returnMessage;
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        returnMessage.put("data", dataList);
        return returnMessage;
    }

    private void initializeDB(String database) {
        createConnection("");
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            BufferedReader reader;
            try {
                if (database.equals("snekdb")) {
                    reader = new BufferedReader(new FileReader(
                            "src/main/java/database/Schema.sql"));
                } else {
                    reader = new BufferedReader(new FileReader(
                            "src/test/java/TestSchema.sql"));
                }
                String line = reader.readLine();
                while (line != null) {
                    stmt.executeUpdate(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void prepareStatements() {
        try {
            insertUserStmt = conn.prepareStatement("INSERT INTO USERS(username, password) VALUES (?, ?)");
            selectUserStmt = conn.prepareStatement("SELECT USERNAME FROM USERS WHERE USERNAME = ?");
            selectUserPwStmt = conn.prepareStatement("SELECT USERNAME, PASSWORD FROM USERS WHERE USERNAME = ?");
            updateUserPwStmt = conn.prepareStatement("UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?");
            deleteUserStmt = conn.prepareStatement("DELETE FROM USERS WHERE USERNAME = ?");
            insertHighscoreStmt = conn.prepareStatement("INSERT INTO HIGHSCORE (USERNAME, SCORE) VALUES (?, ?)");
            selectUserHighscoreStmt = conn.prepareStatement("SELECT USERNAME, SCORE, ADD_TIMESTAMP FROM HIGHSCORE WHERE USERNAME = ? ORDER BY SCORE DESC LIMIT ?");
            selectTopHighscoresStmt = conn.prepareStatement("SELECT USERNAME, SCORE, ADD_TIMESTAMP FROM HIGHSCORE WHERE ADD_TIMESTAMP >= DATE_SUB(NOW(),INTERVAL ? DAY) ORDER BY SCORE DESC LIMIT ?");
            dropDatabaseStmt = conn.prepareStatement("DROP DATABASE snekdb_test");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}
