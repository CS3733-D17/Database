package com.slackers.inc.database;

import com.slackers.inc.database.entities.User;

import java.sql.SQLException;

/**
 * Created by Matt on 3/28/2017.
 */
public class LoginVerification {

    private DerbyConnection db;

    public LoginVerification() throws SQLException {
        db = DerbyConnection.getInstance();
    }

    // returns true if the credentials are valid, and false otherwise
    public boolean verifyCredentials(String email, String password) throws SQLException {
        User user = new User().setEmailAddress(email);
        try {
            db.getEntity(user, "EmailAddress");
        } catch (SQLException e) {
            System.out.println("Trouble accessing database for login verification");
            throw e;
        }

        return password.equals(user.getPassword());
    }


}
