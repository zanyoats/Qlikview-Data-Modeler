package me.charlie.qliktech.representation.types.interval;

import me.charlie.qliktech.representation.types.RelType;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Jul 10, 2010
 * Time: 10:49:44 PM
 */
public class IntervalT implements RelType {

    public String getDirection() {
        return "dir=none";
    }

    public String getStyle() {
        return "";
    }

    public String getType(String ref) {
        return "IntervalMatch (" + ref + ")";
    }
}