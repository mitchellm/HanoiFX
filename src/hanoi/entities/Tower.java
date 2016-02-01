package hanoi.entities;

import java.awt.Point;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mitchell
 */
public class Tower {

    private int id;
    private ArrayList<Disc> discs = new ArrayList();
    private Line tower;
    private static final Point[] START_POSITIONS = {new Point(120, 300), new Point(390, 300), new Point(640, 300)};
    private static final Point[] END_POSITIONS = {new Point(120, 0), new Point(390, 0), new Point(640, 0)};
    private Pane p;
    private boolean lastAttempt = false;

    /**
     * Draws tower (lines) on the pane based on it's ID
     * @param id
     * @param p 
     */
    public Tower(int id, Pane p) {
        this.id = id;
        this.p = p;
        tower = new Line(START_POSITIONS[id].x, START_POSITIONS[id].y, END_POSITIONS[id].x, END_POSITIONS[id].y);
        p.getChildren().add(this.get());
    }

    /**
     * Adds a disc to current tower
     * @param d Disc to add to this tower
     */
    public void add(Disc d, boolean transition) {
        //Check for other discs so placement is known
        int factor = discs.size();
        if(true) {
            d.setX(START_POSITIONS[this.id].x);
            d.setY(START_POSITIONS[this.id].y - (factor * 10));
            this.discs.add(d);
            this.p.getChildren().add(d.get());
        } else {
            d.get().setTranslateX(START_POSITIONS[this.id].x);
            d.get().setTranslateY(START_POSITIONS[this.id].y - (factor * 20));
            this.p.getChildren().add(d.get());
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(4),d.get());
            translateTransition.setFromX(d.get().getCenterX());
            translateTransition.setToX(d.get().getCenterX());
            translateTransition.setCycleCount(Timeline.INDEFINITE);
            translateTransition.setAutoReverse(true);        
            translateTransition = TranslateTransitionBuilder.create()
                    .duration(Duration.seconds(4))
                    .node(d.get())
                    .fromX(20)
                    .toX(380)
                    .cycleCount(Timeline.INDEFINITE)
                    .build();
            translateTransition.play();
        }
    }

    /**
     * Sends top disc to tower
     * @param to Tower to send the disc to Sends the top disc on the tower
     */
    public void send(Tower to) {
        Disc d = null;
        Iterator<Disc> it = this.discs.iterator();
        while (it.hasNext()) {
            d = it.next();
        }
        
        if(d == null) {
            System.out.println("No disc to send!");
            return;
        }
        
        if (canMove(to)) {
            this.remove(d);
            to.add(d, true);
            System.out.println("Tower [" + this.id + "] has sent top disc to Tower [" + to.id + "]");
        } else {
            System.out.println("Cannot move large disc " + d.size() + " onto disc " + to.next().size());
        }
    }
    
    /**
     * Sends top disc to tower (only used for the click moving)
     * @param to Tower to send the disc to Sends the top disc on the tower
     * @param output Label to update with output
     */
    public Label send(Tower to, Label output) {
        Disc d = null;
        Iterator<Disc> it = this.discs.iterator();
        while (it.hasNext()) {
            d = it.next();
        }
        
        if(d == null) {
            output.setText("No disc to send!");
            return output;
        }
        
        if (canMove(to)) {
            this.remove(d);
            to.add(d, true);
            this.lastAttempt = true;
            output.setText("Tower [" + this.id + "] has sent top disc to Tower [" + to.id + "]");
        } else {
            this.lastAttempt = false;
            output.setText("Cannot make that move");
            //System.out.println("Cannot move large disc " + d.size() + " onto disc " + to.next().size());
        }
        return output;
    }

    /**
     * Get's the top disc from tower
     * @return Disc next disc
     */
    public Disc next() {
        Disc d = null;
        Iterator<Disc> it = this.discs.iterator();
        while (it.hasNext()) {
            d = it.next();
        }
        return d;
    }

    /**
     * Removes disc from tower
     * @param d Disc to remove
     */
    public void remove(Disc d) {
        if (d == null) {
            System.out.println("Cannot remove disc as object is null!");
            return;
        }
        this.discs.remove(d);
        this.p.getChildren().remove(d.get());
    }

    /**
     * Gets the tower's line
     * @return Line tower
     */
    public Line get() {
        return this.tower;
    }

    /**
     * Gets all of the tower's discs
     * @return ArrayList discs 
     */
    public ArrayList getAll() {
        return this.discs;
    }

    /**
     * Can move next disc to tower to
     * @param to
     * @return boolean can move
     */
    public boolean canMove(Tower to) {

        Disc a = this.next();
        Disc b = to.next();
        if (b == null) {
            return true;
        }

        return b.size() > a.size();
    }
     
    /**
     * Can move next disc to tower to
     * @param to
     * @return boolean can move
     */
    public boolean lastAttempt() {
        return this.lastAttempt;
    }
    
    public boolean containsTower(Line l) { 
        return this.tower == l;
    }

}
