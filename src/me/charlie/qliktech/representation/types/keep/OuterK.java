package me.charlie.qliktech.representation.types.keep;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Jul 10, 2010
 * Time: 10:49:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class OuterK extends KeepT {
    public String getDirection() {
        return "dir=both";
    }

    public String getType() {
        return "outer";
    }

    // OUTER KEEP(table) syntax does not work, there override this
    // to do nothing
    @Override
    public String getType(String ref) {
        return "";
    }
}