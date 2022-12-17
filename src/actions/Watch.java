package actions;

import input.ActionInput;

import output.ActionExtended;

public final class Watch extends ActionExtended {
    public Watch(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }
}
