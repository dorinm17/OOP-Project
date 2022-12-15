package actions;

import input.ActionInput;

import output.ActionExtended;

public final class Login extends ActionExtended {
    public Login(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }
}
