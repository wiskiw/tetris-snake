package yablonski.a.model.map.items;

import yablonski.a.model.Block;

import java.awt.*;
import java.util.ArrayList;

public class Food implements MapCreature {

    private ArrayList<Block> body = new ArrayList<>();
    private Block block;

    public static Color randomColor() {
        return Color.BLUE;
    }

    public Food(int x, int y, Color color) {
        block = new Block(x, y, color);
        body.add(block);
    }

    public Food(int x, int y) {
        block = new Block(x, y, randomColor());
        body.add(block);
    }

    @Override
    public boolean isIn(int x, int y) {
        return this.block.getX() == x && this.block.getY() == y;
    }

    @Override
    public ArrayList<Block> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Food {" + block + '}';
    }
}
