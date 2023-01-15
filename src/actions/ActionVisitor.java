package actions;

import constants.Error;

import constants.Numbers;

import constants.Page;

import constants.Sort;

import constants.AccountType;

import input.ContainsCriteria;

import input.SortCriteria;

import input.UserInput;

import output.MovieExtended;

import output.Output;

import output.OutputMessage;

import output.UserExtended;

import java.util.ArrayList;

import java.util.LinkedHashSet;

public final class ActionVisitor implements Visitor {
    private OutputMessage outputMessage;
    private final Output output;

    public ActionVisitor() {
        outputMessage = null;
        output = Output.getInstance();
    }

    @Override
    public OutputMessage getOutputMessage() {
        return outputMessage;
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
            MovieExtended auxMovie = null;
            MovieExtended copyMovie = null;

            for (MovieExtended movie : output.getCurrentMoviesList()) {
                if (movie.getName().equals(changePage.getMovie())) {
                    auxMovie = movie;
                    copyMovie = new MovieExtended(movie);
                    break;
                }
            }

            outputMessage = new OutputMessage();

            if (auxMovie == null) {
                outputMessage.setError(Error.ERR.getError());

                output.getCurrentMoviesList().clear();
                for (MovieExtended movie : output.getMovies()) {
                    if (!movie.getCountriesBanned().contains(
                            output.getCurrentUser().getCredentials().getCountry())) {
                        output.getCurrentMoviesList().add(movie);
                    }
                }

                return true;
            }

            output.getCurrentMoviesList().clear();
            output.getCurrentMoviesList().add(auxMovie);
            output.setCurrentPage(changePage.getPage());

            outputMessage.getCurrentMoviesList().add(copyMovie);
            UserExtended copyUser = new UserExtended(output.getCurrentUser());
            outputMessage.setCurrentUser(copyUser);
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
            outputMessage = new OutputMessage();
            MovieExtended auxMovie;

            for (MovieExtended movie : output.getMovies()) {
                if (!movie.getCountriesBanned().contains(
                        output.getCurrentUser().getCredentials().getCountry())) {
                    auxMovie = new MovieExtended(movie);
                    outputMessage.getCurrentMoviesList().add(auxMovie);
                    output.getCurrentMoviesList().add(movie);
                }
            }

            UserExtended auxUser = new UserExtended(output.getCurrentUser());
            outputMessage.setCurrentUser(auxUser);
            output.setCurrentPage(changePage.getPage());
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
                UserExtended auxUser = new UserExtended(user);
                output.setCurrentUser(user);
                outputMessage.setCurrentUser(auxUser);
                output.setCurrentPage(Page.AUTHENTICATED.getPage());
                return;
            }
        }

        outputMessage.setError(Error.ERR.getError());
        output.setCurrentPage(Page.UNAUTHENTICATED.getPage());
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

        UserExtended copyUser = new UserExtended(newUser);
        outputMessage.setCurrentUser(copyUser);
    }

    @Override
    public void visit(final Search search) {
        outputMessage = new OutputMessage();

        if (!Page.MOVIES.getPage().equals(output.getCurrentPage())) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        for (MovieExtended movie : output.getCurrentMoviesList()) {
            if (movie.getName().startsWith(search.getStartsWith())) {
                outputMessage.getCurrentMoviesList().add(movie);
            }
        }

        UserExtended auxUser = new UserExtended(output.getCurrentUser());
        outputMessage.setCurrentUser(auxUser);
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

        UserExtended auxUser = new UserExtended(output.getCurrentUser());
        outputMessage.setCurrentUser(auxUser);
    }

    private void containsCurrentMoviesList(final Filter filter) {
        ContainsCriteria containsCriteria = filter.getFilters().getContains();

        if (containsCriteria == null) {
            return;
        }

        ArrayList<MovieExtended> auxMovies = new ArrayList<>(output.getCurrentMoviesList());

        if (containsCriteria.getActors() != null
                && containsCriteria.getGenre() != null) {
            for (MovieExtended movie : auxMovies) {
                for (String actor : containsCriteria.getActors()) {
                    if (!movie.getActors().contains(actor)) {
                        output.getCurrentMoviesList().remove(movie);
                        break;
                    }
                }

                for (String genre : containsCriteria.getGenre()) {
                    if (!movie.getGenres().contains(genre)
                            && output.getCurrentMoviesList().contains(movie)) {
                        output.getCurrentMoviesList().remove(movie);
                        break;
                    }
                }
            }
        } else if (containsCriteria.getActors() != null) {
            for (MovieExtended movie : auxMovies) {
                for (String actor : containsCriteria.getActors()) {
                    if (!movie.getActors().contains(actor)) {
                        output.getCurrentMoviesList().remove(movie);
                        break;
                    }
                }
            }
        } else if (containsCriteria.getGenre() != null) {
            for (MovieExtended movie : auxMovies) {
                for (String genre : containsCriteria.getGenre()) {
                    if (!movie.getGenres().contains(genre)) {
                        output.getCurrentMoviesList().remove(movie);
                        break;
                    }
                }
            }
        }

        LinkedHashSet<MovieExtended> hashSet = new LinkedHashSet<>(output.getCurrentMoviesList());
        for (MovieExtended movie : hashSet) {
            outputMessage.getCurrentMoviesList().add(new MovieExtended(movie));
        }
    }

    private void sortCurrentMoviesList(final Filter filter) {
        SortCriteria sortCriteria = filter.getFilters().getSort();

        if (sortCriteria == null) {
            return;
        }

        if (outputMessage.getCurrentMoviesList().isEmpty()) {
            outputMessage.setCurrentMoviesList(output.getCurrentMoviesList());
        }

        String getDuration = sortCriteria.getDuration();
        String getRating = sortCriteria.getRating();

        if (getDuration != null && getRating != null) {
            outputMessage.getCurrentMoviesList().sort((MovieExtended m1, MovieExtended m2) -> {
                if (getDuration.equals(Sort.INC.getSort())) {
                    if (m1.getDuration() == m2.getDuration()) {
                        return Double.compare(m1.getRating(), m2.getRating());
                    }
                    return m1.getDuration() - m2.getDuration();
                }
                // duration: decreasing
                if (m1.getDuration() == m2.getDuration()) {
                    return Double.compare(m1.getRating(), m2.getRating());
                }
                return m2.getDuration() - m1.getDuration();
            });
            return;
        }

        if (getDuration != null) {
            outputMessage.getCurrentMoviesList().sort((MovieExtended m1, MovieExtended m2) -> {
                if (getDuration.equals(Sort.INC.getSort())) {
                    return m1.getDuration() - m2.getDuration();
                }
                return m2.getDuration() - m1.getDuration();
            });
            return;
        }

        // rating != null
        outputMessage.getCurrentMoviesList().sort((MovieExtended m1, MovieExtended m2) -> {
            if (getRating.equals(Sort.INC.getSort())) {
                return Double.compare(m1.getRating(), m2.getRating());
            }
            return Double.compare(m2.getRating(), m1.getRating());
        });
    }

    @Override
    public void visit(final BuyTokens buyTokens) {
        if (!Page.UPGRADES.getPage().equals(output.getCurrentPage())) {
            outputMessage = new OutputMessage();
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        int tokensCount = output.getCurrentUser().getTokensCount();
        int newTokensCount = Integer.parseInt(buyTokens.getCount());
        int balance = Integer.parseInt(output.getCurrentUser().getCredentials().getBalance());

        if (newTokensCount > balance) {
            outputMessage = new OutputMessage();
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        output.getCurrentUser().setTokensCount(tokensCount + newTokensCount);
        output.getCurrentUser().getCredentials().setBalance(
                String.valueOf(balance - newTokensCount));
    }

    @Override
    public void visit(final BuyPremAcc buyPremAcc) {
        if (!Page.UPGRADES.getPage().equals(output.getCurrentPage())) {
            outputMessage = new OutputMessage();
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        int tokensCount = output.getCurrentUser().getTokensCount();

        if (tokensCount < Numbers.PREM_ACC_COST.getValue()) {
            outputMessage = new OutputMessage();
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        if (output.getCurrentUser().getCredentials().getAccountType().equals(
                AccountType.PREM.getType())) {
            outputMessage = new OutputMessage();
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        output.getCurrentUser().getCredentials().setAccountType(AccountType.PREM.getType());
        output.getCurrentUser().setTokensCount(tokensCount - Numbers.PREM_ACC_COST.getValue());
    }

    @Override
    public void visit(final Purchase purchase) {
        outputMessage = new OutputMessage();

        if (!Page.DETAILS.getPage().equals(output.getCurrentPage())) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        UserExtended currentUser = output.getCurrentUser();

        if (currentUser.getPurchasedMovies().contains(output.getCurrentMoviesList().get(0))) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        if (currentUser.getCredentials().getAccountType().equals(AccountType.PREM.getType())
                && currentUser.getNumFreePremiumMovies() > 0) {
            currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
            updateCurrentUser(currentUser);
            return;
        }

        if (currentUser.getTokensCount() >= Numbers.MOVIE_COST.getValue()) {
            currentUser.setTokensCount(currentUser.getTokensCount()
                    - Numbers.MOVIE_COST.getValue());
            updateCurrentUser(currentUser);
            return;
        }

        outputMessage.setError(Error.ERR.getError());
    }

    private void updateCurrentUser(final UserExtended currentUser) {
        currentUser.getPurchasedMovies().add(output.getCurrentMoviesList().get(0));

        UserExtended auxUser = new UserExtended(currentUser);
        outputMessage.setCurrentUser(auxUser);
        MovieExtended auxMovie = new MovieExtended(output.getCurrentMoviesList().get(0));
        outputMessage.getCurrentMoviesList().add(auxMovie);
    }

    @Override
    public void visit(final Watch watch) {
        outputMessage = new OutputMessage();

        if (!Page.DETAILS.getPage().equals(output.getCurrentPage())) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        UserExtended currentUser = output.getCurrentUser();

        if (currentUser.getWatchedMovies().contains(output.getCurrentMoviesList().get(0))) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        if (currentUser.getPurchasedMovies().contains(output.getCurrentMoviesList().get(0))) {
            currentUser.getWatchedMovies().add(output.getCurrentMoviesList().get(0));

            UserExtended auxUser = new UserExtended(currentUser);
            outputMessage.setCurrentUser(auxUser);
            MovieExtended auxMovie = new MovieExtended(output.getCurrentMoviesList().get(0));
            outputMessage.getCurrentMoviesList().add(auxMovie);
            return;
        }

        outputMessage.setError(Error.ERR.getError());
    }

    @Override
    public void visit(final Like like) {
        outputMessage = new OutputMessage();

        if (!Page.DETAILS.getPage().equals(output.getCurrentPage())) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        UserExtended currentUser = output.getCurrentUser();

        if (currentUser.getLikedMovies().contains(output.getCurrentMoviesList().get(0))) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        if (currentUser.getWatchedMovies().contains(output.getCurrentMoviesList().get(0))) {
            output.getCurrentMoviesList().get(0).setNumLikes(
                    output.getCurrentMoviesList().get(0).getNumLikes() + 1);
            currentUser.getLikedMovies().add(output.getCurrentMoviesList().get(0));

            UserExtended auxUser = new UserExtended(currentUser);
            outputMessage.setCurrentUser(auxUser);
            MovieExtended auxMovie = new MovieExtended(output.getCurrentMoviesList().get(0));
            outputMessage.getCurrentMoviesList().add(auxMovie);
            return;
        }

        outputMessage.setError(Error.ERR.getError());
    }

    @Override
    public void visit(final Rate rate) {
        outputMessage = new OutputMessage();

        if (!Page.DETAILS.getPage().equals(output.getCurrentPage())) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        UserExtended currentUser = output.getCurrentUser();

        if (currentUser.getRatedMovies().contains(output.getCurrentMoviesList().get(0))) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        if (rate.getRate() < Numbers.MIN_RATE.getValue()
                || rate.getRate() > Numbers.MAX_RATE.getValue()) {
            outputMessage.setError(Error.ERR.getError());
            return;
        }

        MovieExtended currentMovie = output.getCurrentMoviesList().get(0);
        if (currentUser.getWatchedMovies().contains(currentMovie)) {
            output.getMoviesRatings().get(currentMovie).add(rate.getRate());
            currentMovie.setNumRatings(currentMovie.getNumRatings() + 1);

            int sum = 0;
            for (int i = 0; i < currentMovie.getNumRatings(); i++) {
                sum += output.getMoviesRatings().get(currentMovie).get(i);
            }

            currentMovie.setRating((double) sum / currentMovie.getNumRatings());
            currentUser.getRatedMovies().add(currentMovie);

            UserExtended auxUser = new UserExtended(currentUser);
            outputMessage.setCurrentUser(auxUser);
            MovieExtended auxMovie = new MovieExtended(output.getCurrentMoviesList().get(0));
            outputMessage.getCurrentMoviesList().add(auxMovie);
            return;
        }

        outputMessage.setError(Error.ERR.getError());
    }
}
