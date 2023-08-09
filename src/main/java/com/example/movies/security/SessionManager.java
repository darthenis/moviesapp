package com.example.movies.security;

import java.util.prefs.Preferences;

public class SessionManager {
    private static final String SESSION = "localsession";

    private static String SESSION_ID;

    public static void saveToken(String value) {
        Preferences prefs = Preferences.userNodeForPackage(SessionManager.class);
        prefs.put(SESSION, value);
    }

    public static String loadToken() {
        Preferences prefs = Preferences.userNodeForPackage(SessionManager.class);
        return prefs.get(SESSION, null);
    }

    public static void setSession_id(String id){
        SESSION_ID = id;
    }

    public static String getSession_id(){
        return SESSION_ID;
    }
}
