package actions;

import input.ActionInput;

import output.ActionExtended;

public final class DatabaseDelete extends ActionExtended {
    public DatabaseDelete(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
