package me.charlie.qliktech.representation.tables;

import java.util.List;

import me.charlie.qliktech.TopicManager;
import me.charlie.qliktech.representation.ds.Pair;
import me.charlie.qliktech.representation.ds.Relation;

public class BuildJoin {
	
	Table joinOnMe;
	Table addedRelation;
	
	List<String> childColumns;
	List<String> parentColumns;
	
	public BuildJoin(Table joinOnMe, Table addedRelation) {
		this.joinOnMe = joinOnMe;
		this.addedRelation = addedRelation;
        addedRelation.setMyParent(joinOnMe);
        TopicManager.getInstance().getTreeModel().insertNodeInto(
                addedRelation,
                joinOnMe,
                joinOnMe.getChildCount()
        );
	}

    public BuildJoin() {
    }
    
    public String getParentAlias() {
        return joinOnMe.getAlias();
    }
    public BuildJoin setJoinOnMe(Table t) {
        this.joinOnMe = t;
        return this;
    }
    public BuildJoin setAddedRelation(Table t) {
        this.addedRelation = t;
        addedRelation.setMyParent(joinOnMe);
        TopicManager.getInstance().getTreeModel().insertNodeInto(
                addedRelation,
                joinOnMe,
                joinOnMe.getChildCount()
        );
        return this;
    }

	
	public BuildJoin setParentColumns(List<String> l) {
		parentColumns = l;
        return this;
	}
	
	public BuildJoin setChildColumns(List<String> l) {
		childColumns = l;
        return this;
	}
	
	
	public void doJoiningAction() {
		Relation relation = new Relation(addedRelation);
		
		for (int i = 0; i < childColumns.size(); i++) {
			Pair p = new Pair();
			
			p.setFirstElem(parentColumns.get(i));
			p.setSecondElem(childColumns.get(i));
			
			relation.addPair(p);
		}
		
		joinOnMe.addRelation(relation);
	}

}
