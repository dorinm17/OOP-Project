package constants;

public enum AccountType {
    STD("standard"),
    PREM("premium");

    private final String accountType;

    AccountType(final String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }
}
