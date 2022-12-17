package input;

public class UserInput {
    private Credentials credentials;

    public UserInput() {
    }

    public UserInput(final UserInput user) {
        this.credentials = new Credentials(user.credentials);
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
