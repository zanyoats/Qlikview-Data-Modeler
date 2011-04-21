package me.charlie.qliktech.representation.types.join;

import me.charlie.qliktech.representation.types.RelType;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Jul 10, 2010
 * Time: 10:49:44 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class JoinT implements RelType {

    abstract public String getType();
    abstract public String getDirection();

    public String getStyle() {
        return "";
    }
    public String getType(String ref) {
        return getType() + " join(" + ref + ")";
    }
}
