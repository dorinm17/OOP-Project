package actions;

import input.ActionInput;

import output.ActionExtended;

public final class Like extends ActionExtended {
    public Like(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }
}
