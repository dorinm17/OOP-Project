package constants;

public enum Error {
    ERR("Error");

    private final String error;

    Error(final String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
