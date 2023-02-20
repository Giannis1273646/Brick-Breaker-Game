package com.example.brick_breaker_game;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        Scene scene = new Scene(root, 700, 410);

        //Αναγνωρίζει ποιο κουμπί πατιέται κάθε φορά
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                //Ανάλογα πιο κουμπί θα πατηθεί, καλεί την αντίστοιχη μέθοδο από τον Controller
                switch (event.getCode()){
                    case LEFT:
                        controller.moveLeft();
                        break;
                    case RIGHT:
                        controller.moveRight();
                        break;
                }
            }
        });

        stage.setTitle("Brick Breaker Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}