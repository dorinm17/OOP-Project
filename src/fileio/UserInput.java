package fileio;

public class UserInput {
    private Credentials credentials;

    public UserInput() {
    }

    public final Credentials getCredentials() {
        return credentials;
    }

    public final void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public final String toString() {
        return "UserInput{"
                + "credentials=" + credentials
                + '}';
    }
}
