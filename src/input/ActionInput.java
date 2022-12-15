package input;

public class ActionInput {
    private String type;
    private String page;
    private String feature;
    private Credentials credentials;
    private String startsWith;
    private FiltersInput filters;
    private String count;
    private String movie;
    private int rate;

    private ActionInput() {
    }

    public ActionInput(final ActionInput action) {
        this.type = action.getType();
        this.page = action.getPage();
        this.feature = action.getFeature();
        this.credentials = action.getCredentials();
        this.startsWith = action.getStartsWith();
        this.filters = action.getFilters();
        this.count = action.getCount();
        this.movie = action.getMovie();
        this.rate = action.getRate();
    }

    public final String getType() {
        return type;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final String getPage() {
        return page;
    }

    public final void setPage(final String page) {
        this.page = page;
    }

    public final String getFeature() {
        return feature;
    }

    public final void setFeature(final String feature) {
        this.feature = feature;
    }

    public final Credentials getCredentials() {
        return credentials;
    }

    public final void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public final String getStartsWith() {
        return startsWith;
    }

    public final void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    public final FiltersInput getFilters() {
        return filters;
    }

    public final void setFilters(final FiltersInput filters) {
        this.filters = filters;
    }

    public final String getCount() {
        return count;
    }

    public final void setCount(final String count) {
        this.count = count;
    }

    public final String getMovie() {
        return movie;
    }

    public final void setMovie(final String movie) {
        this.movie = movie;
    }

    public final int getRate() {
        return rate;
    }

    public final void setRate(final int rate) {
        this.rate = rate;
    }

    @Override
    public final String toString() {
        return "ActionInput{"
                + "type='" + type + '\''
                + ", page='" + page + '\''
                + ", feature='" + feature + '\''
                + ", credentials=" + credentials
                + ", startsWith='" + startsWith + '\''
                + ", filters=" + filters
                + ", count='" + count + '\''
                + ", movie='" + movie + '\''
                + ", rate=" + rate
                + '}';
    }
}
