package me.charlie.qliktech.representation.tables;

import me.charlie.qliktech.representation.types.RelTypeFactory;
import me.charlie.qliktech.util.Util;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: May 20, 2010
 * Time: 10:21:15 PM
 */

public class IntervalTable extends Table {

    protected IntervalTable() {
        super();
        setRelType(RelTypeFactory.createType(RelTypeFactory.RelTypes.INTERVAL));
    }

    // Specific for interval tables
    private String inlineCode = "";
    private String selectedField;
    public void setInlineCode(String inlineCode) {
        this.inlineCode = inlineCode;
    }
    public void setSelectedField(String f) {
        this.selectedField = f;
    }

    @Override
    public String renderColor() {
        return "red";
    }

    @Override
    public String renderInsightCode(String loadStatements) {
        Util.write(code,getAlias() + ":");
        Util.write(code,"LOAD * INLINE [\n" + inlineCode + "\n];");
        Util.write(code,"");

        if (myParent != null) {
            Util.write(code,"JOIN("+ getAlias() + ") " + myType.getType(selectedField));
        }

        Util.write(code,loadStatements);

        writeStatementType();

        return code.toString();
    }

    @Override
    public String presentToDot() {
        StringBuffer b = new StringBuffer();
        b.append(Util.convertDot("+ Interval Field"));
        b.append(Util.convertDot(Util.tabStop+selectedField.toUpperCase()+"\n"));
        b.append(Util.convertDot("+ Payload Fields"));
        b.append(Util.convertDot("",getUseColumns(), "", true));
        return b.toString();
    }


    @Override
    public String precedingLoad() {
        StringBuffer b = new StringBuffer();
        b.append("LOAD\n");
        b.append("\n");
        b.append(Util.convert(Util.tabStop + "// Interval Range"));
        b.append(Util.convert("",getUseColumns(), "", true));
        return b.toString();
    }

    public void writeStatementType() {
		Util.write(code,"RESIDENT " + getAlias() + ";");
		Util.write(code,"DROP FIELDS " + getUseColumns().get(0) + ", " + getUseColumns().get(1) + ";");
        Util.write(code,"");
    }
}