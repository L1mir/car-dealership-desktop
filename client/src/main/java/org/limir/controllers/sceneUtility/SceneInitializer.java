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
        SceneManager.addScene("avg-company-price", "/org/limir/avg-company-price.fxml");
        SceneManager.addScene("read-companies", "/org/limir/read-companies.fxml");
    }
}
