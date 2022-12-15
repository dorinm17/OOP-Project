package actions;

import input.ActionInput;

import output.ActionExtended;
public final class Register extends ActionExtended {
    public Register(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }
}
