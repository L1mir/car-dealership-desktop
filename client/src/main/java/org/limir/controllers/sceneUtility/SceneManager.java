package org.limir.controllers.sceneUtility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static final Map<String, Scene> scenes = new HashMap<>();
    private static final Map<String, Object> controllers = new HashMap<>();
    private static Stage primaryStage;

    public static void initialize(Stage stage) {
        primaryStage = stage;
    }

    public static void addScene(String name, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            scenes.put(name, new Scene(root));
            controllers.put(name, loader.getController());  // Сохраняем контроллер
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось загрузить FXML: " + fxmlPath);
        }
    }

    public static void showScene(String name) {
        Scene scene = scenes.get(name);
        if (scene == null) {
            throw new IllegalArgumentException("Сцена " + name + " не найдена!");
        }
        primaryStage.setScene(scene);
    }

    public static Scene getScene(String name) {
        return scenes.get(name);
    }

    public static Object getController(String name) {
        return controllers.get(name);
    }
}
