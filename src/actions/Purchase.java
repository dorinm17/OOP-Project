package actions;

import input.ActionInput;

import output.ActionExtended;

public final class Purchase extends ActionExtended {
    public Purchase(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }
}
