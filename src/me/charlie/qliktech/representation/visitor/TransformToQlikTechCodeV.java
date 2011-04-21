package me.charlie.qliktech.representation.visitor;


import me.charlie.qliktech.TopicManager;
import me.charlie.qliktech.representation.tables.*;

import me.charlie.qliktech.representation.ds.Pair;
import me.charlie.qliktech.representation.ds.Relation;
import me.charlie.qliktech.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;

public class TransformToQlikTechCodeV implements QlikTechTableVisitor {

    private static final String
            headerCode = "LET vCurrentDate = now();\n"+
                         "\n"+
                         "ODBC CONNECT TO [insert_dsn_here];" +
                         "\n\n";

    private PrintWriter relationWriter;
    private Stack<String> tables = new Stack<String>();

    public TransformToQlikTechCodeV() {
        try {
            relationWriter = new PrintWriter(
                                new FileOutputStream(
                                    new File(Util.INSIGHT_DIR, Util.SCRIPT_FILE )));

            Enumeration topicChildren = TopicManager.getInstance().getTopicRootNode().children();
            for (; topicChildren.hasMoreElements() ;) {
                TableTree tree = (TableTree) topicChildren.nextElement();
                tree.accept(this);
            }

            relationWriter.write(headerCode);
            while (!tables.isEmpty()) {
                relationWriter.write(tables.pop());
            }
            relationWriter.flush();
            relationWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

	public void visit(TableTree table) {
        System.out.println("Visiting Connected Table: " + table.getClass());

        for (Relation r : table.getRelations()) {

            // Get child table
            Table childRelation = r.getRelation();

            if (! (childRelation.getInnerTableType() instanceof IntervalTable) ) { // ... ie no pairs defined

                // Get list of Pairs
                List<Pair> pairs = r.getListOfPairs();

                // Make composite or regular key
                String parentJoinColumn="", childJoinColumn="";

                for (Pair p : pairs) {
                    parentJoinColumn = parentJoinColumn+ table.getAlias()+"_"+p.getFirstElem()+",";
                    childJoinColumn = childJoinColumn+childRelation.getAlias()+"_"+p.getSecondElem()+",";
                }

                System.out.println("Parent Join Col: " + parentJoinColumn);
                System.out.println("Child Join Col: " + childJoinColumn);

                parentJoinColumn = parentJoinColumn.substring(0, parentJoinColumn.length()-1);
                childJoinColumn = childJoinColumn.substring(0, childJoinColumn.length()-1);

                if (pairs.size() > 1) {
                    parentJoinColumn =
                            "autonumberhash128("+parentJoinColumn+")"+" as "+"key_"+ table.getAlias()+"_"+childRelation.getAlias();
                    childJoinColumn =
                            "autonumberhash128("+childJoinColumn+")"+" as "+"key_"+ table.getAlias()+"_"+childRelation.getAlias();
                } else {
                    parentJoinColumn =
                            parentJoinColumn+" as "+"key_"+ table.getAlias()+"_"+childRelation.getAlias();
                    childJoinColumn =
                            childJoinColumn+" as "+"key_"+ table.getAlias()+"_"+childRelation.getAlias();
                }

                table.addJoinColumn(parentJoinColumn);
                childRelation.addJoinColumn(childJoinColumn);
            }

            // Call child table
            ((Visitable)childRelation).accept(this);

		}

        String tableCode = "///$tab " +
                           table.getAlias() +
                           "\n" +
                           table.renderInsightCode( table.precedingLoad() ) +
                           "\n";
        tables.push(tableCode);
	}
}
