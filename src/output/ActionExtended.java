package output;

import actions.Visitor;

import input.ActionInput;

public abstract class ActionExtended extends ActionInput implements Visitable {
    public ActionExtended(final ActionInput action) {
        super(action);
    }

    @Override
    public abstract void accept(Visitor v);
}
