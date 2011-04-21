package me.charlie.qliktech.representation.tables;

import me.charlie.qliktech.representation.types.RelType;
import me.charlie.qliktech.representation.types.RelTypeFactory;
import me.charlie.qliktech.representation.ds.Relation;
import me.charlie.qliktech.util.Util;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

abstract public class Table extends DefaultMutableTreeNode {

    protected StringBuffer code;
    protected List<Relation> relations; // TODO: remove with iterator pattern
	protected Table myParent = null;
    protected RelType myType;
	protected String myRelation;
	protected String myAlias;
	protected List<String> myUseColumns;
	protected List<String> myMapColumns;
    protected List<String> myJoinColumns;

    protected Table() {
        myType = RelTypeFactory.createType(RelTypeFactory.RelTypes.OUTER_KEEP);
        code = new StringBuffer();
        relations = new ArrayList<Relation>(); // TODO: remove again!!!
        myJoinColumns = new ArrayList<String>();
    }


    // Template method
    public String renderInsightCode(String loadStatements) {

        Util.write(code, getAlias() + ":");

        if (myParent != null) {
            Util.write(code, myType.getType(myParent.getAlias()));
        }

        Util.write(code, loadStatements);

        writeStatementType();

        return code.toString();
    }

    // Template hook
    abstract public void writeStatementType();

    // Rest of abstract interface
    abstract public String precedingLoad();
    abstract public String renderColor();

    // Presentation to Dot
    abstract public String presentToDot();


    /*****************************************************************************************************************
     *  Other Table Mehtods
     *
     */


    public Table getInnerTableType() {
        return this;
    }

    public void setInnerTableType(Table table) {}

    @Override
    public String toString() {
        return myAlias;
    }




    // TODO: Add itertor() method to remove this
    //  iterator() method for island type returns NullIterator
    // add relation to this relation
    public void addRelation(Relation relation) {
        relations.add(relation);
    }
    // get all join actions for this relation
    public List<Relation> getRelations() {
        return relations;
    }



    // parent table; root will be null
	public void setMyParent(Table r) {
		myParent = r;
	}
	public Table getMyParent() {
		return myParent;
	}

    // Set relation type: keep, join, interval match
    public void setRelType(RelType rt) {
        myType = rt;
    }
    public RelType getRelType() {
        return myType;
    }
	
	// relation name
	public void setRelation(String name) {
		myRelation = name;
	}
	public String getRelation() {
		return myRelation;
	}
	
	// alias name
	public void setAlias(String name) {
		myAlias = name;
	}
	public String getAlias() {
		return myAlias;
	}
	
	// use columns
	public void setUseColumns(List<String> useCols) {
		myUseColumns = useCols;
	}
	public List<String> getUseColumns() {
		return myUseColumns;
	}
	
	//  mapping columns
	public void setMappingColumns(List<String> mapCols) {
		myMapColumns = mapCols;
	}
	public List<String> getMappingColumns() {
		return myMapColumns;
	}

    //joins?
    public List<String> getJoinColumns() {
        return myJoinColumns;
    }
    public void addJoinColumn(String joinCol) {
        myJoinColumns.add(joinCol);
    }
}
