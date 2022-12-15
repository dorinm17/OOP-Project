package output;

import actions.ActionVisitor;

import actions.ChangePage;

import actions.Login;

import actions.Register;

import actions.Visitor;

import constants.Feature;

import constants.Page;

import constants.Type;

import input.ActionInput;

import input.Input;

import input.MovieInput;

import input.UserInput;

import java.util.ArrayList;

public final class Output {
    private static volatile Output instance = null;
    private final ArrayList<OutputMessage> output;
    private final Input input;
    private String currentPage;
    private UserExtended currentUser;
    private final ArrayList<MovieExtended> currentMoviesList;
    private final ArrayList<UserExtended> users;
    private final ArrayList<MovieExtended> movies;

    /**
     * Private constructor for singleton imposes the use of getInstance()
     */
    private Output(final Input input) {
        output = new ArrayList<>();
        this.input = input;
        currentPage = Page.UNAUTHENTICATED.getPage();
        currentUser = null;
        currentMoviesList = new ArrayList<>();
        users = new ArrayList<>();
        movies = new ArrayList<>();

        for (UserInput user : input.getUsers()) {
            users.add(new UserExtended(user));
        }

        for (MovieInput movie : input.getMovies()) {
            movies.add(new MovieExtended(movie));
        }
    }

    /**
     * Synchronized method for singleton
     *
     * @return the instance of the singleton pattern class
     */
    public static Output getInstance(final Input input) {
        if (instance == null) {
            synchronized (Output.class) {
                if (instance == null) {
                    instance = new Output(input);
                }
            }
        }

        return instance;
    }

    public static Output getInstance() {
        return instance;
    }

    /**
     * Resets the singleton instance for the next test case.
     */
    public void setInstance() {
        instance = null;
    }

    public ArrayList<OutputMessage> getOutput() {
        return output;
    }

    public Input getInput() {
        return input;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final String currentPage) {
        this.currentPage = currentPage;
    }

    public UserExtended getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final UserExtended currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<MovieExtended> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public ArrayList<UserExtended> getUsers() {
        return users;
    }

    public ArrayList<MovieExtended> getMovies() {
        return movies;
    }

    /**
     * Implements visitor pattern for the actions.
     */
    public void runActions() {
        Visitor actionVisitor;

        for (ActionInput action : input.getActions()) {
            actionVisitor = new ActionVisitor();

            if (Type.CHANGE.getType().equals(action.getType())) {
                ActionExtended changePage = new ChangePage(action);
                changePage.accept(actionVisitor);
            } else if (Feature.LOGIN.getFeature().equals(action.getFeature())) {
                ActionExtended login = new Login(action);
                login.accept(actionVisitor);
            } else if (Feature.REG.getFeature().equals(action.getFeature())) {
                ActionExtended register = new Register(action);
                register.accept(actionVisitor);
            }

            if (actionVisitor.getOutputMessage() != null) {
                output.add(actionVisitor.getOutputMessage());
            }
        }
    }
}
