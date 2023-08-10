package com.example.movies.security;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class SessionManager {
    private static final String SESSION = "localsession";

    private static String SESSION_ID;

    public static void savePersistent(String value) {
        Preferences prefs = Preferences.userNodeForPackage(SessionManager.class);
        prefs.put(SESSION, value);
    }

    public static String getPersistent() {
        Preferences prefs = Preferences.userNodeForPackage(SessionManager.class);
        return prefs.get(SESSION, null);
    }

    public static void saveSession_id(String id, boolean keepSession){
        if(keepSession){
            savePersistent(id);
        } else {
            SESSION_ID = id;
        }

    }

    public static String getSession_id(){
        return SESSION_ID;
    }

    public static void clearSession() throws BackingStoreException {
        SESSION_ID = null;
        Preferences prefs = Preferences.userNodeForPackage(SessionManager.class);
        prefs.clear();
    }
}
