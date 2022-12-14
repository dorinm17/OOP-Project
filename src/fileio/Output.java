package fileio;

import java.util.ArrayList;

public final class Output {
    private String error;
    private ArrayList<MovieExtended> currentMovieList;
    private UserExtended currentUser;

    public Output() {
        error = null;
        currentMovieList = new ArrayList<>();
        currentUser = null;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public ArrayList<MovieExtended> getCurrentMovieList() {
        return currentMovieList;
    }

    public void setCurrentMovieList(final ArrayList<MovieExtended> currentMovieList) {
        this.currentMovieList = currentMovieList;
    }

    public UserExtended getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final UserExtended currentUser) {
        this.currentUser = currentUser;
    }
}
