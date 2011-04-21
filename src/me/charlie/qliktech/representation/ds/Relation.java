package me.charlie.qliktech.representation.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.charlie.qliktech.representation.tables.Table;

public class Relation {
	
	Map<Table, List<Pair>> joinRelation;
	
	Table relation;
	List<Pair> listOfPairs;
	
	public Relation(Table relation) {
		joinRelation = new HashMap<Table, List<Pair>>();
		this.relation = relation;
		listOfPairs = new ArrayList<Pair>();
		setRelation();
	}

    public Relation(Table relation, List<Pair> p) {
        this(relation);
        setListOfPairs(p);
    }
	
	private void setRelation() {
		if (joinRelation.containsKey(relation)) {
			// throw exception: already has relation
			//  or, log.debug it
		} else {
			joinRelation.put(relation, listOfPairs);
		}
	}
	
	public void addPair(Pair p) {
		listOfPairs.add(p);
	}
	
	public Table getRelation() {
		return relation;
	}
	
	public List<Pair> getListOfPairs() {
		return listOfPairs;
	}
	
	public void setListOfPairs(List<Pair> p) {
		listOfPairs = p;
	}
}
