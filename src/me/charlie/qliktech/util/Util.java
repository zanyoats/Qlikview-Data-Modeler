package me.charlie.qliktech.util;

import java.io.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: May 18, 2010
 * Time: 10:09:45 PM
 */
public class Util {

    public static final String tabStop = "    ";
    public static final String SCRIPT_FILE = "qliktech.qvs",
                               DB_PROPS_FILE = "database.properties";
    public static final String SCRIPT_DIR = "script", IMAGE_DIR = "image", INSIGHT_DIR = "insight",
                               CANVAS = "canvas.png", MODEL = "model.png";

    public static void copyFileContents(File src, File dest) throws IOException {
        try {
            FileInputStream in = new FileInputStream(src);
            FileOutputStream out = new FileOutputStream(dest);
            for (int c = in.read(); c != -1; c = in.read()) {
                out.write(c);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void copyCanvas() {
        try {
            copyFileContents(new File(Util.SCRIPT_DIR, Util.CANVAS), new File(Util.SCRIPT_DIR, Util.MODEL));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  --- Utility functions ---
     *   for writing String Buffer (code)
     */
    public static void write(StringBuffer code, String s) {
        code.append(s.toUpperCase().concat("\n"));
    }

    public static void writeArray(StringBuffer code, String alias, List<String> l, String lastChar, boolean convert) {
        for (int i = 0; i < l.size(); i++) {
            String loadField = alias.concat("_").concat(l.get(i));
            String sqlField = Util.tabStop.concat(l.get(i)).concat(" as ").concat(loadField);
            loadField = Util.tabStop.concat(loadField);

            String field = (convert) ? loadField : sqlField;

            write(code, (i == l.size() - 1) ? (field.concat(lastChar)) : (field.concat(",")));
        }
    }

    public static void writeArray(StringBuffer code, String relation, String alias, List<String> l, String lastChar) {
        for (int i = 0; i < l.size(); i++) {
            String mapField = l.get(i);
            String field = Util.tabStop + relation + "_" + mapField + " as " + alias + "_" + mapField;
            write(code, (i == l.size() - 1) ? (field.concat(lastChar)) : (field.concat(",")));
        }
    }

    public static String convert(String s) {
        return s.toUpperCase().concat("\n");
    }




    public static String convertDot(String s) {
        return s.concat("<tr><td align=\"left\" port=\"r0\">"+s+"</td></tr>");
    }
    public static String convertDot(String alias, List<String> l, String lastChar, boolean convert) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < l.size(); i++) {
            String loadField;
            if (!alias.equals("")) {
                loadField = alias.concat("_").concat(l.get(i));
            } else {
                loadField = l.get(i);
            }
            String sqlField = Util.tabStop.concat(l.get(i)).concat(" as ").concat(loadField);
            loadField = Util.tabStop.concat(loadField);

            String field = (convert) ? loadField : sqlField;

            b.append( convertDot( (i == l.size() - 1) ? (field.concat(lastChar)) : (field.concat("")) ) );
        }
        return b.toString();
    }
    public static String convertDot(String relation, String alias, List<String> l, String lastChar) {
        return convertDot(relation, alias, l, lastChar, false);
    }
    public static String convertDot(String relation, String alias, List<String> l, String lastChar, boolean customTable) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < l.size(); i++) {
            String mapField = l.get(i);
            String field;
            if (customTable) {
                field = mapField + " as " + alias + "_" + mapField;
            } else {
                field = Util.tabStop + relation + "_" + mapField + " as " + alias + "_" + mapField;
            }
            b.append( convertDot( (i == l.size() - 1) ? (field.concat(lastChar)) : (field.concat("")) ) );
        }
        return b.toString();
    }



    public static String convert(String alias, List<String> l, String lastChar, boolean convert) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < l.size(); i++) {
            String loadField;
            if (!alias.equals("")) {
                loadField = alias.concat("_").concat(l.get(i));
            } else {
                loadField = l.get(i);
            }
            String sqlField = Util.tabStop.concat(l.get(i)).concat(" as ").concat(loadField);
            loadField = Util.tabStop.concat(loadField);

            String field = (convert) ? loadField : sqlField;

            b.append( convert( (i == l.size() - 1) ? (field.concat(lastChar)) : (field.concat(",")) ) );
        }
        return b.toString();
    }

    public static String convert(String relation, String alias, List<String> l, String lastChar) {
        return convert(relation, alias, l, lastChar, false);
    }

    public static String convert(String relation, String alias, List<String> l, String lastChar, boolean customTable) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < l.size(); i++) {
            String mapField = l.get(i);
            String field;
            if (customTable) {
                field = mapField + " as " + alias + "_" + mapField;
            } else {
                field = Util.tabStop + relation + "_" + mapField + " as " + alias + "_" + mapField;
            }
            b.append( convert( (i == l.size() - 1) ? (field.concat(lastChar)) : (field.concat(",")) ) );
        }
        return b.toString();
    }

}
