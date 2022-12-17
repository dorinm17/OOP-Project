package constants;

public enum AccountType {
    STD("standard"),
    PREM("premium");

    private final String type;

    AccountType(final String accountType) {
        this.type = accountType;
    }

    public String getType() {
        return type;
    }
}
