package actions;

import input.ActionInput;

import output.ActionExtended;

public final class Filter extends ActionExtended {
    public Filter(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }
}
