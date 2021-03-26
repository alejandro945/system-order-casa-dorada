package model;

import java.util.Comparator;

public class IngredientsComparator implements Comparator<Ingredients> {

    @Override
    public int compare(Ingredients i1, Ingredients i2) {
        return i2.getName().compareTo(i1.getName());
    }
    
}
