package me.charlie.qliktech.representation.visitor;

import me.charlie.qliktech.representation.tables.*;

public interface QlikTechTableVisitor {
    public void visit(TableTree table);

}
