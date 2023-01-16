package constants;

public enum Notify {
    ADD("ADD"),
    DELETE("DELETE"),
    RECOMMENDATION("Recommendation"),
    NO_RECOMMENDATION("No recommendation"),;

    private final String notify;

    Notify(final String notify) {
        this.notify = notify;
    }

    public String getNotify() {
        return notify;
    }
}
