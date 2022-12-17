package actions;

import input.ActionInput;

import output.ActionExtended;

public final class BuyTokens extends ActionExtended {
    public BuyTokens(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
