package output;

public final class Notification {
    private String movieName;
    private String message;

    public Notification() {
        movieName = null;
        message = null;
    }

    public Notification(final Notification notification) {
        movieName = notification.getMovieName();
        message = notification.getMessage();
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
