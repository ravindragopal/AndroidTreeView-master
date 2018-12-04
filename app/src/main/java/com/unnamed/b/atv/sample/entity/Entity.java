package com.unnamed.b.atv.sample.entity;

import java.io.Serializable;
import java.util.Comparator;

public abstract class Entity implements Comparator<Entity>, Serializable {

    private Boolean selected = false;

    public abstract long getKey();

    public abstract String getValue();

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setValue(String s) {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this == obj) {
            return true;
        } else if (obj != null && (getKey() == ((Entity) obj).getKey())) {
            return true;
        }
        return false;
    }

    @Override
    public int compare(Entity o1, Entity o2) {
        long one = o1.getKey();
        long two = o2.getKey();
        return one == two ? 0 : one < two ? -1 : 1;
    }



    @Override
    public String toString() {
        return getValue();
    }

    public String getSubValue(){
     return null;
    }

}
