package lk.ijse.chatApp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;

public class LoginController {
    @FXML
    public Circle circle;

    @FXML
    private TextField txtUserName;

    public static String userName;
    public Image image;

    @FXML
    void btnImageOnAction(ActionEvent event) {
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(window);
        event.consume();
        if (file != null) {
            try {
                InputStream in = new FileInputStream(file);
                image = new Image(in);
                circle.setFill(new ImagePattern(image));

            } catch (FileNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnLoginOnAction(ActionEvent event){
        if(!txtUserName.getText().equals("")) {

            userName = txtUserName.getText();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Chat.fxml"));

                    Parent rootNode = null;
                    try {
                        rootNode = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    ClientFormController controller = loader.getController();
                    if(image == null){
                        new Alert(Alert.AlertType.ERROR, "add profile photo").show();
                    }else {
                        controller.circle.setFill(new ImagePattern(image));
                        controller.lblUserName.setText(userName);

                        Stage stage = (Stage) txtUserName.getScene().getWindow();
                        stage.setScene(new Scene(rootNode));
                        stage.setTitle(userName + "'s chat");
                        stage.show();
                    }
        }else
            new Alert(Alert.AlertType.ERROR, "Please enter your name!").show();
    }
}
