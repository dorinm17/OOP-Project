package actions;

import input.ActionInput;

import output.ActionExtended;

public final class Back extends ActionExtended {
    public Back(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }
}
