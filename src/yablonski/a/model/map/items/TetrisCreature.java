package yablonski.a.model.map.items;

import java.awt.*;

// фигура кубика
public interface TetrisCreature extends MovableMapCreature {

    Color PIEC_COLOR = new Color(186, 90, 0);

    //сдвинуть вниз
    void moveTetrisDown();

}
