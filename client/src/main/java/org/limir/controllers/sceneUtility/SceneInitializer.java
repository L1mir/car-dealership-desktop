package org.limir.controllers.sceneUtility;

public class SceneInitializer {
    public static void initializeScenes() {
        SceneManager.addScene("login", "/org/limir/login.fxml");
        SceneManager.addScene("register", "/org/limir/register.fxml");
        SceneManager.addScene("customer-menu", "/org/limir/customer-menu.fxml");
        SceneManager.addScene("admin-menu", "/org/limir/admin-menu.fxml");
        SceneManager.addScene("add-car", "/org/limir/add-car.fxml");
        SceneManager.addScene("add-company", "/org/limir/add-company.fxml");
        SceneManager.addScene("delete-car", "/org/limir/delete-car.fxml");
        SceneManager.addScene("update-car", "/org/limir/update-car.fxml");
        SceneManager.addScene("order-history", "/org/limir/order-history.fxml");
        SceneManager.addScene("avg-company-price", "/org/limir/avg-company-price-diagram.fxml");
        SceneManager.addScene("read-companies", "/org/limir/read-companies.fxml");
        SceneManager.addScene("price-after-coupon", "/org/limir/price-after-coupon.fxml");
        SceneManager.addScene("user-profile", "/org/limir/user-profile.fxml");
        SceneManager.addScene("read-cars", "/org/limir/read-cars.fxml");
        SceneManager.addScene("add-employee", "/org/limir/add-employee.fxml");
        SceneManager.addScene("update-employee", "/org/limir/update-employee.fxml");
        SceneManager.addScene("delete-employee", "/org/limir/delete-employee.fxml");
        SceneManager.addScene("read-employees", "/org/limir/read-employees.fxml");
        SceneManager.addScene("delete-company", "/org/limir/delete-company.fxml");
        SceneManager.addScene("company-geographic-diagram", "/org/limir/company-geographic-diagram.fxml");
        SceneManager.addScene("car-status-diagram", "/org/limir/car-status-diagram.fxml");
        SceneManager.addScene("user-role-diagram", "/org/limir/user-role-diagram.fxml");
    }
}
