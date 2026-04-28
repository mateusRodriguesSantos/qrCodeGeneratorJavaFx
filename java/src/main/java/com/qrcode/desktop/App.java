package com.qrcode.desktop;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import com.qrcode.desktop.QRCodeGen.QRCodeGen;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private Image image;
    private ImageView imageView;

    @Override
    public void start(Stage stage) throws IOException {
        Label title = new Label("QR Code Gen");

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);

        Label labelOne = new Label("Link");
        TextField textFielOne = new TextField();
        VBox vBoxOne = new VBox();
        vBoxOne.getChildren().addAll(labelOne, textFielOne);

        Label labelTwo = new Label("Base Link");
        TextField textFielTwo = new TextField();
        textFielTwo.setText("https://youtu.be/");
        VBox vBoxTwo = new VBox();
        vBoxOne.getChildren().addAll(labelTwo, textFielTwo);

        SplitPane splitPane = new SplitPane();
        splitPane.setMinSize(300, 380);

        imageView = new ImageView(this.image);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        Button button = new Button("Generate");
        button.setOnAction(e -> {
            QRCodeGen qrCodeGen = new QRCodeGen();
            String filePath = qrCodeGen.generateImage(textFielTwo.getText(), textFielOne.getText());
            image = new Image("file:"+filePath);
            imageView.setImage(image);
        });



        VBox vBoxLabels = new VBox();
        vBoxLabels.getChildren().addAll(vBoxOne, vBoxTwo);

        splitPane.getItems().addAll(vBoxLabels, imageView);
        
        root.setSpacing(20);

        root.getChildren().addAll(
            title, 
            splitPane,
            button
        );

        Scene scene = new Scene(root, 600,500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}