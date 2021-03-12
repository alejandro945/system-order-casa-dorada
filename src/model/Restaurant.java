package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private List<Product> products;
    private List<Person> people;
    private List<Order> orders;
    private List<Ingredients> ingredients;
    private User logged;
    public static final String FILE_SEPARATOR = "\\ ";
    public static final String SAVE_PATH_FILE = "data/users.report";


    public Restaurant() {
        products = new ArrayList<Product>();
        people = new ArrayList<Person>();
        orders = new ArrayList<Order>();
        ingredients = new ArrayList<Ingredients>();
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Person> getPeople() {
        return this.people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Ingredients> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void addNewUser(String name, String lastName, int id, String userName, String password, String path) {
        people.add(new User(name, lastName, id, userName, password, path));
    }

    public void setLoggedUser(User user) {
        this.logged = user;
    }

    public User getLoggedUser() {
        return this.logged;
    }

    public void savePeople() throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
        oos.writeObject(people);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    public void loadPeople() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_PATH_FILE));
        people = (List<Person>) ois.readObject();
        ois.close();
    }

    public Boolean searchUser(String userName) {
        boolean found = false;
        if (people == null) {
            found = false;
        } else {
            for (int i = 0; i < people.size() && !found; i++) {
                if (people.get(i) instanceof User) {
                    User user = (User) people.get(i);
                    if (user.getUserName().equals(userName)) {
                        found = true;
                    }
                }
            }
        }
        return found;
    }

    public User userVerification(String userName, String password) {
        User logged = null;
        boolean found = false;
        for (int i = 0; i < people.size() && !found; i++) {
            if (people.get(i) instanceof User) {
                User user = (User) people.get(i);
                if (user.getUserName().equals(userName)) {
                    logged = user;
                    found = true;
                }
            }
        }
        return logged;
    }

    public int getCode() {
        int count = 0;
        for (int i = 0; i < orders.size(); i++) {
            count++;
        }
        return count + 1;
    }

    public String addProduct(String name, ProductType type, List<Ingredients> ingredients, String size, int price,
            User creator) {
        String msg = "";
        Product newProduct = null;
        if (products.isEmpty()) {
            newProduct = new Product(name, type, ingredients, size, price, creator);
            products.add(newProduct);
        } else {
            int i = 0;
            newProduct = new Product(name, type, ingredients, size, price, creator);
            while (i < products.size() && newProduct.compareTo(products.get(i)) > 0) {
                i++;
            }
            for (int j = 0; j < products.size(); j++) {
                if (newProduct.getName().equalsIgnoreCase(products.get(j).getName())) {
                    msg = "You can not added a product with the same name";
                } else {
                    products.add(i, newProduct);
                    msg = "The product " + newProduct.getName() + " have been added succesfully";
                }
            }
        }
        return msg;
    }

    public String setProductInfo(Product product, String newName, ProductType newType, List<Ingredients> newIngredients,
            String newSize, int newPrice, User lastEditor) {
        product.setName(newName);
        product.setType(newType);
        product.setIngredients(newIngredients);
        product.setSize(newSize);
        product.setPrice(newPrice);
        product.setLastEditor(lastEditor);
        return "The Product have been edited succesfully";
    }

    public String deleteProduct(int positionProduct) {
        products.remove(positionProduct);
        return "The product have been deleted succesfully";
    }

    public String setState(Product product, boolean newState) {
        product.setState(newState);
        return "The product state have been changed";
    }

    public String getIngredientsFormated() {
        String ingredients = "";
        for (Ingredients ingredient : this.ingredients) {
            ingredients += ingredient.toString();
        }
        return ingredients;
    }

    public String addIngrendient(String name, User creator) {
        String msg = "";
        Ingredients newIngredient = null;
        if (ingredients.isEmpty()) {
            newIngredient = new Ingredients(name, creator);
            ingredients.add(newIngredient);
        } else {
            int i = 0;
            newIngredient = new Ingredients(name, creator);
            while (i < ingredients.size() && newIngredient.compareTo(ingredients.get(i)) < 0) {
                i++;
            }
            for (int j = 0; j < products.size(); j++) {
                if (newIngredient.getName().equalsIgnoreCase(products.get(j).getName())) {
                    msg = "You can not added an ingredient with the same name";
                } else {
                    ingredients.add(i, newIngredient);
                    msg = "The ingredient " + newIngredient.getName() + " have been added succesfully";
                }
            }
        }
        return msg;
    }

    public String setInfoIngredient(Ingredients ingredient, String name, User lastEditor) {
        ingredient.setName(name);
        ingredient.setLastEditor(lastEditor);
        return "The Ingredient have been edited succesfully";
    }

    public String deleteIngredient(int positionIngredient) {
        products.remove(positionIngredient);
        return "The Ingredient have been deleted succesfully";
    }

    public String setState(Ingredients ingredient, boolean newState) {
        ingredient.setState(newState);
        return "The Ingredient state have been changed";
    }

}
