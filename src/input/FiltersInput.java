package input;

public final class FiltersInput {
    private SortCriteria sort;
    private ContainsCriteria contains;

    public FiltersInput() {
    }

    public SortCriteria getSort() {
        return sort;
    }

    public void setSort(final SortCriteria sort) {
        this.sort = sort;
    }

    public ContainsCriteria getContains() {
        return contains;
    }

    public void setContains(final ContainsCriteria contains) {
        this.contains = contains;
    }

    @Override
    public String toString() {
        return "FiltersInput{"
                + "sort=" + sort
                + ", contains=" + contains
                + '}';
    }
}
