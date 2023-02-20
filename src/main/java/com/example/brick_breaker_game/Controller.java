package com.example.brick_breaker_game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private Rectangle rod;

    @FXML
    private Sphere ball;

    @FXML
    protected int deltaX = 10, deltaY = -5, scoreCounter = 0, rodSpeed = 65, minX = 10, maxX = 12, minY = 8, maxY = 10,
            maxA = 360, minA = 180;

    @FXML
    private final ArrayList<Rectangle> bricks = new ArrayList<>();

    @FXML
    private AnchorPane scene;

    @FXML
    private Label score;

    @FXML
    Random rand = new Random();

    @FXML
    private Canvas canvas;

    @FXML
    PhongMaterial material = new PhongMaterial();


    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<>() {

        @Override
        public void handle(ActionEvent event) {

            Bounds bounds = scene.getBoundsInLocal();

            //Δίνει κίνηση στην μπάλα
            ball.setRotate(rand.nextInt(maxA + 1 - minA) + minA);
            ball.setTranslateX(ball.getTranslateX() + deltaX );
            ball.setTranslateY(ball.getTranslateY() + deltaY );

            //Όταν πάει να βγει από τα όρια της οθόνης η μπάλα αναπηδάει
            if(ball.getTranslateX() >= (bounds.getMaxX()/2) - ball.getRadius()) {

                ball.setRotate(rand.nextInt(maxA + 1 - minA) + minA);
                deltaX = -1 * (rand.nextInt(maxX + 1 - minX) + minX);
                ball.setTranslateX(ball.getTranslateX() + deltaX);

                scoreCounter += 2;
                score.setText("Score: " + scoreCounter);

            }else if( ball.getTranslateX() <= (-bounds.getMaxX()/2) + ball.getRadius()){

                ball.setRotate(rand.nextInt(maxA + 1 - minA) + minA);
                deltaX = (rand.nextInt(maxX + 1 - minX) + minX);
                ball.setTranslateX(ball.getTranslateX() + deltaX);

                scoreCounter += 2;
                score.setText("Score: " + scoreCounter);
            }

            if (ball.getTranslateY() <= (-bounds.getMaxY()/2) + ball.getRadius()){

                ball.setRotate(rand.nextInt(maxA + 1 - minA) + minA);
                deltaY = (rand.nextInt(maxY + 1 - minY) + minY);
                ball.setTranslateY(ball.getTranslateY() + deltaY);

                scoreCounter += 2;
                score.setText("Score: " + scoreCounter);
            }

            //Αναγνωρίζει αν η μπάλα ακούμπησε την μπάρα αναπηδάει
            if(ball.getBoundsInParent().intersects(rod.getBoundsInParent())){

                ball.setRotate(rand.nextInt(maxA + 1 - minA) + minA);
                deltaY = -1 * (rand.nextInt(maxY + 1 - minY) + minY);
                ball.setRotate(rand.nextInt(360));
                ball.setTranslateY(ball.getTranslateY() + deltaY);
                //Ανεβάζει το σκορ
                scoreCounter += 5;
                score.setText("Score: " + scoreCounter);
            }

            //Αν η μπάλα πάει στο κάτω μέρος της οθόνης ο παίκτης χάνει και τελειώνει το παιχνίδι
            if (ball.getTranslateY() >= (bounds.getMaxY()/2) - ball.getRadius()) {

                timeline.stop();
                //Εμφανίζει το μήνυμα στην οθόνη
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.BLACK);
                gc.setFont(new Font("", 40));
                gc.fillText("GAME OVER!! Your score is: " + scoreCounter, 50, 300);
            }

            //Αν υπάρχουν ακόμη τουβλάκια στη λίστα το παιχνίδι συνεχίζεται αφαιρώντας το τουβλάκι που ακούμπησε η μπάλα
            if(!bricks.isEmpty()){
                bricks.removeIf(brick -> checkCollisionBricks(brick));

                //Διαφορετικά ο παίκτης κερδίζει το παιχνίδι
            } else {
                timeline.stop();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.BLACK);
                gc.setFont(new Font("", 40));
                gc.fillText("YOU WON!! Your score is: " + scoreCounter, 50, 300);
            }

        }
    }));

    //Ρυθμίζει την πορεία της μπάλας όταν χτυπάει στα τουβλάκια. Αν αφαιρεθεί τουβλάκι η συνάρτηση επιστρέφει true
    public boolean checkCollisionBricks(Rectangle brick){

        if(ball.getBoundsInParent().intersects(brick.getBoundsInParent())){

            boolean rightBorder = ball.getLayoutX() >= ((brick.getX() + brick.getWidth()) - ball.getRadius());
            boolean leftBorder = ball.getLayoutX() <= (brick.getX() + ball.getRadius());
            boolean bottomBorder = ball.getLayoutY() >= ((brick.getY() + brick.getHeight()) - ball.getRadius());
            boolean topBorder = ball.getLayoutY() <= (brick.getY() + ball.getRadius());

            if (rightBorder || leftBorder) {
                ball.setRotate(rand.nextInt(maxA + 1 - minA) + minA);
                deltaX *= -1;
            }
            if (bottomBorder || topBorder) {
                ball.setRotate(rand.nextInt(maxA + 1 - minA) + minA);
                deltaY *= -1;
            }
            scene.getChildren().remove(brick);//Αφαιρεί το τουβλάκι από την οθόνη όταν το ακουμπήσει η μπάλα
            scoreCounter += 10;
            score.setText("Score: " + scoreCounter);

            return true;
        }
        return false;
    }

    //Για να κινείται η μπάρα προς τα δεξιά
    public void moveRight(){

        Bounds bounds = scene.getBoundsInLocal();

        //Ρυθμίζει την ταχύτητα κίνησης
        //Αλλάζει τη θέση του
        rod.setTranslateX(rod.getTranslateX() + rodSpeed);

        //Άμα φτάσει στο όριο της οθόνης εμφανίζεται από την άλλη μεριά
        if(rod.getTranslateX() >= (bounds.getMaxX()/2)){
            rod.setTranslateX(rod.getTranslateX() - rodSpeed);
        }

    }

    public void moveLeft() {

        Bounds bounds = scene.getBoundsInLocal();

        rod.setTranslateX(rod.getTranslateX() - rodSpeed);

        if(rod.getTranslateX() <= (-(bounds.getMaxX()/2))){
            rod.setTranslateX(rod.getTranslateX() + rodSpeed);
        }
    }

    @FXML //Εμφανίζει τα τουβλάκια στην οθόνη
    private void createBricks(){
        double width = 640;
        double height = 200;

        int spaceCheck = 1;

        for (double i = height; i > 0 ; i = i - 40) {
            for (double j = width; j > 0 ; j = j - 15) {
                if(spaceCheck % 3 == 0){
                    Rectangle brick = new Rectangle(j,i,40,15);
                    brick.setFill(Color.FIREBRICK);
                    scene.getChildren().add(brick);
                    bricks.add(brick);
                }
                spaceCheck++;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        material.setDiffuseColor(Color.YELLOW);
        material.setSpecularColor(Color.BLACK);
        ball.setMaterial(material);


        score.setText("Score: " + scoreCounter);
        createBricks();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}