package output;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

public final class OutputMessage {
    private String error;
    private final List<MovieExtended> currentMoviesList;
    private UserExtended currentUser;

    public OutputMessage() {
        error = null;
        currentMoviesList = Collections.synchronizedList(new ArrayList<>());
        currentUser = null;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public List<MovieExtended> getCurrentMoviesList() {
        return currentMoviesList;
    }

    /**
     * Deep copy setter for currentMoviesList.
     */
    public void setCurrentMoviesList(final List<MovieExtended> currentMoviesList) {
        this.currentMoviesList.addAll(currentMoviesList);
    }

    public UserExtended getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final UserExtended currentUser) {
        this.currentUser = currentUser;
    }
}
