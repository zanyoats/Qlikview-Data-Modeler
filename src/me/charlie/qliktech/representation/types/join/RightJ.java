package me.charlie.qliktech.representation.types.join;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Jul 10, 2010
 * Time: 10:49:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RightJ extends JoinT {
    public String getDirection() {
        return "dir=back";
    }

    public String getType() {
        return "right";
    }
}