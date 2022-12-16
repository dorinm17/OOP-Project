package actions;

import constants.Page;

import constants.Error;

import constants.Sort;

import input.ContainsCriteria;
import input.SortCriteria;

import input.UserInput;

import output.MovieExtended;

import output.Output;

import output.OutputMessage;

import output.UserExtended;

import java.util.ArrayList;

public final class ActionVisitor implements Visitor {
    @Override
    public OutputMessage getOutputMessage() {
        return outputMessage;
    }

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
                    break;
                }
            }

            outputMessage = new OutputMessage();

            if (auxMovie == null) {
                outputMessage.setError(Error.ERR.getError());
                return true;
            }

            output.getCurrentMoviesList().clear();
            output.getCurrentMoviesList().add(auxMovie);
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

        if (output.getCurrentUser() == null) {
            outputMessage.setError(Error.ERR.getError());
            output.setCurrentPage(Page.UNAUTHENTICATED.getPage());
        }
    }

    @Override
    public void visit(final Register register) {
        outputMessage = new OutputMessage();

        if (!Page.REG.getPage().equals(output.getCurrentPage())) {
            outputMessage.setError(Error.ERR.getError());
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
    public void visit(final Search search) {
        outputMessage = new OutputMessage();

        if (!Page.MOVIES.getPage().equals(output.getCurrentPage())) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        ArrayList<MovieExtended> auxMovies = new ArrayList<MovieExtended>();

        for (MovieExtended movie : output.getCurrentMoviesList()) {
            if (movie.getName().startsWith(search.getStartsWith())) {
                auxMovies.add(movie);
            }
        }

        output.setCurrentMoviesList(auxMovies);
        outputMessage.setCurrentUser(output.getCurrentUser());
        outputMessage.setCurrentMoviesList(output.getCurrentMoviesList());
    }

    @Override
    public void visit(final Filter filter) {
        outputMessage = new OutputMessage();

        if (!Page.MOVIES.getPage().equals(output.getCurrentPage())) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        containsCurrentMoviesList(filter);
        sortCurrentMoviesList(filter);

        outputMessage.setCurrentUser(output.getCurrentUser());
        outputMessage.setCurrentMoviesList(output.getCurrentMoviesList());
    }

    private void containsCurrentMoviesList(final Filter filter) {
        ContainsCriteria containsCriteria = filter.getFilters().getContains();

        if (containsCriteria == null) {
            return;
        }

        ArrayList<MovieExtended> auxMovies = new ArrayList<MovieExtended>();
        for (MovieExtended movie : output.getCurrentMoviesList()) {
            if (containsCriteria.getActors() != null) {
                for (String actor : containsCriteria.getActors()) {
                    if (movie.getActors().contains(actor)) {
                        auxMovies.add(movie);
                        break;
                    }
                }
            }

            // genre != null
            for (String genre : containsCriteria.getGenre()) {
                if (movie.getGenres().contains(genre)
                        && !auxMovies.contains(movie)) {
                    auxMovies.add(movie);
                    break;
                }
            }
        }

        output.setCurrentMoviesList(auxMovies);
    }

    private void sortCurrentMoviesList(final Filter filter) {
        SortCriteria sortCriteria = filter.getFilters().getSort();

        if (sortCriteria == null) {
            return;
        }

        String getDuration = sortCriteria.getDuration();
        String getRating = sortCriteria.getRating();

        if (getDuration != null && getRating != null) {
            output.getCurrentMoviesList().sort((MovieExtended m1, MovieExtended m2) -> {
                if (getDuration.equals(Sort.INC.getSort())) {
                    if (m1.getDuration() == m2.getDuration()) {
                        return Double.compare(m1.getRating(), m2.getRating());
                    }
                    return m1.getDuration() - m2.getDuration();
                }
                // duration: decreasing
                if (m1.getDuration() == m2.getDuration()) {
                    return Double.compare(m2.getRating(), m1.getRating());
                }
                return m2.getDuration() - m1.getDuration();
            });
            return;
        }

        if (getDuration != null) {
            output.getCurrentMoviesList().sort((MovieExtended m1, MovieExtended m2) -> {
                if (getDuration.equals(Sort.INC.getSort())) {
                    return m1.getDuration() - m2.getDuration();
                }
                return m2.getDuration() - m1.getDuration();
            });
            return;
        }

        // rating != null
        output.getCurrentMoviesList().sort((MovieExtended m1, MovieExtended m2) -> {
            if (getRating.equals(Sort.INC.getSort())) {
                return Double.compare(m1.getRating(), m2.getRating());
            }
            return Double.compare(m2.getRating(), m1.getRating());
        });
    }
}
