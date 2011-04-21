package me.charlie.qliktech.representation.ds;

import java.util.List;
import java.util.ArrayList;

public class Pair {
	
	protected List<String> p = new ArrayList<String>();
	
	// first element
	public void setFirstElem(String e1) {
		p.add(e1);
	}
	public String getFirstElem() {
		return p.get(0);
	}
	
	// second element
	public void setSecondElem(String e2) {
		p.add(e2);
	}
	public String getSecondElem() {
		return p.get(1);
	}

    @Override
    public String toString() {
        return "("+getFirstElem()+", "+getSecondElem()+")";
    }
}
