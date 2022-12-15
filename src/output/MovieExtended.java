package output;

import input.MovieInput;

import java.math.BigDecimal;

import java.math.RoundingMode;

public final class MovieExtended extends MovieInput {
    private int numLikes;
    private Double rating;
    private int numRatings;

    public MovieExtended(final MovieInput movie) {
        super(movie);
        numLikes = 0;
        rating = 0.00;
        numRatings = 0;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public Double getRating() {
        return rating;
    }

    /**
     * Sets the rating of the movie to 2 decimal places.
     *
     * @param rating the average rating of the movie
     */
    public void setRating(final Double rating) {
        BigDecimal bd = new BigDecimal(rating).setScale(2, RoundingMode.CEILING);
        this.rating = bd.doubleValue();
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }
}
