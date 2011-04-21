package me.charlie.qliktech.representation.tables;

import me.charlie.qliktech.representation.visitor.QlikTechTableVisitor;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Aug 4, 2010
 * Time: 8:53:43 PM
 */
public interface Visitable {
    public void accept(QlikTechTableVisitor v);
}
