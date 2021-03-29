package model;

import java.util.Comparator;

public class CostumersComparator implements Comparator<Costumer> {
    @Override
    public int compare(Costumer c1, Costumer c2) {
        return c1.getId() - c2.getId();
    }
}
