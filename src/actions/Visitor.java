package actions;

import output.OutputMessage;

public interface Visitor {
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
     * @return error status, current movie list and current user
     */
    OutputMessage getOutputMessage();
}
