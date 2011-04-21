package me.charlie.qliktech.representation.tables;

import me.charlie.qliktech.representation.ds.Relation;
import me.charlie.qliktech.representation.types.RelType;
import me.charlie.qliktech.representation.visitor.QlikTechTableVisitor;

import java.util.List;

public class TableTree extends Table implements Visitable {

    private Table innerTableType;

    protected TableTree(Table table) {
        super();
        this.innerTableType = table;
    }

    @Override
    public String renderInsightCode(String loadStatements) {
        return innerTableType.renderInsightCode(loadStatements);
    }
    
    @Override
    public String precedingLoad() {
        return innerTableType.precedingLoad();
    }

    @Override
    public String renderColor() {
        return innerTableType.renderColor();
    }

    @Override
    public String presentToDot() {
        return innerTableType.presentToDot();
    }

    @Override
    public void writeStatementType() {
        innerTableType.writeStatementType();
    }

    @Override
    public Table getInnerTableType() {
        return innerTableType;
    }

    @Override
    public void setInnerTableType(Table table) {
        this.innerTableType = table;
    }

    @Override
    public void accept(QlikTechTableVisitor v) {
        v.visit(this);
    }


    @Override
    public String toString() {
        return innerTableType.toString();
    }



    
    public void addRelation(Relation relation) {
        innerTableType.addRelation(relation);
    }
    // get all join actions for this relation
    public List<Relation> getRelations() {
        return innerTableType.getRelations();
    }



    // parent table; root will be null
	public void setMyParent(Table r) {
		innerTableType.setMyParent(r);
	}
	public Table getMyParent() {
		return innerTableType.getMyParent();
	}

    // Set relation type: keep, join, interval match
    public void setRelType(RelType rt) {
        innerTableType.setRelType(rt);
    }
    public RelType getRelType() {
        return innerTableType.getRelType();
    }

	// relation name
	public void setRelation(String name) {
		innerTableType.setRelation(name);
	}
	public String getRelation() {
		return innerTableType.getRelation();
	}

	// alias name
	public void setAlias(String name) {
		innerTableType.setAlias(name);
	}
	public String getAlias() {
		return innerTableType.getAlias();
	}

	// use columns
	public void setUseColumns(List<String> useCols) {
		innerTableType.setUseColumns(useCols);
	}
	public List<String> getUseColumns() {
		return innerTableType.getUseColumns();
	}

	//  mapping columns
	public void setMappingColumns(List<String> mapCols) {
		innerTableType.setMappingColumns(mapCols);
	}
	public List<String> getMappingColumns() {
		return innerTableType.getMappingColumns();
	}

    //joins?
    public List<String> getJoinColumns() {
        return innerTableType.getJoinColumns();
    }
    public void addJoinColumn(String joinCol) {
        innerTableType.addJoinColumn(joinCol);
    }
}
