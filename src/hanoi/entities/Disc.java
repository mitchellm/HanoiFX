package hanoi.entities;


import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Mitchell
 */
public class Disc {
    private Ellipse disc;
    private int size;
    
    /**
     * Disc is created given size, width and height
     * @param size
     * @param radX
     * @param radY 
     */
    public Disc(int size, int radX, int radY) {
        this.size = size;
        radX = radX*size;
        this.disc = new Ellipse(0, 0, radX, radY);
        this.disc.setStroke(Color.GOLD);
        this.disc.setStrokeWidth(2);
        this.disc.setFill(Color.TRANSPARENT);
    }
    
    /**
     * Creates a new disc at 0,0
     */
    public Disc() {
        this.disc = new Ellipse(0,0,0,0);
        this.disc.setStroke(Color.GOLD);
    }
    
    /**
     * Creates n discs and builds them onto starting tower
     * @param n discs
     * @param t array of towers
     * @param start start tower id
     * @return updated array of towers
     */
    public static Tower[] init(int n, Tower[] t, int start) {
        ArrayList<Disc> d = new ArrayList();
        while(n > 0) {
            d.add(new Disc(n, 5, 5));
            n--;
        }
        Iterator<Disc> list = d.iterator();
        while(list.hasNext()) {
            t[start].add(list.next(), false);
        }
        return t;
    }
    
    /**
     * Gets the ellipse from disc
     * @return 
     */
    public Ellipse get() {
        return this.disc;
    }
    
    /**
     * Gets the size of disc
     * @return 
     */
    public int size() {
        return this.size;
    }
    
    /**
     * Sets the ellipse center x
     * @param x 
     */
    public void setX(int x) {
        this.disc.setCenterX(x);
    }
    
    /**
     * Sets the ellipse center y
     * @param y 
     */
    public void setY(int y) {
        this.disc.setCenterY(y);
    }
    
    /**
     * Set the x radius
     * @param radX 
     */
    public void setRadX(int radX) {
        this.disc.setRadiusX(radX);
    }
    
    /**
     * Set the y radius
     * @param radY 
     */
    public void setRadY(int radY) {
        this.disc.setRadiusY(radY);
    }
}
