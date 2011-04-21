package me.charlie.qliktech.representation.tables;

import me.charlie.qliktech.representation.types.RelType;
import me.charlie.qliktech.representation.ds.Relation;
import me.charlie.qliktech.util.Util;

import java.util.ArrayList;
import java.util.List;

public class PrecedLoadDecorator extends Table {

    private Table table;

    private List<String> precedingColumns =
            new ArrayList<String>();

    protected PrecedLoadDecorator(Table table) {
        super();
        this.table = table;
    }

    public void addPrecedingColumn(String column) {
        precedingColumns.add(column);
    }

    @Override
    public String precedingLoad() {
        StringBuffer b = new StringBuffer();

        if (precedingColumns.isEmpty()) {
            return b.toString().concat(table.precedingLoad());
        }

        b.append("LOAD\n");
        for (int i = 0; i < precedingColumns.size(); i++) {
            b.append(Util.tabStop + precedingColumns.get(i).toUpperCase());
            if (i == precedingColumns.size() - 1) {
                b.append(";\n");
            } else {
                b.append(",\n");
            }
        }

        return b.toString().concat(table.precedingLoad());
    }

    @Override
    public String presentToDot() {
        StringBuffer b = new StringBuffer();

        if (precedingColumns.isEmpty()) {
            return b.toString().concat(table.presentToDot());
        }

        b.append(Util.convertDot("+ Preceding Load"));

        for (int i = 0; i < precedingColumns.size(); i++) {
            b.append(Util.convertDot(Util.tabStop + precedingColumns.get(i)));
        }

        return b.toString().concat(table.presentToDot());
    }


    // Just delegate rest of calls by overriding
    //  table interface methods.

    @Override
    public String toString() {
        return table.toString();
    }

    @Override
    public void writeStatementType() {
        table.writeStatementType();
    }

    @Override
    public String renderInsightCode(String loadStatements) {
        return table.renderInsightCode(loadStatements);
    }

    @Override
    public String renderColor() {
        return table.renderColor();
    }


    // TODO: Add itertor() method to remove this
    //  iterator() method for island type returns NullIterator
    // add relation to this relation
    @Override
    public void addRelation(Relation relation) {
        table.addRelation(relation);
    }
    @Override
    public List<Relation> getRelations() {
        return table.getRelations();
    }



    @Override
	public void setMyParent(Table r) {
		table.setMyParent(r);
	}
    @Override
	public Table getMyParent() {
		return table.getMyParent();
	}

    @Override
    public void setRelType(RelType rt) {
        table.setRelType(rt);
    }
    @Override
    public RelType getRelType() {
        return table.getRelType();
    }

	@Override
	public void setRelation(String name) {
		table.setRelation(name);
	}
    @Override
	public String getRelation() {
		return table.getRelation();
	}

	@Override
	public void setAlias(String name) {
		table.setAlias(name);
	}
    @Override
	public String getAlias() {
		return table.getAlias();
	}

	@Override
	public void setUseColumns(List<String> useCols) {
		table.setUseColumns(useCols);
	}
    @Override
	public List<String> getUseColumns() {
		return table.getUseColumns();
	}

	@Override
	public void setMappingColumns(List<String> mapCols) {
		table.setMappingColumns(mapCols);
	}
    @Override
	public List<String> getMappingColumns() {
		return table.getMappingColumns();
	}

    @Override
    public void addJoinColumn(String joinCol) {
        table.addJoinColumn(joinCol);
    }
    @Override
    public List<String> getJoinColumns() {
        return table.getJoinColumns();
    }

}