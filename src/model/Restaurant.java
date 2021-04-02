package model;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private List<BaseProduct> baseProducts;
    private int userIndex;
    public static final String FILE_SEPARATOR = ",";
    public static final String SAVE_PATH_FILE_DATA = "data/CasaDorada.report";

    // ----------------------------------------------CONSTRUCTOR------------------------------------------------
    public Restaurant() {
        products = new ArrayList<>();
        orders = new ArrayList<Order>();
        ingredients = new ArrayList<Ingredients>();
        people = new ArrayList<Person>();
        productType = new ArrayList<ProductType>();
        productSize = new ArrayList<ProductSize>();
        baseProducts = new ArrayList<BaseProduct>();
    }

    // -------------------------------------------SOME-MINIMUM-METHODS:PEOPLE-----------------------------------------------
    public List<Person> getPeople() {
        return this.people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    // ------------------------------------------------------INSTANCES-LISTS-------------------------------------------------
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

    // ----------------------------------------------GET-ADMIN-AND-LOGGED--------------------------------------------------
    public User getAdmin() {
        User admin = null;
        if (people.isEmpty()) {
            return admin;
        } else {
            boolean render = false;
            for (int i = 0; i < getUsers(people).size() && !render; i++) {
                if (getUsers(people).get(i).getState() == true) {
                    render = true;
                    admin = getUsers(people).get(i);
                }
            }
        }
        return admin;
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

    // ---------------------------------------------HOME-CHART-------------------------------------------------------
    public int countUsers() {
        int count = 0;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof User) {
                count++;
            }
        }
        return count;
    }

    public int countEmployees() {
        int count = 0;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof Employee) {
                count++;
            }
        }
        return count;
    }

    public int countCostumers() {
        int count = 0;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i) instanceof Costumer) {
                count++;
            }
        }
        return count;
    }

    public int getNumberOrders() {
        int count = 0;
        for (int i = 0; i < orders.size(); i++) {
            count++;
        }
        return count;
    }

    public int getNumberIngredients() {
        int count = 0;
        for (int i = 0; i < ingredients.size(); i++) {
            count++;
        }
        return count;
    }

    public int getNumberProduct() {
        int count = 0;
        for (int i = 0; i < products.size(); i++) {
            count++;
        }
        return count;
    }

    public int getNumberProductType() {
        int count = 0;
        for (int i = 0; i < productType.size(); i++) {
            count++;
        }
        return count;
    }

    public int getNumberProductSize() {
        int count = 0;
        for (int i = 0; i < productSize.size(); i++) {
            count++;
        }
        return count;
    }

    public int getNumberBaseProduct() {
        int count = 0;
        for (int i = 0; i < baseProducts.size(); i++) {
            count++;
        }
        return count;
    }

    // -----------------------------------------------SEARCHING-ALGORITHMS--------------------------------------------
    public boolean searchUserByName(Employee e) {
        boolean render = false;
        for (int i = 0; i < orders.size() && !render; i++) {
            Order o = orders.get(i);
            for (int j = 0; j < getEmployees(people).size(); j++) {
                if (o.getEmployee().getId() == e.getId()) {
                    if (!o.getState().equals(State.DELIVERED) || !o.getState().equals(State.CANCELED))
                        render = true;
                }
            }
        }
        return render;
    }

    public boolean searchCostumerInOrders(Costumer c) {
        boolean render = false;
        for (int i = 0; i < orders.size() && !render; i++) {
            Order o = orders.get(i);
            for (int j = 0; j < getCostumers(people).size() && !render; j++) {
                if (o.getCostumer().getId() == c.getId()) {
                    if (o.getState().equals(State.IN_PROCCESS) || o.getState().equals(State.REQUESTED)
                            || o.getState().equals(State.SENT)) {
                        render = true;
                    }
                }
            }
        }
        return render;
    }

    public Costumer searchCostumerByObj(Costumer c) {
        Costumer cos = null;
        for (Costumer cost : getCostumers(people)) {
            if (cost.getId() == c.getId()) {
                cos = c;
            }
        }
        return cos;
    }

    public Costumer searchCostumerByIndex(int index) {
        if (index >= 0) {
            return getCostumers(people).get(index);
        } else {
            return null;
        }
    }

    public Costumer searchBinary(int id, List<Costumer> cost) {
        boolean found = false;
        Costumer cos = null;
        int i = 0;
        int j = cost.size() - 1;
        while (i <= j && !found) {
            int m = (i + j) / 2;
            if (cost.get(m).getId() == id) {
                found = true;
                Costumer c = cost.get(m);
                cos = searchCostumerByObj(c);
            } else if (cost.get(m).getId() > id) {
                j = m - 1;
            } else {
                i = m + 1;
            }
        }
        return cos;
    }

    public boolean searchIngredient(String name) {
        boolean render = false;
        for (int i = 0; i < products.size() && !render; i++) {
            Product p = products.get(i);
            for (int j = 0; j < p.getBaseProduct().getIngredients().size(); j++) {
                if (p.getBaseProduct().getIngredients().get(j).getName().equalsIgnoreCase(name)) {
                    render = true;
                }
            }
        }
        return render;
    }

    public Ingredients searchIngredientByIndex(int i) {
        if (i >= 0) {
            return ingredients.get(i);
        } else {
            return null;
        }
    }

    public boolean searchProduct(String name) {
        boolean render = false;
        for (int i = 0; i < orders.size() && !render; i++) {
            Order o = orders.get(i);
            for (int j = 0; j < o.getProducts().size(); j++) {
                if (o.getNameProducts().equalsIgnoreCase(name)) {
                    render = true;
                }
            }
        }
        return render;
    }

    public Product searchProductByIndex(int i) {
        if (i >= 0) {
            return products.get(i);
        } else {
            return null;
        }
    }

    public boolean searchProductType(String name) {
        boolean render = false;
        for (int i = 0; i < products.size() && !render; i++) {
            Product p = products.get(i);
            if (p.getBaseProduct().getProductType().getName().equalsIgnoreCase(name)) {
                render = true;
            }
        }
        return render;
    }

    public ProductType getTypeImporting(String name) {
        boolean render = false;
        ProductType pt = null;
        for (int i = 0; i < productType.size() && !render; i++) {
            if (productType.get(i).getName().equalsIgnoreCase(name)) {
                pt = productType.get(i);
                render = true;
            }
        }
        return pt;
    }

    public Ingredients getIngredientImporting(String name) {
        boolean render = false;
        Ingredients ing = null;
        for (int i = 0; i < ingredients.size() && !render; i++) {
            if (ingredients.get(i).getName().equalsIgnoreCase(name)) {
                ing = ingredients.get(i);
                render = true;
            }
        }
        return ing;
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

    public Ingredients searchIndex(Ingredients ing) {
        int ingredientIndex = -1;
        boolean found = false;
        if (!ingredients.isEmpty() && ing != null) {
            for (int i = 0; i < ingredients.size() && !found; i++) {
                if (ingredients.get(i).getName().equals(ing.getName())) {
                    ingredientIndex = i;
                    found = true;
                }
            }
        }
        return searchIngredientByIndex(ingredientIndex);
    }

    public Product searchIndex(Product pro) {
        int productIndex = -1;
        boolean found = false;
        if (!products.isEmpty() && pro != null) {
            for (int i = 0; i < products.size() && !found; i++) {
                if (products.get(i).getBaseProduct().getName().equals(pro.getBaseProduct().getName())
                        && products.get(i).getProductSize().equals(pro.getProductSize())) {
                    productIndex = i;
                    found = true;
                }
            }
        }
        return searchProductByIndex(productIndex);
    }

    public boolean searchBaseProduct(String name) {
        boolean render = false;
        for (int i = 0; i < products.size() && !render; i++) {
            Product p = products.get(i);
            if (p.getBaseProduct().getName().equalsIgnoreCase(name)) {
                render = true;
            }
        }
        return render;
    }

    // --------------------------------------------SORT-ALGORITHMS--------------------------------------
    public List<Order> bubble(List<Order> ord) {
        List<Order> orderSorted = ord;
        int i, j;
        Order o;
        for (i = 0; i < ord.size() - 1; i++) {
            for (j = 0; j < ord.size() - i - 1; j++) {
                LocalDate localDate1 = LocalDate.parse(ord.get(j).getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate localDate2 = LocalDate.parse(ord.get(j + 1).getDate(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalTime localTime1 = LocalTime.parse(ord.get(j).getHour());
                LocalTime localTime2 = LocalTime.parse(ord.get(j + 1).getHour());
                if (localDate2.compareTo(localDate1) < 0) {
                    o = ord.get(j + 1);
                    orderSorted.set(j + 1, ord.get(j));
                    orderSorted.set(j, o);
                } else if (localDate2.compareTo(localDate1) == 0 && localTime2.compareTo(localTime1) < 0) {
                    o = ord.get(j + 1);
                    orderSorted.set(j + 1, ord.get(j));
                    orderSorted.set(j, o);
                }
            }
        }
        return orderSorted;
    }

    public List<Costumer> sortCostumerById() {
        List<Costumer> cost = new ArrayList<>();
        for (int j = 0; j < getCostumers(people).size(); j++) {
            Costumer c = getCostumers(people).get(j);
            if (cost.isEmpty()) {
                cost.add(c);
            } else {
                int i = 0;
                CostumersComparator cc = new CostumersComparator();
                while (i < cost.size() && cc.compare(c, cost.get(i)) > 0) {
                    i++;
                }
                cost.add(i, c);
            }
        }
        return cost;
    }

    public void sortIngredientByName() {
        IngredientsComparator ic = new IngredientsComparator();
        Collections.sort(ingredients, ic);
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

    // -----------------------------------------ENABLE-OBJECTS------------------------------------------------------
    public List<Costumer> getEnableCostumers() {
        List<Costumer> c = new ArrayList<>();
        for (Costumer cos : getCostumers(people)) {
            if (cos.getState() == true) {
                c.add(cos);
            }
        }
        return c;
    }

    public List<Employee> getEnableEmployees() {
        List<Employee> e = new ArrayList<>();
        for (int i = 0; i < getEmployees(people).size(); i++) {
            if (getEmployees(people).get(i).getState() == true) {
                e.add(getEmployees(people).get(i));
            }
        }
        return e;
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

    public List<Product> getEnableProducts() {
        List<Product> p = new ArrayList<>();
        for (Product pro : products) {
            if (pro.getState() == true) {
                p.add(pro);
            }
        }
        return p;
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

    public List<ProductSize> getEnableProductSizes() {
        List<ProductSize> ps = new ArrayList<>();
        for (ProductSize proS : productSize) {
            if (proS.getState() == true) {
                ps.add(proS);
            }
        }
        return ps;
    }

    public List<BaseProduct> getEnableBaseProduct() {
        List<BaseProduct> bp = new ArrayList<>();
        for (BaseProduct baseP : baseProducts) {
            if (baseP.getState() == true) {
                bp.add(baseP);
            }
        }
        return bp;
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

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    // --------------------------------------------SOME-MINIMUM-METHODS:PRODUCTS---------------------------------------------------

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // ---------------------------------------SOME-MINIMUM-METHODS:PRDODUCT_TYPE---------------------------------------------------

    public List<ProductType> getProductType() {
        return this.productType;
    }

    public void setProductType(List<ProductType> productType) {
        this.productType = productType;
    }

    // --------------------------------------------SOME-MINIMUM-METHODS:PRODUCT_SIZE----------------------------------------------

    public List<ProductSize> getProductSize() {
        return this.productSize;
    }

    public void setProductSize(List<ProductSize> productSize) {
        this.productSize = productSize;
    }
    // ---------------------------------------SOME-MINIMUM-METHODS:BASE_PRODUCTS----------------------------------------

    public List<BaseProduct> getBaseProducts() {
        return this.baseProducts;
    }

    public void setBaseProducts(List<BaseProduct> baseProducts) {
        this.baseProducts = baseProducts;
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
            oos.writeObject(orders);
            oos.writeObject(baseProducts);
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
            orders = (ArrayList<Order>) ois.readObject();
            baseProducts = (List<BaseProduct>) ois.readObject();
            ingredients = (List<Ingredients>) ois.readObject();
            productType = (List<ProductType>) ois.readObject();
            productSize = (List<ProductSize>) ois.readObject();
            ois.close();
        }
    }

    // -----------------------------------------EMPLOYEE-REPORT--------------------------------------------------
    public ArrayList<int[]> countOrdersForEmployee(List<Employee> e, List<Order> order) {
        ArrayList<int[]> list = new ArrayList<>();
        int[] array = new int[2];
        int orderForEmployee = 0;
        double totalPrice = 0;
        for (int j = 0; j < e.size(); j++) {
            for (int i = 0; i < order.size(); i++) {
                if (order.get(i).getEmployee().getId() == e.get(j).getId()) {
                    orderForEmployee++;
                    totalPrice += order.get(i).getTotalPrice();
                }
            }
            array[0] = orderForEmployee;
            array[1] = (int) (totalPrice);
            list.add(array);
            array = new int[2];
            orderForEmployee = 0;
            totalPrice = 0;
        }
        return list;
    }

    public List<Employee> getUnitEmployees(List<Order> order) {
        List<Employee> e = new ArrayList<>();
        for (int i = 0; i < order.size(); i++) {
            if (e.isEmpty()) {
                e.add(order.get(0).getEmployee());
            } else {
                for (int j = 0; j < e.size(); j++) {
                    if (e.get(j).getId() != order.get(i).getEmployee().getId()
                            && !searchEmployeeInList(e, order.get(i).getEmployee().getId())) {
                        e.add(order.get(i).getEmployee());
                    }
                }
            }
        }
        return e;
    }

    public boolean searchEmployeeInList(List<Employee> e, int id) {
        boolean render = false;
        for (int i = 0; i < e.size(); i++) {
            if (e.get(i).getId() == id) {
                render = true;
            }
        }
        return render;
    }

    public void exportEmployeeReport(String fileName, String separator, List<Order> o) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        List<Employee> em = getUnitEmployees(o);
        pw.println("Name" + separator + "Last name" + separator + "Id" + separator + "# Orders" + separator + "Total");
        ArrayList<int[]> a = countOrdersForEmployee(em, o);
        int grandTotal = 0;
        for (int i = 0; i < em.size(); i++) {
            Employee e = em.get(i);
            pw.println(e.getName() + separator + e.getLastName() + separator + e.getId() + separator + a.get(i)[0]
                    + separator + a.get(i)[1]);
            grandTotal += a.get(i)[1];
        }
        pw.println(separator + separator + separator + "Grand Total" + separator + grandTotal);
        pw.close();
    }

    // --------------------------------------------------ORDERS-REPORT-----------------------------------------------------
    public void exportOrdersReport(String fileName, String separator, List<Order> ord) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        int granTotal = 0;
        List<Order> order = bubble(ord);
        pw.println("CASA DORADA ORDER REPORT");
        pw.println("Code" + separator + "Hour" + separator + "Date" + separator + "State" + separator + "Costumer"
                + separator + "Employee" + separator + "Suggestion" + separator + "Total price");
        for (int i = 0; i < order.size(); i++) {
            Order o = order.get(i);
            String orderInfo = (o.getCode() + separator + o.getHour() + separator + o.getDate() + separator
                    + o.getState() + separator + o.getCostumer() + separator + o.getEmployee() + separator
                    + o.getSuggestion() + separator + o.getTotalPrice());
            String orderProducts = "";
            String space = "";
            for (int j = 0; j < o.getProducts().size(); j++) {
                orderProducts += (space + o.getProducts().get(j).getBaseProduct().getName() + "-"
                        + o.getProducts().get(j).getProductSize() + separator + o.getAmount().get(j) + separator
                        + o.getProducts().get(j).getPrice());
                space = separator;
            }
            pw.println(orderInfo + separator + orderProducts);
            granTotal += o.getTotalPrice();
        }
        pw.println(separator + separator + separator + separator + separator + separator + "Grand Total" + separator
                + String.valueOf(granTotal));
        pw.close();
    }

    // ---------------------------------------------PRODUCTS-REPORT--------------------------------------------------------
    public List<Product> getUnitProducts(List<Order> order) {
        List<Product> p = new ArrayList<>();
        for (int i = 0; i < order.size(); i++) {
            for (int k = 0; k < order.get(i).getProducts().size(); k++) {
                if (p.isEmpty()) {
                    p.add(order.get(0).getProducts().get(0));
                } else {
                    for (int j = 0; j < p.size(); j++) {
                        if (!p.get(j).getBaseProduct().getName()
                                .equals(order.get(i).getProducts().get(k).getBaseProduct().getName())
                                && !searchPro(p, order.get(i).getProducts().get(k).getBaseProduct().getName(),
                                        order.get(i).getProducts().get(k).getProductSize().getName())) {
                            p.add(order.get(i).getProducts().get(k));
                        } else if (!p.get(j).getProductSize().equals(order.get(i).getProducts().get(k).getProductSize())
                                && !searchPro(p, order.get(i).getProducts().get(k).getBaseProduct().getName(),
                                        order.get(i).getProducts().get(k).getProductSize().getName())) {
                            p.add(order.get(i).getProducts().get(k));
                        }
                    }
                }
            }
        }
        return p;
    }

    public ArrayList<int[]> countOrdersForProduct(List<Product> p, List<Order> order) {
        ArrayList<int[]> list = new ArrayList<>();
        int[] array = new int[2];
        int orderForProduct = 0;
        double totalPrice = 0;
        for (int j = 0; j < p.size(); j++) {
            for (int i = 0; i < order.size(); i++) {
                for (int k = 0; k < order.get(i).getProducts().size(); k++) {
                    if (order.get(i).getProducts().get(k).getBaseProduct().getName()
                            .equals(p.get(j).getBaseProduct().getName())
                            && order.get(i).getProducts().get(k).getProductSize().equals(p.get(j).getProductSize())) {
                        orderForProduct += order.get(i).getAmount().get(k);
                        totalPrice += p.get(j).getPrice() * orderForProduct;
                    }
                }
            }
            array[0] = orderForProduct;
            array[1] = (int) (totalPrice);
            list.add(array);
            array = new int[2];
            orderForProduct = 0;
            totalPrice = 0;
        }
        return list;
    }

    public boolean searchPro(List<Product> p, String name, String size) {
        boolean render = false;
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i).getBaseProduct().getName().equals(name) && p.get(i).getProductSize().getName().equals(size)) {
                render = true;
            }
        }
        return render;
    }

    public void exportProductReport(String fileName, String separator, List<Order> o) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        List<Product> p = getUnitProducts(o);
        int grandTotal = 0;
        pw.println("Product" + separator + "Size" + separator + "Requested Amount" + separator + "Unit Price"
                + separator + "Total");
        ArrayList<int[]> a = countOrdersForProduct(p, o);
        for (int i = 0; i < p.size(); i++) {
            Product pro = p.get(i);
            pw.println(pro.getBaseProduct() + separator + pro.getProductSize() + separator + a.get(i)[0] + separator
                    + pro.getPrice() + separator + (a.get(i)[0] * pro.getPrice()));
            grandTotal += (a.get(i)[0] * pro.getPrice());
        }
        pw.println(separator + separator + separator + "Grand Total" + separator + grandTotal);
        pw.close();
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
        boolean redux = false;
        String msg = "You can not delete you";
        boolean render = searchUserByName(useroToDelete);
        for (int i = 0; i < people.size() && !redux; i++) {
            if (people.get(i) instanceof User) {
                User user = (User) (people.get(i));
                if (user == useroToDelete && getLoggedUser(userIndex) != user) {
                    if (render == false) {
                        User u = getLoggedUser(userIndex);
                        people.remove(useroToDelete);
                        userVerification(u.getUserName(), u.getPassword());
                        msg = "The user have been deleted succesfully";
                        redux = true;
                    } else {
                        msg = "The User have a reference by a Orders";
                        redux = true;
                    }
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
            line = br.readLine();
            addPerson(name, lastName, id, userName, password, "Imported", getLoggedUser(userIndex));
        }
        br.close();
    }

    public void exportDataUsers(String fileName, String separator) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("CASA DORADA USERS REPORT");
        pw.println("Name" + separator + "Last name" + separator + "Id" + separator + "User name" + separator
                + "Password" + separator + "Image" + separator + "State");
        for (int i = 0; i < getUsers(people).size(); i++) {
            User u = getUsers(people).get(i);
            String statusU = (u.getState() == true) ? "ACTIVE" : "INACTIVE";
            if (u.getImage() != null) {
                pw.println(
                        u.getName() + separator + u.getLastName() + separator + u.getId() + separator + u.getUserName()
                                + separator + u.getPassword() + separator + u.getImage() + separator + statusU);
            } else {
                pw.println(u.getName() + separator + u.getLastName() + separator + u.getId() + separator
                        + u.getUserName() + separator + u.getPassword() + separator + separator + statusU);
            }
        }
        pw.close();
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

    // -------------------------------------------------COSTUMERS-CRUD---------------------------------------

    public String addPerson(String name, String lastName, int id, String address, int telephone, String suggestions,
            User creator) {
        String msg = "We could not add the costumer";
        Costumer newCostumer = null;
        if (countCostumers() == 0) {
            newCostumer = new Costumer(name, lastName, id, address, telephone, suggestions, creator);
            people.add(newCostumer);
            msg = "The Costumer " + newCostumer.getName() + " have been added succesfully";
        } else {
            int i = 0;
            newCostumer = new Costumer(name, lastName, id, address, telephone, suggestions, creator);
            while (i < countCostumers() && newCostumer.compareTo(getCostumers(getPeople()).get(i)) < 0) {
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
        while (i < countCostumers() && c.compareTo(getCostumers(getPeople()).get(i)) < 0) {
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
        boolean render = false;
        String msg = "";
        boolean redux = searchCostumerInOrders(costumerToDelete);
        for (int i = 0; i < people.size() && !render; i++) {
            if (people.get(i) instanceof Costumer) {
                Costumer costumer = (Costumer) (people.get(i));
                if (costumer == costumerToDelete) {
                    if (redux == false) {
                        User u = getLoggedUser(userIndex);
                        people.remove(costumerToDelete);
                        userVerification(u.getUserName(), u.getPassword());
                        render = true;
                        msg = "The costumer have been deleted succesfully";
                    } else {
                        msg = "The costumer have a reference by a order";
                        render = true;
                    }
                }
            }
        }
        return msg;
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

    public void exportDataCostumers(String fileName, String separator) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("CASA DORADA COSTUMER REPORT");
        pw.println("Name" + separator + "Last name" + separator + "Id" + separator + "Address" + separator + "Telephone"
                + separator + "Suggestions" + separator + "Status");
        for (int i = 0; i < getCostumers(people).size(); i++) {
            Costumer c = getCostumers(people).get(i);
            String statusC = (c.getState() == true) ? "ACTIVE" : "INACTIVE";
            pw.println(c.getName() + separator + c.getLastName() + separator + c.getId() + separator + c.getAddress()
                    + separator + c.getTelephone() + separator + c.getSuggestions() + separator + statusC);
        }
        pw.close();
    }

    // -------------------------------------------EMPLOYEEES-CRUD-----------------------------------------------------
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
        boolean found = false;
        String msg = "you can not delete you";
        boolean redux = searchUserByName(employeeToDelete);
        for (int i = 0; i < people.size() && !found; i++) {
            if (people.get(i) instanceof Employee) {
                Employee employee = (Employee) (people.get(i));
                if (employee == employeeToDelete && getLoggedUser(userIndex) != employee) {
                    if (redux == false) {
                        User u = getLoggedUser(userIndex);
                        people.remove(employeeToDelete);
                        userVerification(u.getUserName(), u.getPassword());
                        found = true;
                        msg = "The employee have been deleted succesfully";
                    } else {
                        msg = "The employee have a reference by order ";
                        found = true;
                    }
                }
            }
        }
        return msg;
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

    public void exportDataEmployees(String fileName, String separator) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("CASA DORADA EMPLOYEES REPORT");
        pw.println("Name" + separator + "Last name" + separator + "Id" + separator + "Status");
        for (int i = 0; i < getEmployees(people).size(); i++) {
            Employee e = getEmployees(people).get(i);
            String statusE = (e.getState() == true) ? "ACTIVE" : "INACTIVE";
            pw.println(e.getName() + separator + e.getLastName() + separator + e.getId() + separator + statusE);
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
                }
            }
            if (!added) {
                ingredients.add(newIngredient);
                msg = "The ingredient " + newIngredient.getName() + " have been added succesfully";
            }
        }
        return msg;
    }

    public boolean validateIngredients(String newName) {
        boolean added = false;
        for (int j = 0; j < ingredients.size() && !added; j++) {
            if (newName.equalsIgnoreCase(ingredients.get(j).getName())) {
                added = true;
            }
        }
        return added;
    }

    public String setInfoIngredient(Ingredients ingredient, String name, User lastEditor) {
        boolean render = validateIngredients(name);
        if (!render) {
            ingredient.setName(name);
            ingredient.setLastEditor(lastEditor);
            return "The Ingredient have been edited succesfully";
        } else {
            return "You can not update a Ingredient with same name";
        }

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
            String name = parts[0];
            line = br.readLine();
            addIngredient(getIngredientsCode(), name, getLoggedUser(userIndex));
        }
        br.close();
    }

    public void exportDataIngredients(String fileName, String separator) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("CASA DORADA INGREDIENTS REPORT");
        pw.println("Code" + separator + "Name" + separator + "State");
        for (int i = 0; i < getIngredients().size(); i++) {
            Ingredients in = getIngredients().get(i);
            String statusI = (in.getState() == true) ? "ACTIVE" : "INACTIVE";
            pw.println(in.getCode() + separator + in.getName() + separator + statusI);
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

    public int getIngredientsCode() {
        int count = 0;
        for (int i = 0; i < ingredients.size(); i++) {
            count++;
        }
        return count + 1;
    }

    // ---------------------------------------------------------PRODUCTS-----------------------------------------------

    public String addProduct(BaseProduct baseproduct, int code, ProductSize productSize, double price, User creator) {
        String msg = "";
        Product newProduct = new Product(baseproduct, code, productSize, price, creator);
        if (products.isEmpty()) {
            products.add(newProduct);
            msg = "The product " + newProduct.getBaseProduct().getName() + " have been added succesfully";
        } else {
            boolean added = false;
            for (int j = 0; j < products.size() && !added; j++) {
                if (newProduct.getBaseProduct().getName().equalsIgnoreCase(products.get(j).getBaseProduct().getName())
                        && newProduct.getProductSize().getName().equals(products.get(j).getProductSize().getName())) {
                    msg = "You can not added an product, because alredy exists";
                    added = true;
                }
            }
            if (!added) {
                products.add(newProduct);
                msg = "The product " + newProduct.getBaseProduct().getName() + " have been added succesfully";
            }
        }
        return msg;
    }

    public boolean validateProduct(String newName, String newSize) {
        boolean added = false;
        for (int j = 0; j < products.size() && !added; j++) {
            if (newName.equalsIgnoreCase(products.get(j).getBaseProduct().getName())
                    && newSize.equals(products.get(j).getProductSize().getName())) {
                added = true;
            }
        }
        return added;
    }

    public String setProductInfo(Product product, BaseProduct newBaseproduct, ProductSize newProductSize,
            double newPrice, User lastEditor) {
        boolean render = validateProduct(newBaseproduct.getName(), newProductSize.getName());
        if (!render) {
            product.setBaseProduct(newBaseproduct);
            product.setProductSize(newProductSize);
            product.setPrice(newPrice);
            product.setLastEditor(lastEditor);
            return "The Product have been edited succesfully";
        } else {
            return "You can not update a Product with same name";
        }
    }

    public String deleteProduct(int positionProduct, String name, String nameSize) {
        boolean redux = searchProduct(name + "-" + nameSize);
        if (redux == false) {
            products.remove(positionProduct);
            setCodeProductPosition();
            return "The product have been deleted succesfully";
        } else {
            return "The product have a reference by a order";
        }

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

    public void exportDataProduct(String fileName, String separator) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("CASA DORADA PRODUCTS REPORT");
        pw.println("Code" + separator + " Name" + separator + "Product type" + separator + "Product size" + separator
                + "Ingredients" + separator + "State" + separator + "Price");
        for (int i = 0; i < getProducts().size(); i++) {
            Product p = getProducts().get(i);
            String statusP = (p.getState() == true) ? "ACTIVE" : "INACTIVE";
            pw.println(p.getCode() + separator + p.getBaseProduct().getName() + separator
                    + p.getBaseProduct().getProductType() + separator + p.getProductSize() + separator
                    + p.getBaseProduct().getNameIngredients() + separator + statusP + separator + p.getPrice());
        }
        pw.close();
    }

    public int getCodeProduct() {
        int count = 0;
        for (int i = 0; i < products.size(); i++) {
            count++;
        }
        return count + 1;
    }

    public void setCodeProductPosition() {
        int code = 1;
        for (Product p : products) {
            p.setCode(code);
            code++;
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

    public boolean validateProductType(String newName) {
        boolean added = false;
        for (int j = 0; j < productType.size() && !added; j++) {
            if (newName.equalsIgnoreCase(productType.get(j).getName())) {
                added = true;
            }
        }
        return added;
    }

    public String setProductType(ProductType productType, String name, User lastEditor) {
        boolean render = validateProductType(name);
        if (!render) {
            productType.setName(name);
            productType.setLastEditor(lastEditor);
            return "The product type have been edited succesfully";
        } else {
            return "You can not update a Product Type with same name";
        }

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
            line = br.readLine();
            addProductType(name, getLoggedUser(userIndex), getCodeProductType());
        }
        br.close();
    }

    public void exportDataProductType(String fileName, String separator) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("CASA DORADA PRODUCT TYPE REPORT");
        pw.println("Code" + separator + "Name" + separator + "Status");
        for (int i = 0; i < getProductType().size(); i++) {
            ProductType pt = getProductType().get(i);
            String statusPt = (pt.getState() == true) ? "ACTIVE" : "INACTIVE";
            pw.println(pt.getCode() + separator + pt.getName() + separator + statusPt);
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

    // ---------------------------------------BASE-PRODUCT----------------------------------------------------
    public String addBaseProduct(String name, ProductType productType, List<Ingredients> ingredients, int code,
            User creator) {
        String msg = "";
        BaseProduct newBaseProduct = new BaseProduct(name, productType, ingredients, code, creator);
        if (baseProducts.isEmpty()) {
            baseProducts.add(newBaseProduct);
            msg = "The base product " + newBaseProduct.getName() + " have been added succesfully";
        } else {
            boolean added = false;
            for (int j = 0; j < baseProducts.size() && !added; j++) {
                if (newBaseProduct.getName().equalsIgnoreCase(baseProducts.get(j).getName())) {
                    msg = "You can not added an base product, because alredy exists";
                    added = true;
                }
            }
            if (!added) {
                baseProducts.add(newBaseProduct);
                msg = "The base product " + newBaseProduct.getName() + " have been added succesfully";
            }
        }
        return msg;
    }

    public boolean validateBaseProduct(String newName) {
        boolean added = false;
        for (int j = 0; j < baseProducts.size() && !added; j++) {
            if (newName.equalsIgnoreCase(baseProducts.get(j).getName())) {
                added = true;
            }
        }
        return added;
    }

    public String setBaseProductInfo(BaseProduct baseProduct, String newName, ProductType newProductType,
            List<Ingredients> newIngredients, int newCode, User lastEditor) {
        boolean render = validateBaseProduct(newName);
        if (!render) {
            baseProduct.setName(newName);
            baseProduct.setProductType(newProductType);
            baseProduct.setIngredients(newIngredients);
            baseProduct.setLastEditor(lastEditor);
            return "The base product have been edited succesfully";
        } else {
            return "You can not update a base product with same name";
        }
    }

    public String deleteBaseProduct(int positionBaseProduct, String name) {
        boolean redux = searchBaseProduct(name);
        if (redux == false) {
            baseProducts.remove(positionBaseProduct);
            setCodeBaseProductPosition();
            return "The base product have been deleted succesfully";
        } else {
            return "The base base product have a reference by a product";
        }
    }

    public String disableBaseProduct(BaseProduct bp) {
        bp.setState(false);
        return "The base product have been disabled succesfully";
    }

    public String enableBaseProduct(BaseProduct bp) {
        bp.setState(true);
        return "The base product have been enabled succesfully";
    }

    public ArrayList<Ingredients> getingImporting(String[] ing) {
        ArrayList<Ingredients> in = new ArrayList<>();
        for (int i = 1; i < ing.length; i++) {
            if (searchIngredient(ing[i])) {
                in.add(getIngredientImporting(ing[i]));
            }
        }
        return in;
    }

    public void importDataBaseProduct(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            String[] parts2 = line.split("|");
            boolean render = searchProductType(parts[1]);
            ArrayList<Ingredients> ing = getingImporting(parts2);
            if (render && ing != null) {
                String name = parts[0];
                ProductType pt = getTypeImporting(parts[1]);
                addBaseProduct(name, pt, ing, getCodeBaseProduct(), getLoggedUser(userIndex));
            }
            line = br.readLine();
        }
        br.close();
    }

    public void exportDataBaseProduct(String fileName, String separator) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("CASA DORADA BASE PRODUCTS REPORT");
        pw.println("Code" + separator + " Name" + separator + "Product type" + separator + "Ingredients" + separator
                + "State");
        for (int i = 0; i < getBaseProducts().size(); i++) {
            BaseProduct bp = getBaseProducts().get(i);
            String statusBp = (bp.getState() == true) ? "ACTIVE" : "INACTIVE";
            pw.println(bp.getCode() + separator + bp.getName() + separator + bp.getProductType() + separator + separator
                    + bp.getNameIngredients() + separator + statusBp);
        }
        pw.close();
    }

    public int getCodeBaseProduct() {
        int count = 0;
        for (int i = 0; i < baseProducts.size(); i++) {
            count++;
        }
        return count + 1;
    }

    public void setCodeBaseProductPosition() {
        int code = 1;
        for (BaseProduct bp : baseProducts) {
            bp.setCode(code);
            code++;
        }
    }
    // -----------------------------------------PRODUCT_SIZE----------------------------------------------

    public String addProductSize(String name, int code, User creator) {
        String msg = "";
        ProductSize newProductSize = new ProductSize(name, code, creator);
        if (productSize.isEmpty()) {
            productSize.add(newProductSize);
            msg = "The product size " + newProductSize.getName() + " have been added succesfully";
        } else {
            boolean added = validateProductSize(name);
            if (added) {
                msg = "You can not added an product size with the same name";
            }
            if (!added) {
                productSize.add(newProductSize);
                msg = "The product size " + newProductSize.getName() + " have been added succesfully";
            }
        }
        return msg;
    }

    public boolean validateProductSize(String newName) {
        boolean added = false;
        for (int j = 0; j < productSize.size() && !added; j++) {
            if (newName.equalsIgnoreCase(productSize.get(j).getName())) {
                added = true;
            }
        }
        return added;
    }

    public String setProductSize(ProductSize productSize, String name, User lastEditor) {
        boolean render = validateProductSize(name);
        if (!render) {
            productSize.setName(name);
            productSize.setLastEditor(lastEditor);
            return "The product size have been edited succesfully";
        } else {
            return "You can not update a Product Size with same name";
        }
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

    public void importDataProductSize(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            String name = parts[0];
            line = br.readLine();
            addProductSize(name, getCodeProductSize(), getLoggedUser(userIndex));
        }
        br.close();
    }

    public void exportDataProductSize(String fileName, String separator) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("CASA DORADA PRODUCT SIZE REPORT");
        pw.println("Code" + separator + "Name" + separator + "Status");
        for (int i = 0; i < getProductSize().size(); i++) {
            ProductSize ps = getProductSize().get(i);
            String statusPs = (ps.getState() == true) ? "ACTIVE" : "INACTIVE";
            pw.println(ps.getCode() + separator + ps.getName() + separator + statusPs);
        }
        pw.close();
    }

    // ----------------------------------------------ORDERS-CRUD----------------------------------------------------

    public String addOrders(int code, State state, List<Product> products, List<Integer> amount, Costumer costumer,
            Employee employee, String date, String suggestion, User creator, double totalPrice, String hour) {
        String msg = "";
        Order newOrder = new Order(code, state, products, amount, costumer, employee, date, suggestion, creator,
                totalPrice, hour);
        orders.add(newOrder);
        msg = "The order " + newOrder.getCode() + " have been added succesfully";
        return msg;
    }

    public String setOrderInfo(Order order, String date, String hour, State newState, List<Product> newProducts,
            List<Integer> newAmount, Costumer newCostumer, Employee newEmployee, String newSuggestion,
            User newLastEditor, double newTotalPrice) {
        String msg = "";
        if (order.getState().equals(State.CANCELED) || newState.equals(State.CANCELED)) {
            order.setState(newState);
            setOrdersField(order, date, hour, newProducts, newAmount, newCostumer, newEmployee, newSuggestion,
                    newLastEditor, newTotalPrice);
            msg = "The Orders have been edited succesfully";
        }
        if (order.getState().equals(State.REQUESTED)) {
            order.setState(newState);
            setOrdersField(order, date, hour, newProducts, newAmount, newCostumer, newEmployee, newSuggestion,
                    newLastEditor, newTotalPrice);
            msg = "The Orders have been edited succesfully";
        } else if (order.getState().equals(State.IN_PROCCESS) && !newState.equals(State.REQUESTED)) {
            order.setState(newState);
            setOrdersField(order, date, hour, newProducts, newAmount, newCostumer, newEmployee, newSuggestion,
                    newLastEditor, newTotalPrice);
            msg = "The Orders have been edited succesfully";
        } else if (order.getState().equals(State.SENT) && !newState.equals(State.IN_PROCCESS)
                && !newState.equals(State.REQUESTED)) {
            order.setState(newState);
            setOrdersField(order, date, hour, newProducts, newAmount, newCostumer, newEmployee, newSuggestion,
                    newLastEditor, newTotalPrice);
            msg = "The Orders have been edited succesfully";
        } else if (order.getState().equals(State.DELIVERED) && !newState.equals(State.IN_PROCCESS)
                && !newState.equals(State.REQUESTED) && !newState.equals(State.SENT)) {
            order.setState(newState);
            setOrdersField(order, date, hour, newProducts, newAmount, newCostumer, newEmployee, newSuggestion,
                    newLastEditor, newTotalPrice);
            msg = "The Orders have been edited succesfully";
        } else {
            msg = "You can not came back order status";
        }
        return msg;
    }

    public void setOrdersField(Order order, String date, String hour, List<Product> newProducts,
            List<Integer> newAmount, Costumer newCostumer, Employee newEmployee, String newSuggestion,
            User newLastEditor, double newTotalPrice) {
        order.setProducts(newProducts);
        order.setAmount(newAmount);
        order.setCostumer(newCostumer);
        order.setEmployee(newEmployee);
        order.setSuggestion(newSuggestion);
        order.setTotalPrice(newTotalPrice);
        order.setLastEditor(newLastEditor);
        order.setDate(date);
        order.setHour(hour);
    }

    public String deleteOrder(int positionOrder, State state) {
        if (state.equals(State.CANCELED) || state.equals(State.DELIVERED)) {
            orders.remove(positionOrder);
            setCodeOrderPosition();
            return "The Order have been deleted succesfully";
        } else {
            return "You can not delete an order with the following states: Requeted, In procces, Sent";
        }
    }

    public int getCodeOrder() {
        int count = 0;
        for (int i = 0; i < orders.size(); i++) {
            count++;
        }
        return count + 1;
    }

    public void setCodeOrderPosition() {
        int code = 1;
        for (Order o : orders) {
            o.setCode(code);
            code++;
        }
    }

    public void importDataOrder(String fileName) throws IOException {
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

    public void exportDataOrder(String fileName, String separator) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        int granTotal = 0;
        pw.println("CASA DORADA ORDER REPORT");
        pw.println("Code" + separator + "Hour" + separator + "Date" + separator + "State" + separator + "Product"
                + separator + "Amount " + separator + "Costumer" + separator + "Employee" + separator + "Suggestion"
                + separator + "Total price");
        for (int i = 0; i < getOrders().size(); i++) {
            Order o = getOrders().get(i);
            String orderInfo = (o.getCode() + separator + o.getHour() + separator + o.getDate() + separator
                    + o.getState() + separator + o.getNameProducts() + separator + o.getNameAmount() + separator
                    + o.getCostumer() + separator + o.getEmployee() + separator + o.getSuggestion() + separator
                    + o.getTotalPrice());
            String orderProducts = "";
            String space = "";
            for (int j = 0; j < o.getProducts().size(); j++) {
                orderProducts += (space + o.getProducts().get(j).getBaseProduct().getName() + separator
                        + o.getAmount().get(j) + separator + o.getProducts().get(j).getPrice());
                space = separator;
            }
            pw.println(orderInfo + separator + orderProducts);
            granTotal += o.getTotalPrice();
        }
        pw.println(separator + separator + separator + separator + separator + separator + separator + separator
                + "Grand Total" + separator + String.valueOf(granTotal));
        pw.close();
    }

}
