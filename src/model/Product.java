package model;

import java.io.Serializable;

public class Product implements Serializable, Comparable<Product> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BaseProduct baseProduct;
    private ProductSize productSize;
    private int code;
    private double price;
    private User creator;
    private User lastEditor;
    private boolean state;

    public Product(BaseProduct baseProduct, int code, ProductSize productSize, double price, User creator) {
        this.baseProduct = baseProduct;
        this.code = code;
        this.productSize = productSize;
        this.price = price;
        this.creator = creator;
        this.lastEditor = creator;
        this.state = true;

    }

    public BaseProduct getBaseProduct() {
        return this.baseProduct;
    }

    public void setBaseProduct(BaseProduct baseProduct) {
        this.baseProduct = baseProduct;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ProductSize getProductSize() {
        return this.productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    @Override
    public int compareTo(Product p) {
        if (price < p.getPrice()) {
            return -1;
        }
        if (price > p.getPrice()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return getBaseProduct().getName() + "-" + getProductSize();
    }

}
