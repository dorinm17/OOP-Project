package output;

import actions.Visitor;

public interface Visitable {
    /**
     * Accepts a visitor.
     * @param v the visitor
     */
    void accept(Visitor v);
}
