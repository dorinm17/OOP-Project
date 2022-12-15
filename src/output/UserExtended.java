package output;

import constants.Numbers;
import input.UserInput;

import java.util.ArrayList;

public final class UserExtended extends UserInput {
    private int tokensCount;
    private int numFreePremiumMovies;
    private ArrayList<MovieExtended> purchasedMovies;
    private ArrayList<MovieExtended> watchedMovies;
    private ArrayList<MovieExtended> likedMovies;
    private ArrayList<MovieExtended> ratedMovies;

    public UserExtended(final UserInput user) {
        super(user);
        tokensCount = 0;
        numFreePremiumMovies = Numbers.PREM_FREE_MOV.getValue();
        purchasedMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
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
}
