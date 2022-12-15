package actions;

import input.ActionInput;

import output.ActionExtended;

public final class ChangePage extends ActionExtended {
    public ChangePage(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }
}
