package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Klasa zawierajaca pojedynczy element.
 * Przechowuje informacje o jego rodzaju, kolorze oraz aktualnym stanie obrotu.
 */
public class Element implements Cloneable {
    private ArrayList<Rectangle2D> rectangles;
    private int rotateState;
    private int elementShape;
    private Color color;

    /**
     * Konstruktor klasy Element Tworzacy nowy element.
     * Stan rotacji elementu ustawiany jest na 0;
     * @param a Lista obiektów klasy <code>Rectangle2D</code> o dlugosci 4, zawierajaca skladowe elementu.
     * @param elementShape Zmienna zawierajaca informacje o rodzaju elementu.
     */
    Element(ArrayList<Rectangle2D> a, int elementShape) {
        this.rectangles = a;
        this.elementShape = elementShape;
        this.rotateState = 0;
    }

    /**
     * Metoda zwracajaca obiekty klasy <code>Rectangle2D</code> odpowiadajace za skladowe elementu.
     * @return
     */
    public ArrayList<Rectangle2D> getShape() {
        return rectangles;
    }

    /**
     * Metoda zwracajaca aktualny stan obrotu elementu.
     * @return Wartosc z zakresu 0-3, stan obrotu elementu.
     */
    public int getRotateState() {
        return rotateState;
    }

    /**
     * Metoda zwracajaca rodzaj elementu.
     * @return Wartosc z zakresu 0-6, rodzaj elementu.
     */
    public int getShapeFamily() {
        return elementShape;
    }

    /**
     * Metoda zwracajaca kolor elementu.
     * @return Obiekt klasy <code>Color</code>
     */
    public Color getColor() {
        return color;
    }

    /**
     * Metoda Umozliwiajaca ustawienie stanu obrotu elementu.
     * @param rotateState Stan obrotu elementu.
     */
    public void setRotateState(int rotateState) {
        this.rotateState = rotateState;
        this.rotateState %= 4;
    }

    /**
     * Metoda umozliwiajaca ustawienie koloru elementu.
     * @param color Obiekt klasy <code>Color</code> definiujacy kolor elementu.
     */
    public void setElementColor(Color color) {
        this.color = color;
    }

    /**
     * Metoda umozliwiajaca glebokie klonowanie Obiektu klasy <code>Element</code>
     * @return Klon obiektu.
     */
    public Element clone() {
        ArrayList<Rectangle2D> newRectangles = new ArrayList<>();
        for (Rectangle2D r : rectangles) {
            newRectangles.add(new Rectangle2D.Double(r.getMinX(), r.getY(), r.getWidth(), r.getHeight()));
        }
        Element e = new Element(newRectangles, elementShape);
        e.setElementColor(color);
        e.setRotateState(rotateState);

        return e;
    }
}