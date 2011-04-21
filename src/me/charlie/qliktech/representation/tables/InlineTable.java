package me.charlie.qliktech.representation.tables;

import me.charlie.qliktech.util.Util;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: May 20, 2010
 * Time: 10:21:15 PM
 */

public class InlineTable extends Table {
    private String inlineCode = "";

    protected InlineTable() {
        super();
    }

    public void setInlineCode(String inlineCode) {
        this.inlineCode = inlineCode;
    }

    @Override
    public String renderColor() {
        return "black";
    }

    @Override
    public String presentToDot() {
        StringBuffer b = new StringBuffer();
        b.append(Util.convertDot("+ Interface Fields"));
        b.append(Util.convertDot("",getMappingColumns(), "", true));
        b.append(Util.convertDot("+ Payload Fields"));
        b.append(Util.convertDot("",getUseColumns(), "", true));
        return b.toString();
    }

    @Override
    public String precedingLoad() {
        StringBuffer b = new StringBuffer();
        b.append("LOAD\n");
        b.append(Util.convert(Util.tabStop + "// Key Field"));
        int i = 0;
        for (String keyField: getJoinColumns()) {
            keyField = keyField.substring(keyField.indexOf("key_"));
            String joinField = getMappingColumns().get(i++) + " as " + keyField;
            b.append(Util.convert(Util.tabStop + joinField + ","));
        }
        b.append("\n");
        b.append(Util.convert(Util.tabStop + "// Payload Fields"));
        b.append(Util.convert("",getUseColumns(), "", true));
        return b.toString();
    }

    @Override
    public void writeStatementType() {
		Util.write(code, "INLINE [\n" + inlineCode + "\n];");
		Util.write(code, "");
    }
}