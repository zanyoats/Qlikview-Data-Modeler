package me.charlie.qliktech.representation.visitor;


import me.charlie.qliktech.TopicManager;
import me.charlie.qliktech.representation.tables.*;
import me.charlie.qliktech.representation.types.RelType;
import me.charlie.qliktech.representation.ds.Pair;
import me.charlie.qliktech.representation.ds.Relation;
import me.charlie.qliktech.util.Util;

import java.io.*;
import java.util.Enumeration;

public class TransformToDotV implements QlikTechTableVisitor {

    private PrintWriter relationWriter;
    private PrintWriter relationShipsWriter;

    public TransformToDotV() {
        File relationFile = new File("script\\relations");
        File relationshipFile = new File("script\\relationships");
        try {
            relationWriter = new PrintWriter( new FileOutputStream(relationFile) );
            relationShipsWriter = new PrintWriter( new FileOutputStream(relationshipFile) );

            relationWriter.write("digraph g {\n" +
                    "graph [fontsize=30 labelloc=\"t\" label=\"\" splines=true overlap=false rankdir = \"LR\"];\n" +
                    "ratio = auto;\n");

            Enumeration topicChildren = TopicManager.getInstance().getTopicRootNode().children();
            for (; topicChildren.hasMoreElements() ;) {
                TableTree tree = (TableTree) topicChildren.nextElement();
                tree.accept(this);
            }

            relationShipsWriter.write("}\n");
            relationWriter.flush(); relationWriter.close();
            relationShipsWriter.flush(); relationShipsWriter.close();

            // run command here
            try {
                Runtime.getRuntime().exec("cmd /C start /MIN .\\script\\dotToPng.cmd");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

	public void visit(TableTree table) {

        System.out.println("Running transform dot [TableTree]" + table.getAlias());

        String relationStr = "\""+ table.getAlias()+"\" " +
                             "[ style = \"filled, bold\" penwidth = 1 fillcolor = \"white\" fontname = \"Courier New\" shape = \"Mrecord\" label = " +
                                 "<<table border=\"0\" cellborder=\"0\" cellpadding=\"3\" bgcolor=\"white\">"+
                                     "<tr><td bgcolor=\""+table.renderColor()+"\" align=\"center\" colspan=\"2\"><font color=\"white\">"+ table.getAlias()+"</font></td></tr>"+
                                     table.presentToDot().replaceAll("&","&amp;") +
                                 "</table>> ];\n";

        relationWriter.write(relationStr);

		for (Relation r : table.getRelations()) {

            // Get child table
            Table childRelation = r.getRelation();

            if (!r.getListOfPairs().isEmpty()) {
                StringBuilder label = new StringBuilder();
                for (Pair p : r.getListOfPairs()) {
                    label.append(p+", ");
                }
                RelType rt = childRelation.getRelType();
                relationShipsWriter.write(table.getAlias()+" -> "+childRelation.getAlias()+" [label=\""+label.toString().substring(0, label.length()-2)+"\" " + rt.getDirection() + " " + rt.getStyle() + " penwidth = 1 fontsize = 8 fontcolor = \"black\"];\n");
            }

			// Call child table
			((Visitable)childRelation).accept(this);
		}
	}

}