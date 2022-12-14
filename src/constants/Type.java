package constants;

public enum Type {
    ON("on page"),
    CHANGE("change page");

    private final String type;

    Type(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
