package output;

import constants.AccountType;

import constants.Notify;

import constants.Numbers;

import input.UserInput;

import java.util.ArrayList;

import java.util.List;

public final class UserExtended extends UserInput {
    private int tokensCount;
    private int numFreePremiumMovies;
    private ArrayList<MovieExtended> purchasedMovies;
    private ArrayList<MovieExtended> watchedMovies;
    private ArrayList<MovieExtended> likedMovies;
    private ArrayList<MovieExtended> ratedMovies;
    private List<Notification> notifications;

    public UserExtended(final UserInput user) {
        super(user);
        tokensCount = 0;
        numFreePremiumMovies = Numbers.PREM_FREE_MOV.getValue();
        purchasedMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
        notifications = new ArrayList<>();
    }

    public UserExtended(final UserExtended user) {
        super(user);
        tokensCount = user.getTokensCount();
        numFreePremiumMovies = user.getNumFreePremiumMovies();
        purchasedMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
        notifications = new ArrayList<>();

        for (MovieExtended movie : user.getPurchasedMovies()) {
            purchasedMovies.add(new MovieExtended(movie));
        }

        for (MovieExtended movie : user.getWatchedMovies()) {
            watchedMovies.add(new MovieExtended(movie));
        }

        for (MovieExtended movie : user.getLikedMovies()) {
            likedMovies.add(new MovieExtended(movie));
        }

        for (MovieExtended movie : user.getRatedMovies()) {
            ratedMovies.add(new MovieExtended(movie));
        }

        for (Notification notification : user.getNotifications()) {
            notifications.add(new Notification(notification));
        }
    }

    /**
     * Adds a new notification that a movie has been added to the database
     */
    public void update(final MovieExtended addedMovie) {
        if (!addedMovie.getCountriesBanned().contains(this.getCredentials().getCountry())) {
            notifications.add(new Notification(addedMovie.getName(), Notify.ADD.getNotify()));
        }
    }

    /**
     * Adds a new notification that a movie has been removed from the database
     */
    public void update(final String deletedMovie) {
        for (MovieExtended movie : purchasedMovies) {
            if (movie.getName().equals(deletedMovie)) {
                purchasedMovies.remove(movie);
                break;
            }
        }

        notifications.add(new Notification(deletedMovie, Notify.DELETE.getNotify()));

        if (this.getCredentials().getAccountType().equals(AccountType.PREM.getType())) {
            numFreePremiumMovies++;
        } else {
            tokensCount += Numbers.MOVIE_COST.getValue();
        }

        for (MovieExtended movie : watchedMovies) {
            if (movie.getName().equals(deletedMovie)) {
                watchedMovies.remove(movie);
                break;
            }
        }

        for (MovieExtended movie : likedMovies) {
            if (movie.getName().equals(deletedMovie)) {
                likedMovies.remove(movie);
                break;
            }
        }

        for (MovieExtended movie : ratedMovies) {
            if (movie.getName().equals(deletedMovie)) {
                ratedMovies.remove(movie);
                break;
            }
        }
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<MovieExtended> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<MovieExtended> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<MovieExtended> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<MovieExtended> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<MovieExtended> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<MovieExtended> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<MovieExtended> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<MovieExtended> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(final List<Notification> notifications) {
        this.notifications = notifications;
    }
}
