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
    private List<Costumer> costumers;
    private User logged;
    public static final String FILE_SEPARATOR = "\\;";
    public static final String SAVE_PATH_FILE = "data/users.report";
    public static final String SAVE_PATH_FILE_COSTUMERS = "data/costumers.report";

    public Restaurant() {
        products = new ArrayList<Product>();
        orders = new ArrayList<Order>();
        ingredients = new ArrayList<Ingredients>();
        users = new ArrayList<User>();
        costumers = new ArrayList<Costumer>();
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

    public List<Costumer> getCostumers() {
        return this.costumers;
    }

    public void setCostumers(List<Costumer> costumers) {
        this.costumers = costumers;
    }

    public User getLogged() {
        return this.logged;
    }

    public void setLogged(User logged) {
        this.logged = logged;
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
                    User user = users.get(i);
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

    // BASE PRODUCT

    public BaseProduct addBaseProduct(String name, ProductType productType, List<Ingredients> ingredients, int code) {
        BaseProduct baseProduct = new BaseProduct(name, productType, ingredients, code);
        return baseProduct;
    }

    public String addProduct(BaseProduct baseProduct, ProductSize productSize, double price, User creator,
            User lastEditor, boolean state) {
        String msg = "";
        Product newProduct = null;
        for (int j = 0; j < products.size(); j++) {
            if (baseProduct.getName().equalsIgnoreCase(products.get(j).getBaseProduct().getName())) {
                msg = "You can not added a product with the same name";
            } else {
                newProduct = new Product(baseProduct, productSize, price, creator, lastEditor, state);
                products.add(j, newProduct);
                msg = "The product " + newProduct.getBaseProduct().getName() + " have been added succesfully";
            }
        }
        return msg;
    }

    public String setProductInfo(Product product, BaseProduct newBaseProduct, ProductSize newProductSize,
            double newPrice, User lastEditor, boolean newState) {
        product.setBaseProduct(newBaseProduct);
        product.setProductSize(newProductSize);
        product.setPrice(newPrice);
        product.setLastEditor(lastEditor);
        product.setState(newState);
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

    /*
     * public void importDataProducts(String fileName) throws IOException {
     * BufferedReader br = new BufferedReader(new FileReader(fileName)); String line
     * = br.readLine(); while(line != null){ String[] parts =
     * line.split(FILE_SEPARATOR); String name = parts[0]; ProductType type =
     * ProductType.toString(parts[1]); List<Ingredients> ingredients =
     * Ingredients.toString(parts[2]); String size = parts[3]; int price =
     * Integer.parseInt(parts[4]); User creator = User.toString(parts[5]); line =
     * br.readLine(); addProduct(name, type, ingredients, size, price, creator); }
     * br.close(); }
     */

    // INGREDIENTS

    public String getIngredientsFormated() {
        String ingredients = "";
        for (Ingredients ingredient : this.ingredients) {
            ingredients += ingredient.toString();
        }
        return ingredients;
    }

    /*
     * public String addIngrendient(String name, User creator) { String msg = "";
     * Ingredients newIngredient = null; if (ingredients.isEmpty()) { newIngredient
     * = new Ingredients(name, creator); ingredients.add(newIngredient); } else {
     * int i = 0; newIngredient = new Ingredients(name, creator); while (i <
     * ingredients.size() && newIngredient.compareTo(ingredients.get(i)) < 0) { i++;
     * } for (int j = 0; j < products.size(); j++) { / if
     * (newIngredient.getName().equalsIgnoreCase(products.get(j).getName())) { msg =
     * "You can not added an ingredient with the same name"; } else {
     * ingredients.add(i, newIngredient); msg = "The ingredient " +
     * newIngredient.getName() + " have been added succesfully"; } } } return msg; }
     */

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

    // COSTUMERS

    public void saveCostumers() throws FileNotFoundException, IOException {
        ObjectOutputStream oos = null;
        File file = new File(SAVE_PATH_FILE_COSTUMERS);
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(costumers);
            oos.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("We could not find the path");
            alert.showAndWait();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadCostumers() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_PATH_FILE_COSTUMERS)));
        costumers = (List<Costumer>) ois.readObject();
        ois.close();
    }

    public void importDataCostumers(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            String name = parts[0];
            String lastName = parts[1];
            int id = Integer.parseInt(parts[2]);
            String address = parts[3];
            int telephone = Integer.parseInt(parts[4]);
            String suggestion = parts[5];
            String creator = "";
            line = br.readLine();
            addCostumer(name, lastName, id, address, telephone, suggestion, creator);
        }
        br.close();
    }

    /*
     * public int binarySearchCostumer(List<Costumer> costumers, Costumer x){ int
     * pos = -1; int i = 0; int j = costumers.size()-1; while(i<= j && pos<0){ int m
     * = (i+j)/2;
     * 
     * if(costumers.get(m).equals(x)){ pos = m; } else
     * if(x.compareTo(costumers.get(m)>0)){ i = m+1; } else{ j = m-1; } } return
     * pos; }
     */

    public String addCostumer(String name, String lastName, int id, String address, int telephone, String suggestions,
            String creator) {
        String msg = "";
        Costumer newCostumer = null;
        if (costumers.isEmpty()) {
            newCostumer = new Costumer(name, lastName, id, address, telephone, suggestions, creator);
            costumers.add(newCostumer);
        } else {
            int i = 0;
            newCostumer = new Costumer(name, lastName, id, address, telephone, suggestions, creator);
            while (i < costumers.size() && newCostumer.compareTo(costumers.get(i)) > 0) {
                i++;
            }
            for (int j = 0; j < costumers.size(); j++) {
                if (newCostumer.getName().equalsIgnoreCase(costumers.get(j).getName())) {
                    msg = "You can not added a product with the same name";
                } else {
                    costumers.add(i, newCostumer);
                    msg = "The product " + newCostumer.getName() + " have been added succesfully";
                }
            }
        }
        return msg;
    }

    public String setInfoCostumer(Costumer costumer, String name, String lastName, int id, String address,
            int telephone, String suggestions, String lastEditor) {
        costumer.setName(name);
        costumer.setLastName(lastName);
        costumer.setId(id);
        costumer.setAddress(address);
        costumer.setTelephone(telephone);
        costumer.setSuggestions(suggestions);
        costumer.setLastEditor(lastEditor);
        return "The Costumer have been edited succesfully";
    }

}
