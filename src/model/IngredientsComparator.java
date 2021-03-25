package model;

import java.util.Comparator;

public class IngredientsComparator implements Comparator<Ingredients> {

    @Override
    public int compare(Ingredients i1, Ingredients i2) {
        return i1.getName().compareTo(i2.getName());
    }
    
}
