package constants;

public enum Numbers {
    MOVIE_COST(2),
    PREM_ACC_COST(10),
    PREM_FREE_MOV(15),
    MIN_RATE(1),
    MAX_RATE(5);

    private final int value;

    Numbers(final int number) {
        this.value = number;
    }

    public int getValue() {
        return value;
    }
}
