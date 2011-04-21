package me.charlie.qliktech.representation.visitor;

import me.charlie.qliktech.representation.ds.Relation;
import me.charlie.qliktech.representation.tables.Table;
import me.charlie.qliktech.representation.tables.TableTree;
import me.charlie.qliktech.representation.tables.Visitable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Aug 8, 2010
 * Time: 3:53:16 PM
 */
public class RemoveTableV implements QlikTechTableVisitor {

    private TableTree target;
    private boolean deleteOn;
    private List<Table> listOfTables;

    public RemoveTableV(TableTree t) {
        target = t;
        deleteOn = false;
        listOfTables = new ArrayList<Table>();
    }

    @Override
    public void visit(TableTree table) {

        if (deleteOn) {
            listOfTables.add(table);
        }

        for (Relation r : table.getRelations()) {

            // Get child table
            Table childRelation = r.getRelation();

            if (target == childRelation) {
                removeModel(target);
                table.getRelations().remove(r);
                return;
            }

            // Call child table
            ((Visitable)childRelation).accept(this);
        }
    }

    private void removeModel(TableTree table) {
        listOfTables.add(table);
        deleteOn = true;
        for (Relation r: table.getRelations()) {
            ((Visitable)r.getRelation()).accept(this);
        }
        deleteOn = false;

        //DefaultListModel model = TopicManager.getInstance().getTopicModel();
        //for (Table t: listOfTables) {
        //    model.removeElement(t);
        //}
    }

}
