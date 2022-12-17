package actions;

import input.ActionInput;

import output.ActionExtended;

public final class BuyPremAcc extends ActionExtended {
    public BuyPremAcc(final ActionInput action) {
        super(action);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
