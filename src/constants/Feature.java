package constants;

public enum Feature {
    REG("register"),
    LOGIN("login"),
    FILTER("filter"),
    SEARCH("search"),
    PURCHASE("purchase"),
    WATCH("watch"),
    RATE("rate"),
    LIKE("like"),
    BUY_TOK("buy tokens"),
    BUY_PREM_ACC("buy premium account");

    private final String feature;

    Feature(final String feature) {
        this.feature = feature;
    }

    public String getFeature() {
        return feature;
    }
}
