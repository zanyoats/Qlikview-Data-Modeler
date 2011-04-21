package me.charlie.qliktech.representation.tables;

import me.charlie.qliktech.util.Util;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: May 20, 2010
 * Time: 10:21:15 PM
 */

public class ResidentTable extends Table {
    protected String whereClause = null;

    // -----------------------------------------------------------------------------------------------------------------
    protected ResidentTable() {
        super();
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public String renderColor() {
        return "brown";
    }

    @Override
    public String presentToDot() {
        StringBuffer b = new StringBuffer();
        b.append(Util.convertDot("+ Interface Fields"));
        b.append(Util.convertDot(getRelation(), getAlias(),getMappingColumns(), ""));
        b.append(Util.convertDot("+ Payload Fields"));
        b.append(Util.convertDot(getRelation(), getAlias(),getUseColumns(), ""));
        b.append(Util.convertDot("+ Where Clause"));
        b.append(Util.convertDot(Util.tabStop + whereClause));
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
        b.append("*;\n");
        b.append("LOAD\n");
        b.append(Util.convert(Util.tabStop + "// Interface Fields"));
		b.append(Util.convert(getRelation(), getAlias(),getMappingColumns(), ","));
        b.append("\n");
		b.append(Util.convert(Util.tabStop + "// Payload Fields"));
		b.append(Util.convert(getRelation(), getAlias(),getUseColumns(), ""));
        return b.toString();
    }

    @Override
    public void writeStatementType() {
        if (whereClause == null) {
            Util.write(code, "RESIDENT " + getRelation() + ";");
        } else {
            Util.write(code, "RESIDENT " + getRelation());
            Util.write(code, "WHERE " + whereClause.concat(";"));
        }
        Util.write(code, "");
    }

}