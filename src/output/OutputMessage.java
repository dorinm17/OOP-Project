package output;

import java.util.ArrayList;

public final class OutputMessage {
    private String error;
    private ArrayList<MovieExtended> currentMoviesList;
    private UserExtended currentUser;

    public OutputMessage() {
        error = null;
        currentMoviesList = new ArrayList<>();
        currentUser = null;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public ArrayList<MovieExtended> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(final ArrayList<MovieExtended> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public UserExtended getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final UserExtended currentUser) {
        this.currentUser = currentUser;
    }
}
