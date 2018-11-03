package yablonski.a.model.map.items;

import yablonski.a.model.Block;

import java.awt.*;
import java.util.ArrayList;

public interface MapCreature {

    // содержится ли часть фигуры в точке Х Y
    boolean isIn(int x, int y);

    ArrayList<Block> getBody();

    void recolor(Color color);

}
