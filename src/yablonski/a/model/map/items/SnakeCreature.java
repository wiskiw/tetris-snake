package yablonski.a.model.map.items;

import yablonski.a.model.Block;
import yablonski.a.model.Direction;
import yablonski.a.model.TurnDirection;

import java.awt.*;

public interface SnakeCreature extends MovableMapCreature {

    Color COLOR_HEAD = new Color(222, 104, 103);
    Color COLOR_BODY = new Color(222, 152, 127);

    // сдвинуть по направлению движения
    void moveSnake();

    // повернуть
    void turn(TurnDirection turnDirection);

    // изменить направления движения
    void setMovingDirection(Direction direction);

    Block getHead();

    void setHead(Block snakeHead, Direction movingDirection);

}
