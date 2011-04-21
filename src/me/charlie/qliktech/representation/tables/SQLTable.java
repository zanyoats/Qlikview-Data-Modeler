package me.charlie.qliktech.representation.tables;

import me.charlie.qliktech.util.Util;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: May 20, 2010
 * Time: 10:21:15 PM
 */

public class SQLTable extends Table {
    protected String whereClause = null;

    // -----------------------------------------------------------------------------------------------------------------
    protected SQLTable() {
        super();
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public String renderColor() {
        return "blue";
    }

    @Override
    public String presentToDot() {
        StringBuffer b = new StringBuffer();
        b.append(Util.convertDot("+ Interface Fields"));
        b.append(Util.convertDot(getAlias(),getMappingColumns(), "", true));
        b.append(Util.convertDot("+ Payload Fields"));
        b.append(Util.convertDot(getAlias(),getUseColumns(), "", true));
        return b.toString();
    }

    @Override
    public String precedingLoad() {
        StringBuffer b = new StringBuffer();
        b.append("LOAD\n");
        b.append(Util.convert(Util.tabStop + "// Key Field"));
        for (String joinField : getJoinColumns()) {
            b.append(Util.convert(Util.tabStop + joinField + ","));
        }
        b.append("\n");
        b.append(Util.convert(Util.tabStop + "// Interface Fields"));
		b.append(Util.convert(getAlias(),getMappingColumns(), ",", true));
        b.append("\n");
		b.append(Util.convert(Util.tabStop + "// Payload Fields"));
		b.append(Util.convert(getAlias(),getUseColumns(), ";", true));
        return b.toString();
    }

    @Override
    public void writeStatementType() {
        Util.write(code, "SELECT");
        Util.writeArray(code, getAlias(),getMappingColumns(), ",", false);
        Util.writeArray(code, getAlias(),getUseColumns(), "", false);
        if (whereClause == null) {
            Util.write(code, "FROM " + getRelation() + ";");
        } else {
            Util.write(code, "FROM " + getRelation());
            Util.write(code, "WHERE " + whereClause.concat(";"));
        }
        Util.write(code, "");
    }
}
