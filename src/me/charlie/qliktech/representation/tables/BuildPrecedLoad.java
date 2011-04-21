package me.charlie.qliktech.representation.tables;

import me.charlie.qliktech.representation.ds.Pair;
import me.charlie.qliktech.representation.ds.Relation;

import java.util.List;

public class BuildPrecedLoad {

	private Table toBeDecorated;
    private Table decorated;

	public BuildPrecedLoad(Table tbd) {
		toBeDecorated = tbd;
	}

    public Table getDecoratedTable() {
        return new PrecedLoadDecorator(toBeDecorated);

        
//        Table parent = toBeDecorated.getMyParent();

//        if (parent != null) {
//            setUpNewMapping(parent);
//        } else {
//            setUpNewMapping();
//        }
//
//        return decorated;
    }

    private void setUpNewMapping() {
        setDecorated();
    }

    private void setUpNewMapping(Table parent) {
        List<Relation> edges = parent.getRelations();
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getRelation() == toBeDecorated) {
                List<Pair> pairs = edges.get(i).getListOfPairs();
                setDecorated();
                edges.set(i, new Relation( decorated,
                                           pairs)
                                         );
                break;
            }
        }
    }

    private void setDecorated() {
        decorated = new PrecedLoadDecorator(toBeDecorated);
    }

}