package constants;

public enum Sort {
    DEC("decreasing"),
    INC("increasing");

    private final String sort;

    Sort(final String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }
}
