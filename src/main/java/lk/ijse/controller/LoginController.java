package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginController {
    @FXML
    public Circle circle;

    @FXML
    private Button imageButton;

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private TextField txtUserName;

    public static String userName;
    public static List<String> users = new ArrayList<>();
    public static HashMap<String, Image> userLIst = new HashMap<>();
    public static Image image;


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
    void btnLoginOnAction(ActionEvent event) {
        if(!txtUserName.getText().equals("")) {

            userName = txtUserName.getText();

            if (users.contains(txtUserName.getText())) {
                new Alert(Alert.AlertType.ERROR,"already added").show();
            } else {
                users.add(userName);
                userLIst.put(txtUserName.getText(), image);
                    /*Stage stage = new Stage();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ClientForm.fxml"))));
                    */
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Chat.fxml"));

                    Parent rootNode = null;
                    try {
                        rootNode = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    ClientFormController controller = loader.getController();

                    if (image==null){
                        image = new Image("C:\\My Projects\\Live-Chat\\chat-app\\src\\main\\resources\\icon\\user.png");
                        controller.circle.setFill(new ImagePattern(image));
                    }else {
                        controller.circle.setFill(new ImagePattern(image));
                    }
                    controller.lblUserName.setText(userName);

                    Scene scene = new Scene(rootNode);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Chat Room");
                    //stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                    txtUserName.setText("");
                    circle.setFill(null);
                    image = null;
            }
        }else
            new Alert(Alert.AlertType.ERROR, "Please enter your name!").show();
    }
}
