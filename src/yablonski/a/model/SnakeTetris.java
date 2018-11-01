package yablonski.a.model;

import yablonski.a.model.input.KeyActionTranslator;

import java.awt.event.KeyEvent;

public class SnakeTetris implements KeyActionTranslator.Callback {

    private KeyActionTranslator keyTranslator;
    private SnakeTetrisCallback callback;
    private FieldMap map;
    private int score = 0;

    public SnakeTetris(SnakeTetrisCallback callback, int xSize, int ySize) {
        map = new FieldMap(xSize, ySize);
        keyTranslator = new KeyActionTranslator(this);
        this.callback = callback;
    }


    public void onKeyPress(KeyEvent event) {
        keyTranslator.process(event);
    }

    // tick update method
    public void update() {
        //System.out.println("update");
    }

    @Override
    public void goUp() {
        callback.onGameUpdate(map, ++score);

    }

    @Override
    public void goDown() {
        callback.onGameUpdate(map, --score);
    }

    @Override
    public void goRight() {

    }

    @Override
    public void goLeft() {

    }

    @Override
    public void action() {
        score = 0;
        callback.onGameUpdate(map, score);
    }
}
