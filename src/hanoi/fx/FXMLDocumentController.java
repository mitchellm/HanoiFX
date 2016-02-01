package hanoi.fx;

import hanoi.entities.Disc;
import hanoi.entities.Tower;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;


public class FXMLDocumentController {
    
    private Tower[] towers = new Tower[3];
    private int n, start,aux,dest;
    private boolean gameSet = false;
    private boolean solved = false;
    private List<int[]> moves;
    private Tower a, b;
    
    @FXML
    private Label movesNeeded;
   
    @FXML
    private Label movesMade;
    
    @FXML
    private Label output;
    
    @FXML
    private Label selectedA;
    
    @FXML
    private Label selectedB;
    
    @FXML
    private TextField aIn;

    @FXML
    private Button clear;

    @FXML
    private Button construct;

    @FXML
    private TextField dIn;

    @FXML
    private TextField nIn;

    @FXML
    private TextField sIn;

    @FXML
    private Button solve;

    @FXML
    private Pane view;

    @FXML
    void initialize() {
        assert view != null : "fx:id=\"view\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        moves = new ArrayList<>();
    }
    
    @FXML
    void stepButton() {
        if(!gameSet || moves.size() < 1)
            return;
        
        if(solved)
            return;
        Iterator<int[]> it = moves.iterator();
        int[] move = null;
        while(it.hasNext()) {
            move = it.next();
            towers[move[0]].send(towers[move[1]]);
            break;
        }
        moves.remove(0);
        if(moves.size() < 1)
            this.solved = true;
    }
    
    @FXML
    void constructButton() {
        if(gameSet)
            this.clearGame();
        
        this.n = Integer.parseInt(nIn.getText());
        this.start = Integer.parseInt(sIn.getText());
        this.aux = Integer.parseInt(aIn.getText());
        this.dest = Integer.parseInt(dIn.getText());
        
        if(!this.validate(this.n,this.start,this.aux,this.dest))
            return;
        
        calculateMoves(n,start,aux,dest);
        output.setText("Game started!");
        handleGame(n,start,aux,dest);
        int x = (int) Math.pow(2, n) -1;
        movesNeeded.setText(String.valueOf(x));
        movesMade.setText("Zero");
    }
    
    @FXML
    void clearButton() {
        this.clearGame();
    }
    
    @FXML
    void solveButton() {
        if(this.solved) {
            System.out.println("Already solved...");
            return;
        }
        
        if(!this.gameSet) {
            System.out.println("Game not set...");
            return;
        }
        
        this.recusiveSolution(this.n, this.start, this.aux, this.dest, towers);
        this.solved = true;
        System.out.println("Hanoi tower of " + this.n + " discs solved with all discs on tower [" + this.dest + "]");
    }
    
    /**
     * These following methods handle the click moving in a really, really messy way. 
     * should probably do something different
     */
    
    @FXML
    void clickTowerA() {
        if(!gameSet)
            return;
        
        if(a == null) {
            a = towers[0];
            selectedA.setText("Zero");
            selectedB.setText("");
        } else if(b == null) {
            b = towers[0];
            selectedB.setText("Zero");
            handleClickMove();
        } else {
            a = towers[0];
            selectedA.setText("Zero");
            selectedB.setText("");
            b = null;
        }
    }
    
    @FXML
    void clickTowerB() {
        if(!gameSet)
            return;
        
        if(a == null) {
            a = towers[1];
            selectedA.setText("One");
            selectedB.setText("");
        } else if(b == null) {
            b = towers[1];
            selectedB.setText("One");
            handleClickMove();
        } else {
            a = towers[1];
            selectedA.setText("One");
            selectedB.setText("");
            b = null;
        }
    }
    
    @FXML
    void clickTowerC() {
        if(!gameSet)
            return;
        
        if(a == null) {
            a = towers[2];
            selectedA.setText("Two");
            selectedB.setText("");
        } else if(b == null) {
            b = towers[2];
            selectedB.setText("Two");
            handleClickMove();
        } else {
            a = towers[2];
            selectedA.setText("Two");
            selectedB.setText("");
            b = null;
        }
    }
    
    /**
     * Creates and handles the game as well as the interface
     * @param discs
     * @param start
     * @param aux
     * @param dest 
     */
    void handleGame(int discs, int start, int aux, int dest) {
        Tower zero = new Tower(0, view);
        Tower one = new Tower(1, view);
        Tower two = new Tower(2, view);
        towers[0] = zero;
        towers[1] = one;
        towers[2] = two;
        towers = Disc.init(discs, towers, start);
        gameSet = true;
    }
    
    /**
     * Clears the game interface
     */
    void clearGame() {
        ArrayList<Disc> dList = null;
        Disc d = null;
        for(int i = 0; i < towers.length; i++) {
            dList = towers[i].getAll();
            Iterator<Disc> it = dList.iterator();
            while(it.hasNext()) {
                d = it.next();
                view.getChildren().remove(d.get());
            }
        }
        gameSet = false;
        solved = false;
    }
    
    /**
     * Solves the Hanoi Tower with recursion
     * @param n
     * @param start
     * @param aux
     * @param dest
     * @param towers 
     */
    void recusiveSolution(int n, int start, int aux, int dest, Tower[] towers) {
        if(n == 1) {
            //actually moves the disc from start to dest
            towers[start].send(towers[dest]);
        } else {
            recusiveSolution(n-1,start,dest,aux, towers);
            towers[start].send(towers[dest]);
            recusiveSolution(n-1,aux,start,dest, towers);
        }
    }
    /**
     * Solves the Hanoi Tower with recursion and produces a list of moves to solve puzzle
     * @param n
     * @param start
     * @param aux
     * @param dest
     * @param towers 
     */
    void calculateMoves(int n, int start, int aux, int dest) {
        if(n == 1) {
            moves.add(new int[] { start, dest });
        } else {
            //move start -> dest
            calculateMoves(n - 1, start, dest, aux);
            moves.add(new int[] { start, dest });
            calculateMoves(n-1,aux,start,dest);
        }
    }
    
    /**
     * Handles the click moves
     */
    void handleClickMove() {
        output = a.send(b, output);
        if(!a.lastAttempt())
            return;
        
        if(movesMade.getText() == "Zero") {
            movesMade.setText("1");
            return;
        }
        int moves = Integer.parseInt(movesMade.getText());
        moves++;
        movesMade.setText(String.valueOf(moves));
    }
    
    boolean validate(int n, int start, int aux, int dest) {
        if(start < 0 || start > 3 || dest < 0 || dest > 3 || aux < 0 || aux > 3) {
            output.setText("You set tower values out of range [0,3]");
            return false;
        }
        
        if(n > 25) {
            int x = (int) Math.pow(2, 25) -1;
            output.setText("Disc count cannot be larger than 25 or solver will be too slow as it requires " + x + " moves at n=25");
            return false;
        }
        return true;
    }

}
