package org.limir.controllers.sceneUtility;

public class SceneInitializer {
    public static void initializeScenes() {
        SceneManager.addScene("login", "/org/limir/login.fxml");
        SceneManager.addScene("register", "/org/limir/register.fxml");
        SceneManager.addScene("customer-menu", "/org/limir/customer-menu.fxml");
        SceneManager.addScene("admin-menu", "/org/limir/admin-menu.fxml");
        SceneManager.addScene("add-car", "/org/limir/add-car.fxml");
        SceneManager.addScene("add-company", "/org/limir/add-company.fxml");
    }
}
