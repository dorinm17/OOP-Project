package fileio;

public final class SortCriteria {
    private String rating;
    private String duration;

    public SortCriteria() {
    }

    public String getRating() {
        return rating;
    }

    public void setRating(final String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "SortCriteria{"
                + "rating='" + rating + '\''
                + ", duration='" + duration + '\''
                + '}';
    }
}
