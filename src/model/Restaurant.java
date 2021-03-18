package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Restaurant {
    private List<Product> products;
    private List<Order> orders;
    private List<Ingredients> ingredients;
    private List<User> users;
    private User logged;
    public static final String FILE_SEPARATOR = "\\ ";
    public static final String SAVE_PATH_FILE = "data/users.report";

    public Restaurant() {
        products = new ArrayList<Product>();
        orders = new ArrayList<Order>();
        ingredients = new ArrayList<Ingredients>();
        users = new ArrayList<User>();
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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
        users.add(new User(name, lastName, id, userName, password, path));
    }

    public void setLoggedUser(User user) {
        this.logged = user;
    }

    public User getLoggedUser() {
        return this.logged;
    }

    public void saveUsers() throws FileNotFoundException, IOException {
        ObjectOutputStream oos = null;
        File file = new File(SAVE_PATH_FILE);
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(users);
            oos.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("We could not find the path");
            alert.showAndWait();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadUsers() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_PATH_FILE)));
        users = (List<User>) ois.readObject();
        ois.close();

    }

    public Boolean searchUser(String userName) {
        boolean found = false;
        if (users == null) {
            found = false;
        } else {
            for (int i = 0; i < users.size() && !found; i++) {
                if (users.get(i) instanceof User) {
                    User user = (User) users.get(i);
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
        if (!users.isEmpty()) {
            for (int i = 0; i < users.size() && !found; i++) {
                if (users.get(i) instanceof User) {
                    User user = (User) users.get(i);
                    if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                        logged = user;
                        found = true;
                    }
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
