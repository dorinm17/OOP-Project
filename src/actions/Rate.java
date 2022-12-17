package actions;

import input.ActionInput;

import output.ActionExtended;

public final class Rate extends ActionExtended {
    public Rate(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }
}
