package com.doge.dogeapp.Helpers;

import com.doge.dogeapp.Models.User;

public class Settings {

    private static User loggedInUser = null;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    //Set user only from null to object and from object to null
    public static void setLoggedInUser(User loggedInUser) {
        if ((loggedInUser == null && Settings.loggedInUser != null) || (loggedInUser != null && Settings.loggedInUser == null)) {
            Settings.loggedInUser = loggedInUser;
        }
    }
}
