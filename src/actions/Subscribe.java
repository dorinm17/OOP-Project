package actions;

import input.ActionInput;

import output.ActionExtended;

public final class Subscribe extends ActionExtended {
    public Subscribe(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }
}
