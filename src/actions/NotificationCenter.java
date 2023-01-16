package actions;

import output.MovieExtended;

import output.UserExtended;

import java.util.ArrayList;

import java.util.List;

public final class NotificationCenter {
    private final List<UserExtended> notifiedUsers = new ArrayList<>();

    /**
     * Checks is already set to be notified
     */
    public boolean containsObserver(final UserExtended user) {
        return notifiedUsers.contains(user);
    }

    /**
     * Adds a new observer
     */
    public void addObserver(final UserExtended user) {
        notifiedUsers.add(user);
    }


    /**
     * Sends a notification to all observers that a new movie has been added
     */
    public void setNotifications(final MovieExtended movie) {
        for (UserExtended user : notifiedUsers) {
            user.update(movie);
        }
    }

    /**
     * Sends a notification to all observers that a movie they had purchased has been removed
     */
    public void setNotifications(final String movie) {
        for (UserExtended user : notifiedUsers) {
            user.update(movie);
        }
    }
}
