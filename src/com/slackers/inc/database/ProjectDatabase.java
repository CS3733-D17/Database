/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slackers.inc.database;

import com.slackers.inc.database.entities.LabelApplication;
import com.slackers.inc.database.entities.User;

import java.sql.SQLException;

/**
 *
 * @author John Stegeman <j.stegeman@labyrinth-tech.com>
 */
public class ProjectDatabase{

    
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here

        DerbyConnection db = DerbyConnection.getInstance();
        LoginVerification verify = new LoginVerification();

        System.out.println("Bob is not in the database.");
        System.out.println("Bob can log in : "
                           + verify.verifyCredentials("bob@thebuilder.com", "build"));

        User bobTheBuilder = new User()
                .setEmailAddress("bob@thebuilder.com")
                .setPassword("build")
                .setFirstName("Bob")
                .setLastName("the Builder")
                .setBrandName("")
                .setIsManufacturer(true)
                .setPhysicalAddress("")
                .setPhoneNumber("")
                .setUserId(1);
        db.createEntity(bobTheBuilder);

        System.out.println("\nBob is now in the database.");
        System.out.println("Bob can log in : "
                           + verify.verifyCredentials("bob@thebuilder.com", "build"));

        for(int i = 0; i < 30; i++){
            db.writeEntity(getRandomApplication());
        }

        db.shutdownDb();
    }

    static int index = 1;

   public static LabelApplication getRandomApplication(){
       String beverageName = "Beer #" + index;
       String companyName = "Company #" + index;
       index++;
       return new LabelApplication(beverageName, beverageName);
   }

    
}
