package com.slackers.inc.database;

import com.slackers.inc.database.entities.User;

import java.sql.SQLException;

/**
 * Created by Matt on 3/28/2017.
 */
public class LoginVerification {

    DerbyConnection db;

    public LoginVerification() throws SQLException {
        db = DerbyConnection.getInstance();
    }

    // returns true if the credentials are valid, and false otherwise
    public boolean verifyCredentials(String email, String password){
        User user = new User().setEmailAddress(email);

        return false;
    }


}
