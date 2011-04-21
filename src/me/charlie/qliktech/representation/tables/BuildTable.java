package me.charlie.qliktech.representation.tables;

import java.util.List;

public class BuildTable {

    public enum TableTypes {
        SQL_TABLE,
        RESIDENT_TABLE,
        INLINE_TABLE,
        INTERVAL_TABLE,
        CUSTOM_TABLE
    }

	private Table relation;
	
	public BuildTable(TableTypes type) {
       relation =
          new TableTree( getType(type) );
	}

    private Table getType(TableTypes type) {
        if (type == TableTypes.SQL_TABLE) {
           return new SQLTable();
        } else if (type == TableTypes.RESIDENT_TABLE) {
            return new ResidentTable();
        } else if (type == TableTypes.INLINE_TABLE) {
            return new InlineTable();
        } else if (type == TableTypes.INTERVAL_TABLE) {
            return new IntervalTable();
        } else if (type == TableTypes.CUSTOM_TABLE) {
            return new CustomTable();
        } else {
            return null;  // Should never happen...
        }
    }
	
	public BuildTable setRelation(String s) {
		relation.setRelation(s);
        return this;
	}

	public BuildTable setAlias(String s) {
		relation.setAlias(s);
        return this;
	}

	public BuildTable setUseColumns(List<String> l) {
		relation.setUseColumns(l);
        return this;
	}

	public BuildTable setMappingColumns(List<String> l) {
		relation.setMappingColumns(l);
        return this;
	}

	public Table build() {
		return relation;
	}

}
