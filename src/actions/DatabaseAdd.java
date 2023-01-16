package actions;

import input.ActionInput;

import output.ActionExtended;

public final class DatabaseAdd extends ActionExtended {
    public DatabaseAdd(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
