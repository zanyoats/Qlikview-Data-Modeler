package me.charlie.qliktech.representation.types;

import me.charlie.qliktech.representation.types.interval.IntervalT;
import me.charlie.qliktech.representation.types.join.InnerJ;
import me.charlie.qliktech.representation.types.join.LeftJ;
import me.charlie.qliktech.representation.types.join.OuterJ;
import me.charlie.qliktech.representation.types.join.RightJ;
import me.charlie.qliktech.representation.types.keep.InnerK;
import me.charlie.qliktech.representation.types.keep.LeftK;
import me.charlie.qliktech.representation.types.keep.OuterK;
import me.charlie.qliktech.representation.types.keep.RightK;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Jul 11, 2010
 * Time: 3:01:44 PM
 */
public class RelTypeFactory {

    public enum RelTypes {
        OUTER_KEEP,
        INNER_KEEP,
        LEFT_KEEP,
        RIGHT_KEEP,

        OUTER_JOIN,
        INNER_JOIN,
        LEFT_JOIN,
        RIGHT_JOIN,

        INTERVAL
    }

    public static RelType createType(RelTypes t) {
        if      (t == RelTypes.OUTER_KEEP) return new OuterK();
        else if (t == RelTypes.OUTER_JOIN) return new OuterJ();
        else if (t == RelTypes.INNER_KEEP) return new InnerK();
        else if (t == RelTypes.INNER_JOIN) return new InnerJ();
        else if (t == RelTypes.LEFT_KEEP)  return new LeftK();
        else if (t == RelTypes.LEFT_JOIN)  return new LeftJ();
        else if (t == RelTypes.RIGHT_KEEP) return new RightK();
        else if (t == RelTypes.RIGHT_JOIN) return new RightJ();
        else if (t == RelTypes.INTERVAL)   return new IntervalT();
        else return null;
    }
}
