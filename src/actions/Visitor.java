package actions;

import output.OutputMessage;

public interface Visitor {
    /**
     * @return error status, current movie list and current user
     */
    OutputMessage getOutputMessage();

    /**
     * Changes the current page
     */
    void visit(ChangePage changePage);

    /**
     * Logins into an existing account and enters the authenticated homepage
     */
    void visit(Login login);

    /**
     * Registers a non-existing account into the user database
     */
    void visit(Register register);

    /**
     * Searches for movies that start with the given string
     */
    void visit(Search search);

    /**
     * Sorts the movies by rating and duration
     */
    void visit(Filter addFavorite);
}
