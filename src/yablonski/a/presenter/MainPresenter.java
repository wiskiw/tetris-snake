package yablonski.a.presenter;

import yablonski.a.model.FieldMap;
import yablonski.a.model.input.KeyEventController;
import yablonski.a.model.SnakeTetris;
import yablonski.a.model.SnakeTetrisCallback;
import yablonski.a.view.MainViewInterface;

import java.awt.event.KeyEvent;

public class MainPresenter implements SnakeTetrisCallback {

    private MainViewInterface view;

    private SnakeTetris snakeTetris;
    private KeyEventController keyEventController;

    public MainPresenter(MainViewInterface view, int xSize, int ySize) {
        this.view = view;
        this.keyEventController = new KeyEventController();
        this.snakeTetris = new SnakeTetris(this, xSize, ySize);
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
        fieldMap.forEach((x, y, color) -> {
            view.updateSquare(x, y, color);
            return null;
        });
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
