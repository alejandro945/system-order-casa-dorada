package model;

import java.io.Serializable;
import java.util.*;

public class BaseProduct implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private ProductType productType;
    private List<Ingredients> ingredients;
    private int code;
    private User creator;
    private User lastEditor;
    private boolean state;

    public BaseProduct(String name, ProductType productType, List<Ingredients> ingredient, int code, User creator) {
        this.name = name;
        this.productType = productType;
        this.ingredients = new ArrayList<>();
        this.ingredients = ingredient;
        this.code = code;
        this.creator = creator;
        this.lastEditor = creator;
        this.state = true;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public List<Ingredients> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getLastEditor() {
        return this.lastEditor;
    }

    public void setLastEditor(User lastEditor) {
        this.lastEditor = lastEditor;
    }

    public boolean isState() {
        return this.state;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getNameIngredients() {
        String nameIngredients = "";
        String redux = "";
        for (int i = 0; i < ingredients.size(); i++) {
            nameIngredients += redux + ingredients.get(i).getName();
            redux = ",";
        }
        return nameIngredients;
    }

    @Override
    public String toString() {
        return getName();
    }

}
