package yablonski.a.presenter;

import yablonski.a.model.FieldMap;
import yablonski.a.model.SnakeTetris;
import yablonski.a.model.SnakeTetrisCallback;
import yablonski.a.model.input.KeyEventController;
import yablonski.a.view.MainViewInterface;

import java.awt.event.KeyEvent;

public class MainPresenter implements SnakeTetrisCallback {

    private MainViewInterface view;

    private SnakeTetris snakeTetris;
    private KeyEventController keyEventController;

    public MainPresenter(MainViewInterface view, int mapWidth, int mapHeight) {
        this.view = view;
        this.keyEventController = new KeyEventController();
        this.snakeTetris = new SnakeTetris(this, mapWidth, mapHeight);
    }

    public void onKeyPress(KeyEvent event) {
        keyEventController.onKeyPress(event);
    }

    public void tickUpdate() {
        if (keyEventController.hasPress()) {
            snakeTetris.onKeyPress(keyEventController.getPressedKey());
        }
        keyEventController.tickUpdate();
        snakeTetris.update();
    }

    private void redrawMap(FieldMap fieldMap) {
        view.clearGameField();
        fieldMap.getBlocks().forEach(block ->
                view.updateSquare(block.getX(), block.getY(), block.getColor()));
    }


    @Override
    public void onGameUpdate(FieldMap map, int score) {
        redrawMap(map);
        view.updateScore(score);
    }

    @Override
    public void onPauseUpdate(String msg) {
        //redrawMap();
        if (msg != null) {
            view.showMessage(msg);
        }
    }
}
