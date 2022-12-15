package actions;

import constants.Page;

import constants.Error;

import input.UserInput;

import output.MovieExtended;

import output.Output;

import output.OutputMessage;

import output.UserExtended;

public final class ActionVisitor implements Visitor {
    private OutputMessage outputMessage;
    private final Output output;

    public ActionVisitor() {
        outputMessage = null;
        output = Output.getInstance();
    }

    @Override
    public void visit(final ChangePage changePage) {
        if (Page.UNAUTHENTICATED.getPage().equals(output.getCurrentPage())
                || Page.REG.getPage().equals(output.getCurrentPage())
                || Page.LOGIN.getPage().equals(output.getCurrentPage())) {
            if (checkRegLogin(changePage)) {
                return;
            }

            outputMessage = new OutputMessage();
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        if (Page.AUTHENTICATED.getPage().equals(output.getCurrentPage())
                || Page.UPGRADES.getPage().equals(output.getCurrentPage())) {
            if (checkMovies(changePage)) {
                return;
            }
            if (checkUpgrades(changePage)) {
                return;
            }
            if (checkLogout(changePage)) {
                return;
            }

            outputMessage = new OutputMessage();
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        if (Page.MOVIES.getPage().equals(output.getCurrentPage())) {
            if (checkMovies(changePage)) {
                return;
            }
            if (checkSeeDetails(changePage)) {
                return;
            }
            if (checkLogout(changePage)) {
                return;
            }

            outputMessage = new OutputMessage();
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        // SEE DETAILS
        if (checkSeeDetails(changePage)) {
            return;
        }
        if (checkUpgrades(changePage)) {
            return;
        }
        if (checkMovies(changePage)) {
            return;
        }
        if (checkLogout(changePage)) {
            return;
        }

        outputMessage = new OutputMessage();
        outputMessage.setError(Error.ERR.getError());
    }

    private boolean checkSeeDetails(final ChangePage changePage) {
        if (Page.DETAILS.getPage().equals(changePage.getPage())) {
            output.setCurrentPage(changePage.getPage());
            MovieExtended auxMovie = null;

            for (MovieExtended movie : output.getCurrentMoviesList()) {
                if (movie.getName().equals(changePage.getMovie())) {
                    auxMovie = movie;
                }
            }

            output.getCurrentMoviesList().clear();
            output.getCurrentMoviesList().add(auxMovie);

            outputMessage = new OutputMessage();
            outputMessage.setCurrentUser(output.getCurrentUser());
            outputMessage.setCurrentMoviesList(output.getCurrentMoviesList());
            return true;
        }
        return false;
    }

    private boolean checkRegLogin(final ChangePage changePage) {
        if (Page.REG.getPage().equals(changePage.getPage())
                || Page.LOGIN.getPage().equals(changePage.getPage())) {
            output.setCurrentPage(changePage.getPage());
            return true;
        }
        return false;
    }

    private boolean checkUpgrades(final ChangePage changePage) {
        if (Page.UPGRADES.getPage().equals(changePage.getPage())) {
            output.setCurrentPage(changePage.getPage());
            output.getCurrentMoviesList().clear();
            return true;
        }
        return false;
    }

    private boolean checkMovies(final ChangePage changePage) {
        if (Page.MOVIES.getPage().equals(changePage.getPage())) {
            output.setCurrentPage(changePage.getPage());

            for (MovieExtended movie : output.getMovies()) {
                if (!movie.getCountriesBanned().contains(
                        output.getCurrentUser().getCredentials().getCountry())) {
                    output.getCurrentMoviesList().add(movie);
                }
            }

            outputMessage = new OutputMessage();
            outputMessage.setCurrentUser(output.getCurrentUser());
            outputMessage.setCurrentMoviesList(output.getCurrentMoviesList());
            return true;
        }
        return false;
    }

    private boolean checkLogout(final ChangePage changePage) {
        if (Page.LOGOUT.getPage().equals(changePage.getPage())) {
            output.setCurrentPage(Page.UNAUTHENTICATED.getPage());
            output.setCurrentUser(null);
            output.getCurrentMoviesList().clear();
            return true;
        }

        return false;
    }

    @Override
    public void visit(final Login login) {
        outputMessage = new OutputMessage();

        if (!Page.LOGIN.getPage().equals(output.getCurrentPage())) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        for (UserExtended user : output.getUsers()) {
            if (user.getCredentials().getName().equals(login.getCredentials().getName())
                    && user.getCredentials().getPassword().equals(
                    login.getCredentials().getPassword())) {
                output.setCurrentUser(user);
                output.setCurrentPage(Page.AUTHENTICATED.getPage());
                outputMessage.setCurrentUser(output.getCurrentUser());
                break;
            }
        }

        if (outputMessage.getCurrentUser() == null) {
            outputMessage.setError(Error.ERR.getError());
            output.setCurrentPage(Page.UNAUTHENTICATED.getPage());
        }
    }

    @Override
    public void visit(final Register register) {
        outputMessage = new OutputMessage();

        if (!Page.REG.getPage().equals(output.getCurrentPage())) {
            outputMessage.setError(Error.ERR.getError());
            outputMessage.setCurrentUser(output.getCurrentUser());
            outputMessage.setCurrentMoviesList(output.getCurrentMoviesList());
            return;
        }

        for (UserExtended user : output.getUsers()) {
            if (user.getCredentials().getName().equals(register.getCredentials().getName())) {
                outputMessage = new OutputMessage();
                outputMessage.setError(Error.ERR.getError());
                output.setCurrentPage(Page.UNAUTHENTICATED.getPage());
                return;
            }
        }

        UserInput auxUser = new UserInput();
        auxUser.setCredentials(register.getCredentials());
        UserExtended newUser = new UserExtended(auxUser);
        output.getUsers().add(newUser);
        output.setCurrentUser(newUser);
        output.setCurrentPage(Page.AUTHENTICATED.getPage());
        outputMessage.setCurrentUser(output.getCurrentUser());
    }

    @Override
    public OutputMessage getOutputMessage() {
        return outputMessage;
    }
}
