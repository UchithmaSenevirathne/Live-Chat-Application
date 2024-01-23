package lk.ijse.chatApp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.Socket;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientFormController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    public Circle circle;

    @FXML
    private ImageView emojiBtn;

    @FXML
    private ImageView imgBtn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public Label lblUserName;

    @FXML
    private Pane imagePane;

    @FXML
    private ImageView clickedImage;
    
    private static String userName;

    @FXML
    private TextField txtMessage;

    @FXML
    private VBox vBox;

    @FXML
    private Pane emojiPane;

    @FXML
    private ImageView img1,img2,img3,img4,img5,img6,img7,img8;

    private File file;
    private BufferedReader bufferedReader;
    private static PrintWriter writer;
    private String finalName;

    Stage stage;


    @FXML
    void btnExitOnAction(ActionEvent event) {
        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        if (!txtMessage.getText().isEmpty()){
            if (file != null){
                writer.println("img"+lblUserName.getText()+"~"+file.getPath());
            }else {
                writer.println(lblUserName.getText() + "~" + txtMessage.getText());
            }
            txtMessage.setEditable(true);
            txtMessage.clear();
        }
    }

    @FXML
    void emojiBtnOnAction(MouseEvent event) {
        if (emojiPane.isVisible()){
            emojiPane.setVisible(false);
            img1.setVisible(false);
            img2.setVisible(false);
            img3.setVisible(false);
            img4.setVisible(false);
            img5.setVisible(false);
            img6.setVisible(false);
            img7.setVisible(false);
            img8.setVisible(false);
        }else {
            emojiPane.setVisible(true);
            img1.setVisible(true);
            img2.setVisible(true);
            img3.setVisible(true);
            img4.setVisible(true);
            img5.setVisible(true);
            img6.setVisible(true);
            img7.setVisible(true);
            img8.setVisible(true);
        }
    }

    @FXML
    void imgBtnOnAction(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the image");
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        file = fileChooser.showOpenDialog(txtMessage.getScene().getWindow());
        if (file != null){
            txtMessage.setText("1 image selected");
            txtMessage.setEditable(false);
        }
    }

    @FXML
    void modeOnAction(MouseEvent event) {

    }

    @FXML
    void txtMessageOnAction(ActionEvent event) {
        btnSendOnAction(event);
    }

    @FXML
    void angryOnAction(MouseEvent event) {

    }

    @FXML
    void cryOnAction(MouseEvent event) {

    }

    @FXML
    void eyesWithHearrtOnAction(MouseEvent event) {

    }

    @FXML
    void laughOnAction(MouseEvent event) {

    }

    @FXML
    void likeOnAction(MouseEvent event) {

    }

    @FXML
    void sadOnAction(MouseEvent event) {

    }

    @FXML
    void smileOnAction(MouseEvent event) {

    }

    @FXML
    void wowOnAction(MouseEvent event) {

    }

    public void initialize() {
        userName = LoginController.userName;
        System.out.println(userName);

        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost",3002);

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(),true);

                writer.println("joi"+userName+"~joining");

                while (true){
                    //reading response
                    String receive = bufferedReader.readLine();
                    String[] split = receive.split("~");
                    String name = split[0];
                    String message = split[1];

                    //find which type of message is came
                    String firstChars = "";
                    if (name.length() > 3) {
                        firstChars = name.substring(0, 3);
                    }
                    if (firstChars.equalsIgnoreCase("img")){
                        String[] imgs = name.split("img");
                        finalName = imgs[1];
                    }else if(firstChars.equalsIgnoreCase("joi")){
                        String[] imgs = name.split("joi");
                        finalName = imgs[1];
                    }else if(firstChars.equalsIgnoreCase("lef")){
                        String[] imgs = name.split("lef");
                        finalName = imgs[1];
                    }
                    if (firstChars.equalsIgnoreCase("img")){
                        if (finalName.equalsIgnoreCase(userName)){

                            //adding image to message
                            File receiveFile = new File(message);
                            Image image = new Image(receiveFile.toURI().toString());
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(170);
                            imageView.setFitWidth(200);
                            imageView.setOnMouseClicked(mouseEvent -> {
                                clickedImage.setImage(imageView.getImage());
                                imagePane.setVisible(true);
                            });
                            //adding sender to message
                            Text text = new Text("~ Me");
                            text.getStyleClass().add("send-text");

                            //add time
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                            LocalDateTime now = LocalDateTime.now();
                            Text time = new Text(dtf.format(now));
                            time.getStyleClass().add("time-text");
                            HBox timeBox = new HBox();
                            timeBox.getChildren().add(time);
                            timeBox.setAlignment(Pos.BASELINE_RIGHT);

                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(text, imageView, timeBox);

                            HBox hBox = new HBox(10);
                            hBox.getStyleClass().add("send-box");
                            hBox.setMaxHeight(190);
                            hBox.setMaxWidth(220);
                            hBox.getChildren().add(vbox);

                            StackPane stackPane = new StackPane(hBox);
                            stackPane.setAlignment(Pos.CENTER_RIGHT);

                            //adding message to message area
                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(stackPane);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setAlignment(Pos.CENTER_LEFT);
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }else {
                            //adding image to message
                            File receives = new File(message);
                            Image image = new Image(receives.toURI().toString());
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(170);
                            imageView.setFitWidth(200);
                            imageView.setOnMouseClicked(mouseEvent -> {
                                clickedImage.setImage(imageView.getImage());
                                imagePane.setVisible(true);
                            });

                            //adding sender to message
                            Text text = new Text("~ "+finalName);
                            text.getStyleClass().add("receive-text");

                            //add time
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                            LocalDateTime now = LocalDateTime.now();
                            Text time = new Text(dtf.format(now));
                            time.getStyleClass().add("time-text");
                            HBox timeBox = new HBox();
                            timeBox.getChildren().add(time);
                            timeBox.setAlignment(Pos.BASELINE_RIGHT);

                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(text, imageView, timeBox);

                            HBox hBox = new HBox(10);
                            hBox.getStyleClass().add("receive-box");
                            hBox.setMaxHeight(190);
                            hBox.setMaxWidth(220);
                            hBox.getChildren().add(vbox);

                            //adding message to message area
                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(hBox);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }
                    }else if(firstChars.equalsIgnoreCase("joi")) {
                        if (finalName.equalsIgnoreCase(userName)){

                            //adding name of client which join the chat
                            Label text = new Label("You have join the chat");
                            text.getStyleClass().add("join-text");
                            HBox hBox = new HBox();
                            hBox.getChildren().add(text);
                            hBox.setAlignment(Pos.CENTER);

                            Platform.runLater(() -> {
                                vBox.getChildren().add(hBox);

                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }else{
                            Label text = new Label(finalName+" has join the chat");
                            text.getStyleClass().add("join-text");
                            HBox hBox = new HBox();
                            hBox.getChildren().add(text);
                            hBox.setAlignment(Pos.CENTER);

                            Platform.runLater(() -> {
                                vBox.getChildren().add(hBox);

                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }
                    }else if(firstChars.equalsIgnoreCase("lef")){
                        //adding name of client which left the chat
                        Label text = new Label(finalName+" has left the chat");
                        text.getStyleClass().add("left-text");
                        HBox hBox = new HBox();
                        hBox.getChildren().add(text);
                        hBox.setAlignment(Pos.CENTER);

                        Platform.runLater(() -> {
                            vBox.getChildren().add(hBox);

                            HBox hBox1 = new HBox();
                            hBox1.setPadding(new Insets(5, 5, 5, 10));
                            vBox.getChildren().add(hBox1);
                        });
                    } else{
                        if(name.equalsIgnoreCase(userName)){

                            //add message
                            TextFlow tempFlow = new TextFlow();
                            Text text = new Text(message);
                            text.setStyle("-fx-fill: #000000");
                            text.setWrappingWidth(200);
                            tempFlow.getChildren().add(text);
                            tempFlow.setMaxWidth(200);

                            //add sender name
                            Text nameText = new Text("~ Me");
                            nameText.getStyleClass().add("send-text");

                            //add time
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                            LocalDateTime now = LocalDateTime.now();
                            Text time = new Text(dtf.format(now));
                            time.getStyleClass().add("time-text");
                            HBox timeBox = new HBox();
                            timeBox.getChildren().add(time);
                            timeBox.setAlignment(Pos.BASELINE_RIGHT);
                            VBox vbox = new VBox(10);
                            vbox.setPrefWidth(210);
                            vbox.getChildren().addAll(nameText, tempFlow, timeBox);

                            //add all into message
                            HBox hBox = new HBox(12);
                            hBox.getStyleClass().add("send-box");
                            hBox.setMaxWidth(220);
                            hBox.setMaxHeight(50);
                            hBox.getChildren().add(vbox);
                            StackPane stackPane = new StackPane(hBox);
                            stackPane.setAlignment(Pos.CENTER_RIGHT);

                            //add message into message area
                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(stackPane);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }else {
                            //add message
                            TextFlow tempFlow = new TextFlow();
                            Text text = new Text(message);
                            text.setStyle("-fx-fill: #ffffff");
                            text.setWrappingWidth(200);
                            tempFlow.getChildren().add(text);
                            tempFlow.setMaxWidth(200);

                            //add sender name
                            Text nameText = new Text("~ "+name);
                            nameText.getStyleClass().add("receive-text");

                            //add time
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                            LocalDateTime now = LocalDateTime.now();
                            Text time = new Text(dtf.format(now));
                            time.getStyleClass().add("time-text");
                            HBox timeBox = new HBox();
                            timeBox.getChildren().add(time);
                            timeBox.setAlignment(Pos.BASELINE_RIGHT);
                            VBox vbox = new VBox(10);
                            vbox.setPrefWidth(210);
                            vbox.getChildren().addAll(nameText, tempFlow, timeBox);

                            //add all into message
                            HBox hBox = new HBox();
                            hBox.getStyleClass().add("receive-box");
                            hBox.setMaxWidth(220);
                            hBox.setMaxHeight(50);
                            hBox.getChildren().add(vbox);

                            //add message into message area
                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(hBox);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }
                    }
                    file = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
