package output;

import actions.ActionVisitor;

import actions.ChangePage;

import actions.Login;

import actions.Register;

import actions.Visitor;

import actions.Search;

import actions.Filter;

import actions.BuyPremAcc;

import actions.BuyTokens;

import actions.Purchase;

import actions.Watch;

import actions.Rate;

import actions.Like;

import actions.Back;

import actions.DatabaseAdd;

import actions.DatabaseDelete;

import actions.Subscribe;

import actions.NotificationCenter;

import constants.AccountType;

import constants.Type;

import constants.Feature;

import constants.Notify;

import constants.Page;

import input.ActionInput;

import input.Input;

import input.MovieInput;

import input.UserInput;

import java.util.ArrayList;

import java.util.List;

import java.util.Map;

import java.util.HashMap;

import java.util.Deque;

import java.util.ArrayDeque;

import java.util.Collections;

public final class Output {
    private static volatile Output instance = null;
    private final List<OutputMessage> output;
    private final Input input;
    private String currentPage;
    private UserExtended currentUser;
    private List<MovieExtended> currentMoviesList;
    private final ArrayList<UserExtended> users;
    private final ArrayList<MovieExtended> movies;
    private final Map<MovieExtended, ArrayList<Integer>> moviesRatings;
    private final Deque<String> previousPages;
    private final Map<String, NotificationCenter> subscriptions;

    /**
     * Private constructor for singleton imposes the use of getInstance()
     */
    private Output(final Input input) {
        output = Collections.synchronizedList(new ArrayList<>());
        this.input = input;
        currentPage = Page.UNAUTHENTICATED.getPage();
        currentUser = null;
        currentMoviesList = Collections.synchronizedList(new ArrayList<>());
        users = new ArrayList<>();
        movies = new ArrayList<>();
        moviesRatings = new HashMap<>();
        previousPages = new ArrayDeque<>();
        subscriptions = new HashMap<>();

        for (UserInput user : input.getUsers()) {
            users.add(new UserExtended(user));
        }

        for (MovieInput movie : input.getMovies()) {
            movies.add(new MovieExtended(movie));
        }

        for (MovieExtended movie : movies) {
            moviesRatings.put(movie, new ArrayList<>());
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

    public List<OutputMessage> getOutput() {
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

    public List<MovieExtended> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(final ArrayList<MovieExtended> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public Map<MovieExtended, ArrayList<Integer>> getMoviesRatings() {
        return moviesRatings;
    }

    public ArrayList<UserExtended> getUsers() {
        return users;
    }

    public ArrayList<MovieExtended> getMovies() {
        return movies;
    }

    public Deque<String> getPreviousPages() {
        return previousPages;
    }

    public Map<String, NotificationCenter> getSubscriptions() {
        return subscriptions;
    }

    /**
     * Implements visitor pattern for the actions.
     */
    public void runActions() {
        Visitor actionVisitor;
        ActionExtended actionExtended;

        for (ActionInput action : input.getActions()) {
            actionVisitor = new ActionVisitor();

            if (Type.CHANGE.getType().equals(action.getType())) {
                actionExtended = new ChangePage(action);
            } else if (Type.BACK.getType().equals(action.getType())) {
                actionExtended = new Back(action);
            } else if (Feature.LOGIN.getFeature().equals(action.getFeature())) {
                actionExtended = new Login(action);
            } else if (Feature.REG.getFeature().equals(action.getFeature())) {
                actionExtended = new Register(action);
            } else if (Feature.SEARCH.getFeature().equals(action.getFeature())) {
                actionExtended = new Search(action);
            } else if (Feature.FILTER.getFeature().equals(action.getFeature())) {
                actionExtended = new Filter(action);
            } else if (Feature.BUY_TOK.getFeature().equals(action.getFeature())) {
                actionExtended = new BuyTokens(action);
            } else if (Feature.BUY_PREM_ACC.getFeature().equals(action.getFeature())) {
                actionExtended = new BuyPremAcc(action);
            } else if (Feature.PURCHASE.getFeature().equals(action.getFeature())) {
                actionExtended = new Purchase(action);
            } else if (Feature.WATCH.getFeature().equals(action.getFeature())) {
                actionExtended = new Watch(action);
            } else if (Feature.LIKE.getFeature().equals(action.getFeature())) {
                actionExtended = new Like(action);
            } else if (Feature.RATE.getFeature().equals(action.getFeature())) {
                actionExtended = new Rate(action);
            } else if (Feature.SUBSCRIBE.getFeature().equals(action.getFeature())) {
                actionExtended = new Subscribe(action);
            } else if (Feature.ADD.getFeature().equals(action.getFeature())) {
                actionExtended = new DatabaseAdd(action);
            } else {
                actionExtended = new DatabaseDelete(action);
            }

            actionExtended.accept(actionVisitor);

            if (actionVisitor.getOutputMessage() != null) {
                output.add(actionVisitor.getOutputMessage());
            }
        }

        if (currentUser != null) {
            if (currentUser.getCredentials().getAccountType().equals(
                    AccountType.PREM.getType())) {
                OutputMessage outputMessage = new OutputMessage();
                currentUser.getNotifications().add(new Notification(
                        Notify.NO_RECOMMENDATION.getNotify(), Notify.RECOMMENDATION.getNotify()));
                outputMessage.setCurrentUser(currentUser);
                outputMessage.setCurrentMoviesList(null);
                output.add(outputMessage);
            }
        }
    }
}
