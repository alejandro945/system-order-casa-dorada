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
    private List<Person> people;
    private List<ProductType> productType;
    private List<ProductSize> productSize;
    private User logged;
    public static final String FILE_SEPARATOR = ",";
    public static final String SAVE_PATH_FILE_PEOPLE = "data/people.report";
    public static final String SAVE_PATH_FILE_INGREDIENTS = "data/ingredients.report";
    public static final String SAVE_PATH_FILE_PRODUCT_TYPE = "data/productType.report";
    public static final String SAVE_PATH_FILE_PRODUCT_SIZE = "data/productSize.report";

    // ------------------------------------------CONSTRUCTOR-------------------------------------
    public Restaurant() {
        products = new ArrayList<Product>();
        orders = new ArrayList<Order>();
        ingredients = new ArrayList<Ingredients>();
        people = new ArrayList<Person>();
        productType = new ArrayList<ProductType>();
        productSize = new ArrayList<ProductSize>();
    }

    // ----------------------------------------GETS&SETS-----------------------------------------
    public List<User> getUsers(List<Person> people) {
        List<User> userlist = new ArrayList<>();
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof User) {
                User user = (User) (people.get(i));
                userlist.add(user);
            }
        }
        return userlist;
    }

    public int getUsers() {
        int count = 0;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof User) {
                count++;
            }
        }
        return count;
    }

    public List<Employee> getEmployees(List<Person> people) {
        List<Employee> employeelist = new ArrayList<>();
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof Employee) {
                Employee employee = (Employee) (people.get(i));
                employeelist.add(employee);
            }
        }
        return employeelist;
    }

    public int getEmployees() {
        int count = 0;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof Employee) {
                count++;
            }
        }
        return count;
    }

    public List<Costumer> getCostumers(List<Person> people) {
        List<Costumer> costumerlist = new ArrayList<>();
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof Costumer) {
                Costumer costumer = (Costumer) (people.get(i));
                costumerlist.add(costumer);
            }
        }
        return costumerlist;
    }

    public int getCostumers() {
        int count = 0;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof Costumer) {
                count++;
            }
        }
        return count;
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

    public int getNumberIngredients() {
        int count = 0;
        for (int i = 0; i < ingredients.size(); i++) {
            count++;
        }
        return count;
    }

    public List<Person> getPeople() {
        return this.people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public User getLogged() {
        return this.logged;
    }

    public void setLogged(User logged) {
        this.logged = logged;
    }

    public void setLoggedUser(User user) {
        this.logged = user;
    }

    public User getLoggedUser() {
        return this.logged;
    }

    public List<ProductType> getProductType() {
        return this.productType;
    }

    public void setProductType(List<ProductType> productType) {
        this.productType = productType;
    }

    public int getNumberProductType() {
        int count = 0;
        for (int i = 0; i < productType.size(); i++) {
            count++;
        }
        return count;
    }

    public List<ProductSize> getProductSize() {
        return this.productSize;
    }

    public void setProductSize(List<ProductSize> productSize) {
        this.productSize = productSize;
    }

    public int getNumberProductSize() {
        int count = 0;
        for (int i = 0; i < productSize.size(); i++) {
            count++;
        }
        return count;
    }

    // ---------------------------------------------PERSISTENCE------------------------------------------------
    public File getPeopleFile() {
        File file = new File(SAVE_PATH_FILE_PEOPLE);
        return file;
    }

    public File getIngredientFile() {
        File file = new File(SAVE_PATH_FILE_INGREDIENTS);
        return file;
    }

    public File getProductTypeFile() {
        File file = new File(SAVE_PATH_FILE_PRODUCT_TYPE);
        return file;
    }

    public File getProductSizeFile() {
        File file = new File(SAVE_PATH_FILE_PRODUCT_SIZE);
        return file;
    }

    public void savePeople() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream oos = null;
        File file = new File(SAVE_PATH_FILE_PEOPLE);
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(people);
            oos.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("We could not find the path");
            alert.showAndWait();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadPeople() throws IOException, ClassNotFoundException {
        if (getPeopleFile().length() > 0) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_PATH_FILE_PEOPLE)));
            people = (List<Person>) ois.readObject();
            ois.close();
        }
    }

    public void saveIngredients() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream oos = null;
        File file = new File(SAVE_PATH_FILE_INGREDIENTS);
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(ingredients);
            oos.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("We could not find the path");
            alert.showAndWait();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadIngredients() throws IOException, ClassNotFoundException {
        if (getIngredientFile().length() > 0) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_PATH_FILE_INGREDIENTS)));
            ingredients = (List<Ingredients>) ois.readObject();
            ois.close();
        }
    }

    public void saveProductType() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream oos = null;
        File file = new File(SAVE_PATH_FILE_PRODUCT_TYPE);
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(productType);
            oos.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("We could not find the path");
            alert.showAndWait();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadProductType() throws IOException, ClassNotFoundException {
        if (getProductTypeFile().length() > 0) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_PATH_FILE_PRODUCT_TYPE)));
            productType = (List<ProductType>) ois.readObject();
            ois.close();
        }
    }

    public void saveProductSize() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream oos = null;
        File file = new File(SAVE_PATH_FILE_PRODUCT_SIZE);
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(productSize);
            oos.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("We could not find the path");
            alert.showAndWait();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadProductSize() throws IOException, ClassNotFoundException {
        if (getProductSizeFile().length() > 0) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_PATH_FILE_PRODUCT_SIZE)));
            productSize = (List<ProductSize>) ois.readObject();
            ois.close();
        }
    }

    // -------------------------------------------------USERS--------------------------------------------------
    public String addPerson(String name, String lastName, int id, String userName, String password, String image,
            User creator) throws FileNotFoundException, IOException {
        String msg = "";
        User newUser = new User(name, lastName, id, userName, password, image, creator);
        if (people.size() > 0) {
            boolean founded = validateidCreating(newUser);
            if (founded == false) {
                people.add(newUser);
                msg = "The user " + newUser.getName() + " have been added succesfully";
            } else {
                msg = "You can not added a User with the same id";
            }
        } else {
            people.add(newUser);
        }
        return msg;
    }

    public String setUserInfo(User newUser, User pastUser, String newName, String newLastName, int newId,
            String newUserName, String path, User newLastEditor) {
        if (!validateidUpdating(newUser, pastUser) && !searchUser(newUser, pastUser)) {
            pastUser.setName(newName);
            pastUser.setLastName(newLastName);
            pastUser.setId(newId);
            pastUser.setUserName(newUserName);
            pastUser.setImage(path);
            pastUser.setLastEditor(newLastEditor);
            return "The user have been edited succesfully";
        } else {
            return "The user could not been edited.(Same ID or Username)";
        }
    }

    public String deleteUser(User useroToDelete) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof User) {
                User user = (User) (people.get(i));
                if (user == useroToDelete) {
                    people.remove(useroToDelete);
                }
            }
        }
        return "The user have been deleted succesfully";
    }

    public String disableUser(User user) {
        user.setState(false);
        return "The user have been disabled succesfully";
    }

    public String enableUser(User user) {
        user.setState(true);
        return "The user have been enabled succesfully";
    }

    public void importDataUsers(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            String name = parts[0];
            String lastName = parts[1];
            int id = Integer.parseInt(parts[2]);
            String userName = parts[3];
            String password = parts[4];
            String image = parts[5];
            User creator = null;
            line = br.readLine();
            addPerson(name, lastName, id, userName, password, image, creator);
        }
        br.close();
    }

    public void exportDataUsers(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        for (int i = 0; i < getUsers(people).size(); i++) {
            User u = getUsers(people).get(i);
            pw.println(u.getName() + " ");
        }
        pw.close();
    }

    public Boolean searchUser(User newUser, User pastUser) {
        boolean found = false;
        if (people.isEmpty()) {
            found = false;
        } else {
            for (int i = 0; i < people.size() && !found; i++) {
                if (people.get(i) instanceof User) {
                    User usr = (User) (people.get(i));
                    if (!pastUser.equals(usr)) {
                        if (newUser.getUserName().equals(usr.getUserName())) {
                            found = true;
                        }
                    }
                }
            }
        }
        return found;
    }

    public Boolean searchUser(String userName) {
        boolean found = false;
        if (people.isEmpty()) {
            found = false;
        } else {
            for (int i = 0; i < getUsers(people).size() && !found; i++) {
                if (userName.equals(getUsers(people).get(i).getUserName())) {
                    found = true;
                }
            }
        }
        return found;
    }

    public boolean validateidCreating(User user) {
        boolean found = false;
        for (int j = 0; j < getEmployees(people).size() && !found; j++) {
            if (user != getEmployees(people).get(j)) {
                if (user.getId() == getEmployees(people).get(j).getId()) {
                    found = true;
                }
            }
        }
        for (int j = 0; j < getCostumers(people).size() && !found; j++) {
            if (user.getId() == getCostumers(people).get(j).getId()) {
                found = true;
            }
        }
        return found;
    }

    public boolean validateidUpdating(User newUser, User pastUser) {
        boolean found = false;
        for (int i = 0; i < people.size() && !found; i++) {
            if (people.get(i) instanceof Employee) {
                Employee emp = (Employee) (people.get(i));
                if (!pastUser.equals(emp)) {
                    if (newUser.getId() == emp.getId()) {
                        found = true;
                    }

                }
            }
        }
        for (int j = 0; j < people.size() && !found; j++) {
            if (people.get(j) instanceof Costumer) {
                Costumer c = (Costumer) (people.get(j));
                if (newUser.getId() == c.getId()) {
                    found = true;
                }
            }
        }
        return found;
    }

    public boolean usersDisabled() throws ClassNotFoundException, IOException {
        loadPeople();
        boolean disabled = false;
        for (User user : getUsers(people)) {
            if (user.getState() == true) {
                disabled = false;
            } else {
                disabled = true;
            }
        }
        return disabled;
    }

    public User userVerification(String userName, String password) {
        User logged = null;
        boolean found = false;
        if (!people.isEmpty()) {
            for (int i = 0; i < people.size() && !found; i++) {
                if (people.get(i) instanceof User) {
                    User user = (User) people.get(i);
                    if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                        logged = user;
                        found = true;
                    }
                }
            }
        }
        return logged;
    }

    // -----------------------------------------------------COSTUMERS---------------------------------------
    public int getNumberCostumers() {
        int count = 0;
        for (Person person : people) {
            if (person instanceof Costumer) {
                count++;
            }
        }
        return count;
    }

    public String addPerson(String name, String lastName, int id, String address, int telephone, String suggestions,
            User creator) {
        String msg = "We could not add the costumer";
        Costumer newCostumer = null;
        if (getNumberCostumers() == 0) {
            newCostumer = new Costumer(name, lastName, id, address, telephone, suggestions, creator);
            people.add(newCostumer);
            msg = "The Costumer " + newCostumer.getName() + " have been added succesfully";
        } else {
            int i = 0;
            newCostumer = new Costumer(name, lastName, id, address, telephone, suggestions, creator);
            while (i < getNumberCostumers() && newCostumer.compareTo(getCostumers(getPeople()).get(i)) < 0) {
                i++;
            }
            boolean repeated = validateidCreating(newCostumer);
            if (repeated == false) {
                people.add(i, newCostumer);
                msg = "The Costumer " + newCostumer.getName() + " have been added succesfully";
            } else {
                msg = "You can not added a costumer with the same id";
            }

        }
        return msg;
    }

    public String setInfoCostumer(Costumer pastCostumer, String newName, String newLastName, int newId,
            String newAddress, int newTelephone, String newSuggestions, User newLastEditor) {
        if (!validateidUpdating(newId, pastCostumer)) {
            pastCostumer.setName(newName);
            pastCostumer.setLastName(newLastName);
            pastCostumer.setId(newId);
            pastCostumer.setAddress(newAddress);
            pastCostumer.setTelephone(newTelephone);
            pastCostumer.setSuggestions(newSuggestions);
            pastCostumer.setLastEditor(newLastEditor);
            return "The Costumer have been edited succesfully";
        } else {
            return "The Costumer could not been edited (Same id)";
        }
    }

    public String deleteCostumer(Costumer costumerToDelete) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof Costumer) {
                Costumer costumer = (Costumer) (people.get(i));
                if (costumer == costumerToDelete) {
                    people.remove(costumerToDelete);
                }
            }
        }
        return "The costumer have been deleted succesfully";
    }

    public String disableCostumer(Costumer costumer) {
        costumer.setState(false);
        return "The Costumer have been disabled succesfully";

    }

    public String enableCostumer(Costumer costumer) {
        costumer.setState(true);
        return "The Costumer have been enabled succesfully";
    }

    public boolean validateidCreating(Costumer costumer) {
        boolean found = false;
        for (int j = 0; j < getCostumers(people).size() && !found; j++) {
            if (costumer != getCostumers(people).get(j)) {
                if (costumer.getId() == getCostumers(people).get(j).getId()) {
                    found = true;
                }
            }
        }
        for (int j = 0; j < getEmployees(people).size() && !found; j++) {
            if (costumer.getId() == getEmployees(people).get(j).getId()) {
                found = true;
            }
        }
        return found;
    }

    public boolean validateidUpdating(int newId, Costumer pastCostumer) {
        boolean found = false;
        for (int j = 0; j < people.size() && !found; j++) {
            if (people.get(j) instanceof Costumer) {
                Costumer c = (Costumer) (people.get(j));
                if (!pastCostumer.equals(c)) {
                    if (newId == c.getId()) {
                        found = true;
                    }
                }
            }
        }
        for (int i = 0; i < people.size() && !found; i++) {
            if (people.get(i) instanceof Employee) {
                Employee emp = (Employee) (people.get(i));
                if (newId == emp.getId()) {
                    found = true;
                }
            }
        }
        return found;
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
            User creator = null;
            line = br.readLine();
            addPerson(name, lastName, id, address, telephone, suggestion, creator);
        }
        br.close();
    }

    public void exportDataCostumers(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        for (int i = 0; i < getCostumers(people).size(); i++) {
            Costumer c = getCostumers(people).get(i);
            pw.println(c.getName() + " ");
        }
        pw.close();
    }

    // -------------------------------------------EMPLOYEEES-----------------------------------------------------
    public String addPerson(String name, String lastName, int id, User creator) {
        String msg = "";
        Employee newEmployee = new Employee(name, lastName, id, creator);
        boolean found = validateidCreating(newEmployee);
        if (found == false) {
            people.add(newEmployee);
            msg = "The employee " + newEmployee.getName() + " have been added succesfully";
        } else {
            msg = "You can not added the employee with the same id";
        }
        return msg;
    }

    public String setInfoEmployee(Employee employee, String newName, String newLastName, int newId, User lastEditor) {
        if (!validateidUpdating(newId, employee)) {
            employee.setName(newName);
            employee.setLastName(newLastName);
            employee.setId(newId);
            employee.setLastEditor(lastEditor);
            return "The employee have been edited succesfully";
        } else {
            return "The employee could not been edited";
        }
    }

    public String deleteEmployee(Employee employeeToDelete) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof Employee) {
                Employee employee = (Employee) (people.get(i));
                if (employee == employeeToDelete) {
                    people.remove(employeeToDelete);
                }
            }
        }
        return "The employee have been deleted succesfully";
    }

    public String disableEmployee(Employee employee) {
        employee.setState(false);
        return "The employee have been disabled succesfully";

    }

    public String enableEmployee(Employee employee) {
        employee.setState(true);
        return "The employee have been enabled succesfully";
    }

    public void importDataEmployees(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            String name = parts[0];
            String lastName = parts[1];
            int id = Integer.parseInt(parts[2]);
            User creator = null;
            line = br.readLine();
            addPerson(name, lastName, id, creator);
        }
        br.close();
    }

    public void exportDataEmployees(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        for (int i = 0; i < getEmployees(people).size(); i++) {
            Employee e = getEmployees(people).get(i);
            pw.println(e.getName() + " ");
        }
        pw.close();
    }

    public boolean validateidCreating(Employee employee) {
        boolean found = false;
        for (int j = 0; j < getEmployees(people).size() && !found; j++) {
            if (employee != getEmployees(people).get(j)) {
                if (employee.getId() == getEmployees(people).get(j).getId()) {
                    found = true;
                }
            }
        }
        for (int j = 0; j < getCostumers(people).size() && !found; j++) {
            if (employee.getId() == getCostumers(people).get(j).getId()) {
                found = true;
            }
        }
        return found;
    }

    public boolean validateidUpdating(int newId, Employee pastEmployee) {
        boolean found = false;
        for (int j = 0; j < people.size() && !found; j++) {
            if (people.get(j) instanceof Employee) {
                Employee e = (Employee) (people.get(j));
                if (!pastEmployee.equals(e)) {
                    if (newId == e.getId()) {
                        found = true;
                    }
                }
            }
        }
        for (int i = 0; i < people.size() && !found; i++) {
            if (people.get(i) instanceof Costumer) {
                Costumer c = (Costumer) (people.get(i));
                if (newId == c.getId()) {
                    found = true;
                }
            }
        }
        return found;
    }

    // -------------------------------------------INGREDIENTS-------------------------------------------------
    public String getIngredientsFormated() {
        String ingredients = "";
        for (Ingredients ingredient : this.ingredients) {
            ingredients += ingredient.toString();
        }
        return ingredients;
    }

    public String addIngredient(int code, String name, User creator) {
        String msg = "";
        Ingredients newIngredient = new Ingredients(code, name, creator);
        if (ingredients.isEmpty()) {
            ingredients.add(newIngredient);
            msg = "The ingredient " + newIngredient.getName() + " have been added succesfully";
        } else {
            boolean added = false;
            for (int j = 0; j < ingredients.size() && !added; j++) {
                if (newIngredient.getName().equalsIgnoreCase(ingredients.get(j).getName())) {
                    msg = "You can not added an ingredient with the same name";
                    added = true;
                } else {
                    ingredients.add(newIngredient);
                    msg = "The ingredient " + newIngredient.getName() + " have been added succesfully";
                    added = true;
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
        ingredients.remove(positionIngredient);
        setCodePosition();
        return "The Ingredient have been deleted succesfully";
    }

    public String disableIngredient(Ingredients ingredient) {
        ingredient.setState(false);
        return "The Ingredient have been disabled succesfully";
    }

    public String enableIngredient(Ingredients ingredient) {
        ingredient.setState(true);
        return "The Ingredient have been enabled succesfully";
    }

    public void importDataIngredients(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            int code = Integer.parseInt(null);
            String name = parts[0];
            User creator = null;
            line = br.readLine();
            addIngredient(code, name, creator);
        }
        br.close();
    }

    public void exportDataIngredients(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        for (int i = 0; i < getIngredients().size(); i++) {
            Ingredients in = getIngredients().get(i);
            pw.println(in.getName() + " ");
        }
        pw.close();
    }

    public void setCodePosition() {
        int code = 1;
        for (Ingredients ingredient : ingredients) {
            ingredient.setCode(code);
            code++;
        }
    }

    public int getCode() {
        int count = 0;
        for (int i = 0; i < ingredients.size(); i++) {
            count++;
        }
        return count + 1;
    }

 
    // -------------------------------------------------------BASE-PRODUCT-------------------------------------------

    public BaseProduct addBaseProduct(String name, ProductType productType, List<Ingredients> ingredients, int code) {
        BaseProduct baseProduct = new BaseProduct(name, productType, ingredients, code);
        return baseProduct;
    }

    // ---------------------------------------------------------PRODUCTS-----------------------------------------------

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
    
    public void sortProductByPrice(){
        for(int i = 0; i < products.size(); i++){
            for (int j=i; j > 0 && products.get(j-1).getPrice() > products.get(j).getPrice(); j--){
                Product temp = products.get(j);
                products.set(j, products.get(j-1));
                products.set(j-1, temp);
            }
        }
    }

    // -----------------------------------------PRODUCT_TYPE----------------------------------------------

    public String addProductType(String name, User creator, int code) {
        String msg = "";
        ProductType newProductType = new ProductType(name, creator, code);
        if (productType.isEmpty()) {
            productType.add(newProductType);
            msg = "The product type " + newProductType.getName() + " have been added succesfully";
        } else {
            boolean added = false;
            for (int j = 0; j < ingredients.size() && !added; j++) {
                if (newProductType.getName().equalsIgnoreCase(productType.get(j).getName())) {
                    msg = "You can not added an product type with the same name";
                    added = true;
                } else {
                    productType.add(newProductType);
                    msg = "The product type " + newProductType.getName() + " have been added succesfully";
                    added = true;
                }
            }
        }
        return msg;
    }

    public String setProductType(ProductType productType, String name, User lastEditor) {
        productType.setName(name);
        productType.setLastEditor(lastEditor);
        return "The product type have been edited succesfully";
    }

    public String deleteProductType(int positionProductType) {
        productType.remove(positionProductType);
        setCodeProductTypePosition();
        return "The product type have been deleted succesfully";
    }

    public String disableProductType(ProductType pType) {
        pType.setState(false);
        return "The product type have been disabled succesfully";
    }

    public String enableProductType(ProductType pType) {
        pType.setState(true);
        return "The product type have been enabled succesfully";
    }

    public void importDataProductType(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            String name = parts[0];
            User creator = null;
            int code = Integer.parseInt(null);
            line = br.readLine();
            addProductType(name, creator, code);
        }
        br.close();
    }

    public void exportDataProductType(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        for (int i = 0; i < getProductType().size(); i++) {
            ProductType pt = getProductType().get(i);
            pw.println(pt.getName() + " ");
        }
        pw.close();
    }

    public void setCodeProductTypePosition() {
        int code = 1;
        for (ProductType ptype : productType) {
            ptype.setCode(code);
            code++;
        }
    }

    public int getCodeProductType() {
        int count = 0;
        for (int i = 0; i < productType.size(); i++) {
            count++;
        }
        return count + 1;
    }

    // -----------------------------------------PRODUCT_SIZE----------------------------------------------

    public String addProductSize(String name, int code, User creator) {
        String msg = "";
        ProductSize newProductSize = new ProductSize(name, code, creator);
        if (productSize.isEmpty()) {
            productSize.add(newProductSize);
            msg = "The product size " + newProductSize.getName() + " have been added succesfully";
        } else {
            boolean added = false;
            for (int j = 0; j < productSize.size() && !added; j++) {
                if (newProductSize.getName().equalsIgnoreCase(productSize.get(j).getName())) {
                    msg = "You can not added an product size with the same name";
                    added = true;
                } else {
                    productSize.add(newProductSize);
                    msg = "The product size " + newProductSize.getName() + " have been added succesfully";
                    added = true;
                }
            }
        }
        return msg;
    }

    public String setProductSize(ProductSize productSize, String name, User lastEditor) {
        productSize.setName(name);
        productSize.setLastEditor(lastEditor);
        return "The product size have been edited succesfully";
    }

    public String deleteProductSize(int positionProductSize) {
        productSize.remove(positionProductSize);
        setCodeProductSizePosition();
        return "The product size have been deleted succesfully";
    }

    public String disableProductSize(ProductSize pSize) {
        pSize.setState(false);
        return "The product size have been disabled succesfully";
    }

    public String enableProductSize(ProductSize pSize) {
        pSize.setState(true);
        return "The product size have been enabled succesfully";
    }

    public void importDataProductSize(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            String name = parts[0];
            int code = Integer.parseInt(null);
            User creator = null;
            line = br.readLine();
            addProductSize(name, code, creator);
        }
        br.close();
    }

    public void exportDataProductSize(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        for (int i = 0; i < getProductSize().size(); i++) {
            ProductSize ps = getProductSize().get(i);
            pw.println(ps.getName() + " ");
        }
        pw.close();
    }

    public void setCodeProductSizePosition() {
        int code = 1;
        for (ProductSize pSize : productSize) {
            pSize.setCode(code);
            code++;
        }
    }

    public int getCodeProductSize() {
        int count = 0;
        for (int i = 0; i < productSize.size(); i++) {
            count++;
        }
        return count + 1;
    }

}