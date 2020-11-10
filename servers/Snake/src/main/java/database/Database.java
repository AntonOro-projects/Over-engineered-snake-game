package database;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

/**
 * Every function returns a JSONObject in the format:
 * {"success": boolean, "data": ArrayList<JSONObject>, "message" : String}
 */
public class Database {
    private final DatabaseHelper helper;

    /**
     * Valid inputs are either "snekdb" or "snekdb_test"
     * @param database
     */
    public Database(String database) {
        this.helper = new DatabaseHelper(database);
    }

    public JSONObject createUser(String username, String password) {
        if (!verifyUsername(username)) {
            return incorrectUsernameMessage();
        }

        return helper.insertUserDB(username, hashPassword(password));
    }

    public JSONObject getUser(String username) {
        if (!verifyUsername(username)) {
            return incorrectUsernameMessage();
        }

        return helper.getUserDB(username);
    }


    public JSONObject removeUser(String username, String password) {
        if (!verifyUsername(username)) {
            return incorrectUsernameMessage();
        }
        JSONObject verifiedCredentials = verifyCredentials(username, password);

        if (verifiedCredentials.get("success").equals(true)) {
            return helper.deleteUserDB(username);
        }

        return verifiedCredentials;
    }

    public JSONObject verifyUser(String username, String password) {
        if (!verifyUsername(username)) {
            return incorrectUsernameMessage();
        }

        return verifyCredentials(username, password);
    }

    public JSONObject updatePassword(String username, String oldPassword, String newPassword) {
        if (!verifyUsername(username)) {
            return incorrectUsernameMessage();
        }
        JSONObject verifiedCredentials = verifyCredentials(username, oldPassword);

        if (verifiedCredentials.get("success").equals(true)) {
            return helper.updateUserPasswordDB(username, hashPassword(newPassword));
        }

        return verifiedCredentials;
    }

    public JSONObject setUserScore(String username, int score) {
        if (!verifyUsername(username)) {
            return incorrectUsernameMessage();
        }

        return helper.insertScoreDB(username, score);
    }

    public JSONObject getUserHighscore(String username, int amountOfScores) {
        if (!verifyUsername(username)) {
            return incorrectUsernameMessage();
        }

        return helper.getHighscoreDB(username, 0, amountOfScores);
    }

    public JSONObject getTopHighscores(int daysBack, int amountOfScores) {
        return helper.getHighscoreDB("", daysBack, amountOfScores);
    }

    public void emptyDatabase() {
        helper.clearDB();
    }

    private boolean verifyUsername(String username) {
        if(username.length() <= 3) return false;
        return true;
    }

    private String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(12);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
    }

    private JSONObject verifyCredentials(String username, String password) {
        JSONObject queryResult = helper.verifyUserExistsDB(username);
        if (queryResult.get("success").equals(false)) {
            return queryResult;
        }
        JSONArray data = (JSONArray) queryResult.get("data");
        JSONObject dataJson = (JSONObject) data.get(0);
        String hashedPassword = (String) dataJson.get("password");
        boolean verified = checkPassword(password, hashedPassword);
        if (!verified) {
            queryResult.put("message", "Incorrect password");
        }
        queryResult.put("data", new ArrayList<JSONObject>());
        queryResult.put("success", verified);

        return queryResult;
    }

    private boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }

    private JSONObject incorrectUsernameMessage() {
        JSONObject returnMessage = new JSONObject();
        returnMessage.put("success", false);
        returnMessage.put("message", "The username is incorrect");
        returnMessage.put("data", new JSONObject());
        return returnMessage;
    }
}
