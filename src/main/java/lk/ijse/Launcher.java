package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Login.fxml"));
        Scene mainScene = new Scene(root);
        stage.setScene(mainScene);
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.show();
    }
}
