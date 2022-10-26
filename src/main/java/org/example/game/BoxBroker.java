package org.example.game;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Random;

public class BoxBroker {

    public VBox root;

    // background
    final int width = 600,height = 600;
    final int col = 10, row = 10;
    final int w = width/col, h = height/row;

    final Color emptyBox = Color.WHITE;
    final Color wallBox = Color.web("#5a5c5a");
    final Color mirrorBox = Color.web("#7ae0e6");
    final Color woodBox = Color.web("#e394be");

    class Box extends Rectangle{
        int life;
        int x,y; // position of box
        Box[] neighbours = new Box[4];
        Box(int _life, int _x, int _y){
            life = _life;
            x = _x;
            y = _y;
            setLayoutX(x*w);
            setLayoutY(y*h);
            setWidth(w);
            setHeight(h);
            updateColor();
            setOnMouseClicked(e->{
                if(life<=0)return;
                loseLife();
                updateColor();
                for (Box neighbour : neighbours) {
                    if(neighbour != null) {
                        neighbour.loseLife();
                        neighbour.updateColor();
                    }
                }
            });
        }
        void loseLife(){
            if(life > 0)
                life--;
        }
        void updateColor(){
            switch (life){
                case 2:
                    setFill(woodBox);
                    setStroke(Color.web("#ff54af"));
                    setStrokeWidth(3);
                    break;
                case 1:
                    setFill(mirrorBox);
                    setStroke(Color.web("#21d5db"));
                    setStrokeWidth(3);
                    break;
                case -1:
                    setFill(wallBox);
                    setStroke(Color.BLACK);
                    setStrokeWidth(3);
                    break;
                case 0:
                    setFill(emptyBox);
                    setStroke(Color.GREY);
                    setStrokeWidth(3);
            }
        }

        void addNeighbours(){
            // add neighbours from left and right.
            if(x > 0)
                neighbours[0] = boxes[x - 1][y];
            if(x < col-1)
                neighbours[1] = boxes[x + 1][y];
            if (y > 0) // from up and down
                neighbours[2] = boxes[x][y-1];
            if (y < row-1)
                neighbours[3] = boxes[x][y+1];
        }
    }

    Box[][] boxes = new Box[col][row];


    public BoxBroker(){
        root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(10);
        Pane pane = new Pane();

        // let's do setup
        // initialize the boxes
        for(int i = 0; i< col; i++){
            for(int j = 0; j<row; j++){
                boxes[i][j] = new Box(new Random().nextInt(4)-1,i,j);
                pane.getChildren().add(boxes[i][j]);
            }
        }
        // add the neighbours
        for(int i = 0; i< col; i++){
            for(int j = 0; j<row; j++){
                boxes[i][j].addNeighbours();
            }
        }
        root.getChildren().addAll(getTop(),pane,getBottom());
    }

    HBox getTop(){
        HBox left = new HBox();
        left.setAlignment(Pos.TOP_LEFT);
        Label lvl = getLabel("Level: NOT DEFINED",18);
        Label score = getLabel("Score: Not Counting",18);
        left.getChildren().add(lvl);
        HBox right = new HBox();
        right.setAlignment(Pos.TOP_RIGHT);
        right.getChildren().add(score);

        HBox box = new HBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.getChildren().addAll(left,right);
        return box;
    }
    HBox getBottom(){
        Label msg = getLabel("MSG: ",16);
        HBox box = new HBox();
        box.getChildren().add(msg);
        return box;
    }

    Label getLabel(String txt, int font_size){
        Label label = new Label(txt);
        label.setFont(Font.font("Arial",font_size));
        label.setTextFill(Color.DARKGREEN);
        return label;
    }

    public VBox getRoot() {
        return root;
    }
}
