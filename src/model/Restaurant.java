package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
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
    private int userIndex;
    public static final String FILE_SEPARATOR = ",";
    public static final String SAVE_PATH_FILE_DATA = "data/CasaDorada.report";

    // ------------------------------------------CONSTRUCTOR-------------------------------------
    public Restaurant() {
        products = new ArrayList<>();
        orders = new ArrayList<Order>();
        ingredients = new ArrayList<Ingredients>();
        people = new ArrayList<Person>();
        productType = new ArrayList<ProductType>();
        productSize = new ArrayList<ProductSize>();
    }

    // -------------------------------------------SOME-MINIMUM-METHODS:PEOPLE-----------------------------------------------
    public List<Person> getPeople() {
        return this.people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

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

    public User getLoggedUser(int index) {
        if (index >= 0) {
            return (User) people.get(index);
        } else {
            return null;
        }
    }

    public int getUserIndex() {
        return this.userIndex;
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

    public int searchCostumer(Costumer c) {
        int indexC = 0;
        boolean render = false;
        for (int i = 0; i < people.size() && !render; i++) {
            if (people.get(i) instanceof Costumer) {
                Costumer p = (Costumer) people.get(i);
                if (p.getName().equalsIgnoreCase(c.getName())) {
                    render = true;
                    indexC = p.getId();
                }
            }
        }
        System.out.println(indexC);
        return indexC;
    }

    public Costumer getC(int index) {
        if (index >= 0) {
            return getCostumers(people).get(index);
        } else {
            return null;
        }
    }

    // ----------------------------------------------------SOME-MINIMUM-METHODS:ORDERS--------------------------------------------------

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // --------------------------------------------------SOME-MINIMUM-METHODS:INGREDIENTS---------------------------------------------------
    public List<Ingredients> getIngredients() {
        return this.ingredients;
    }

    public List<Ingredients> getEnableIngredients() {
        List<Ingredients> i = new ArrayList<>();
        for (Ingredients ing : ingredients) {
            if (ing.getState() == true) {
                i.add(ing);
            }
        }
        return i;
    }

    public boolean searchIngredient(String name) {
        boolean render = false;
        for (int i = 0; i < products.size() && !render; i++) {
            Product p = products.get(i);
            for (int j = 0; j < p.getIngredients().size(); j++) {
                if (p.getIngredients().get(j).getName().equalsIgnoreCase(name)) {
                    render = true;
                }
            }
        }
        return render;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public Ingredients getIngredient(int i) {
        if (i >= 0) {
            return ingredients.get(i);
        } else {
            return null;
        }
    }

    public int getNumberIngredients() {
        int count = 0;
        for (int i = 0; i < ingredients.size(); i++) {
            count++;
        }
        return count;
    }

    // --------------------------------------------SOME-MINIMUM-METHODS:PRODUCTS---------------------------------------------------

    public List<Product> getProducts() {
        return this.products;
    }

    public List<Product> getEnableProducts() {
        List<Product> p = new ArrayList<>();
        for (Product pro : products) {
            if (pro.getState() == true) {
                p.add(pro);
            }
        }
        return p;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Product getProduct(int i) {
        if (i >= 0) {
            return products.get(i);
        } else {
            return null;
        }
    }

    public int getNumberProduct() {
        int count = 0;
        for (int i = 0; i < products.size(); i++) {
            count++;
        }
        return count;
    }

    public void setCodeProductPosition() {
        int code = 1;
        for (Product p : products) {
            p.setCode(code);
            code++;
        }
    }

    // ---------------------------------------SOME-MINIMUM-METHODS:PRDODUCT_TYPE---------------------------------------------------

    public ProductType getProductType(int i) {
        return productType.get(i);
    }

    public List<ProductType> getEnableProductTypes() {
        List<ProductType> pt = new ArrayList<>();
        for (ProductType proT : productType) {
            if (proT.getState() == true) {
                pt.add(proT);
            }
        }
        return pt;
    }

    public boolean searchProductType(String name) {
        boolean render = false;
        for (int i = 0; i < products.size() && !render; i++) {
            Product p = products.get(i);
            if (p.getProductType().getName().equalsIgnoreCase(name)) {
                render = true;
            }
        }
        return render;
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

    // --------------------------------------------SOME-MINIMUM-METHODS:PRODUCT_SIZE----------------------------------------------

    public List<ProductSize> getProductSize() {
        return this.productSize;
    }

    public List<ProductSize> getEnableProductSizes() {
        List<ProductSize> ps = new ArrayList<>();
        for (ProductSize proS : productSize) {
            if (proS.getState() == true) {
                ps.add(proS);
            }
        }
        return ps;
    }

    public boolean searchProductSize(String name) {
        boolean render = false;
        for (int i = 0; i < products.size() && !render; i++) {
            Product p = products.get(i);
            if (p.getProductSize().getName().equalsIgnoreCase(name)) {
                render = true;
            }
        }
        return render;
    }

    public ProductSize getProductSize(int i) {
        return productSize.get(i);
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
    public File getDataFile() {
        File file = new File(SAVE_PATH_FILE_DATA);
        return file;
    }

    public void saveData() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream oos = null;
        File file = new File(SAVE_PATH_FILE_DATA);
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(people);
            oos.writeObject(products);
            oos.writeObject(ingredients);
            oos.writeObject(productType);
            oos.writeObject(productSize);
            oos.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("We could not find the path");
            alert.showAndWait();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() throws IOException, ClassNotFoundException {
        if (getDataFile().length() > 0) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_PATH_FILE_DATA)));
            people = (List<Person>) ois.readObject();
            products = (ArrayList<Product>) ois.readObject();
            ingredients = (List<Ingredients>) ois.readObject();
            productType = (List<ProductType>) ois.readObject();
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
        String msg = "";
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof User) {
                User user = (User) (people.get(i));
                if (user == useroToDelete && getLoggedUser(userIndex) != user) {
                    User u = getLoggedUser(userIndex);
                    people.remove(useroToDelete);
                    userVerification(u.getUserName(), u.getPassword());
                    msg = "The user have been deleted succesfully";
                } else {
                    msg = "You can't delete you";
                }
            }
        }
        return msg;
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
            line = br.readLine();
            addPerson(name, lastName, id, userName, password, image, getLoggedUser(userIndex));
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
        loadData();
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
        userIndex = -1;
        boolean found = false;
        if (!people.isEmpty()) {
            for (int i = 0; i < people.size() && !found; i++) {
                if (people.get(i) instanceof User) {
                    User user = (User) people.get(i);
                    if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                        userIndex = i;
                        found = true;
                    }
                }
            }
        }
        return getLoggedUser(userIndex);
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
                User u = getLoggedUser(userIndex);
                people.add(i, newCostumer);
                userVerification(u.getUserName(), u.getPassword());
                msg = "The Costumer " + newCostumer.getName() + " have been added succesfully";
            } else {
                msg = "You can not added a costumer with the same id";
            }

        }
        return msg;
    }

    public int getCostumerIndex(Costumer c) {
        int i = 0;
        while (i < getNumberCostumers() && c.compareTo(getCostumers(getPeople()).get(i)) < 0) {
            i++;
        }
        return i;
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
            people.set(getCostumerIndex(pastCostumer), pastCostumer);
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
                    User u = getLoggedUser(userIndex);
                    people.remove(costumerToDelete);
                    userVerification(u.getUserName(), u.getPassword());
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
            line = br.readLine();
            addPerson(name, lastName, id, address, telephone, suggestion, getLoggedUser(userIndex));
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

    public Costumer searchBinary(int index) {
        CostumersComparator cc = new CostumersComparator();
        Collections.sort(getCostumers(getPeople()), cc);
        boolean found = false;
        int inicio = 0;
        int fin = getCostumers(people).size() - 1;
        int ind = 0;
        int medio = (inicio + fin) / 2;
        while (inicio <= fin && !found) {
                if (getCostumers(people).get(medio).getId() == index) {
                    found = true;
                     ind = medio;
                } else if (getCostumers(people).get(medio).getId() > index) {
                    fin = medio - 1;
                } else {
                    inicio = medio + 1;
                }
                medio = (inicio + fin)/2;
        }
        System.out.println(ind);
        return getC(ind);

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
                    User u = getLoggedUser(userIndex);
                    people.remove(employeeToDelete);
                    userVerification(u.getUserName(), u.getPassword());
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
            line = br.readLine();
            addPerson(name, lastName, id, getLoggedUser(userIndex));
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
                    System.out.println(getLoggedUser(userIndex) == creator);
                }
            }
            if (!added) {
                ingredients.add(newIngredient);
                msg = "The ingredient " + newIngredient.getName() + " have been added succesfully";
            }
        }
        return msg;
    }

    public String setInfoIngredient(Ingredients ingredient, String name, User lastEditor) {
        ingredient.setName(name);
        ingredient.setLastEditor(lastEditor);
        return "The Ingredient have been edited succesfully";
    }

    public String deleteIngredient(int positionIngredient, String name) {
        boolean render = searchIngredient(name);
        if (render == false) {
            ingredients.remove(positionIngredient);
            setCodePosition();
            return "The Ingredient have been deleted succesfully";
        } else {
            return "The Ingredient have a reference by a product";
        }

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
            line = br.readLine();
            addIngredient(code, name, getLoggedUser(userIndex));
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

    public void sortIngredientByName() {
        IngredientsComparator ic = new IngredientsComparator();
        Collections.sort(ingredients, ic);
    }

    public Ingredients searchIndex(Ingredients ing) {
        int ingredientIndex = -1;
        boolean found = false;
        if (!ingredients.isEmpty()) {
            for (int i = 0; i < ingredients.size() && !found; i++) {
                if (ingredients.get(i).getName().equals(ing.getName())) {
                    ingredientIndex = i;
                    found = true;
                }
            }
        }
        return getIngredient(ingredientIndex);
    }

    // ---------------------------------------------------------PRODUCTS-----------------------------------------------

    public String addProduct(String name, ProductType productType, List<Ingredients> ingredient, int code,
            ProductSize productSize, double price, User creator) {
        String msg = "";
        Product newProduct = new Product(name, productType, ingredient, code, productSize, price, creator);
        if (products.isEmpty()) {
            products.add(newProduct);
            msg = "The product " + newProduct.getName() + " have been added succesfully";
        } else {
            boolean added = false;
            for (int j = 0; j < products.size() && !added; j++) {
                if (newProduct.getName().equalsIgnoreCase(products.get(j).getName()) && newProduct.getProductSize() == products.get(j).getProductSize()) {
                    msg = "You can not added an product, because alredy exists";
                    added = true;
                }
            }
            if (!added) {
                products.add(newProduct);
                msg = "The product " + newProduct.getName() + " have been added succesfully";
            }
        }
        return msg;
    }

    public String setProductInfo(Product product, String newName, ProductType newProductType,
            List<Ingredients> newIngredients, int newCode, ProductSize newProductSize, double newPrice,
            User lastEditor) {
        product.setName(newName);
        product.setProductType(newProductType);
        product.setProductSize(newProductSize);
        product.setIngredients(newIngredients);
        product.setPrice(newPrice);
        product.setLastEditor(lastEditor);
        return "The Product have been edited succesfully";
    }

    public String deleteProduct(int positionProduct) {
        products.remove(positionProduct);
        setCodeProductPosition();
        return "The product have been deleted succesfully";
    }

    public String disableProduct(Product p) {
        p.setState(false);
        return "The product have been disabled succesfully";
    }

    public String enableProduct(Product p) {
        p.setState(true);
        return "The product have been enabled succesfully";
    }

    public void importDataProduct(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            String name = parts[0];
            int code = Integer.parseInt(null);
            line = br.readLine();
            addProductSize(name, code, getLoggedUser(userIndex));
        }
        br.close();
    }

    public void exportDataProduct(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        for (int i = 0; i < getProductSize().size(); i++) {
            ProductSize ps = getProductSize().get(i);
            pw.println(ps.getName() + " ");
        }
        pw.close();
    }

    public void sortProductByPrice() {
        for (int i = 0; i < products.size(); i++) {
            for (int j = i; j > 0 && products.get(j - 1).getPrice() > products.get(j).getPrice(); j--) {
                Product temp = products.get(j);
                products.set(j, products.get(j - 1));
                products.set(j - 1, temp);
            }
        }
    }

    public int getCodeProduct() {
        int count = 0;
        for (int i = 0; i < products.size(); i++) {
            count++;
        }
        return count + 1;
    }

    public Product searchIndex(Product pro) {
        int productIndex = -1;
        boolean found = false;
        if (!products.isEmpty()) {
            for (int i = 0; i < products.size() && !found; i++) {
                if (products.get(i).getName().equals(pro.getName())) {
                    productIndex = i;
                    found = true;
                }
            }
        }
        return getProduct(productIndex);
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
            for (int j = 0; j < productType.size() && !added; j++) {
                if (newProductType.getName().equalsIgnoreCase(productType.get(j).getName())) {
                    msg = "You can not added an product type with the same name";
                    added = true;
                }
            }
            if (!added) {
                productType.add(newProductType);
                msg = "The product type " + newProductType.getName() + " have been added succesfully";
            }
        }
        return msg;
    }

    public String setProductType(ProductType productType, String name, User lastEditor) {
        productType.setName(name);
        productType.setLastEditor(lastEditor);
        return "The product type have been edited succesfully";
    }

    public String deleteProductType(int positionProductType, String name) {
        boolean render = searchProductType(name);
        if (render == false) {
            productType.remove(positionProductType);
            setCodeProductTypePosition();
            return "The product type have been deleted succesfully";
        } else {
            return "The product type have a reference by a product";
        }
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
                }
            }
            if (!added) {
                productSize.add(newProductSize);
                msg = "The product size " + newProductSize.getName() + " have been added succesfully";
            }
        }
        return msg;
    }

    public String setProductSize(ProductSize productSize, String name, User lastEditor) {
        productSize.setName(name);
        productSize.setLastEditor(lastEditor);
        return "The product size have been edited succesfully";
    }

    public String deleteProductSize(int positionProductSize, String name) {
        boolean render = searchProductSize(name);
        if (render == false) {
            productSize.remove(positionProductSize);
            setCodeProductSizePosition();
            return "The product size have been deleted succesfully";
        } else {
            return "The product size have a reference by a product";
        }
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
            line = br.readLine();
            addProductSize(name, code, getLoggedUser(userIndex));
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

    // ----------------------------------------------ORDERS----------------------------------------------------

    public String addOrders(int code, State state, List<Product> products, List<Integer> amount, Costumer costumer,
            Employee employee, String date, String suggestion, User creator, double totalPrice, String hour) {
        String msg = "";
        System.out.println(products.get(0).getName());
        Order newOrder = new Order(code, state, products, amount, costumer, employee, date, suggestion, creator,
                totalPrice, hour);
        orders.add(newOrder);
        msg = "The order " + newOrder.getCode() + " have been added succesfully";
        return msg;
    }

    public int getCodeOrder() {
        int count = 0;
        for (int i = 0; i < orders.size(); i++) {
            count++;
        }
        return count + 1;
    }
}
