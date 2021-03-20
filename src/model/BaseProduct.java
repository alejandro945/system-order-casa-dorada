package model;

import java.util.List;

public class BaseProduct {
    
    private String name; 
    private ProductType productType;
    private List<Ingredients> ingredients;
    private int code;


    public BaseProduct(String name, ProductType productType, List<Ingredients> ingredients, int code) {
        this.name = name;
        this.productType = productType;
        this.ingredients = ingredients;
        this.code = code;
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

}
