package me.charlie.qliktech.representation.types.keep;

import me.charlie.qliktech.representation.types.RelType;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Jul 10, 2010
 * Time: 10:49:44 PM
 */
abstract public class KeepT implements RelType {

    abstract public String getType();
    abstract public String getDirection();

    public String getStyle() {
        return "style=dotted";
    }
    public String getType(String ref) {
        return getType() + " keep(" + ref + ")";
    }
}