package yablonski.a.model;

import yablonski.a.model.input.KeyActionTranslator;
import yablonski.a.model.map.items.Food;
import yablonski.a.model.map.items.SnakeCreature;

import java.awt.event.KeyEvent;

public class SnakeTetris implements KeyActionTranslator.Callback {

    private KeyActionTranslator keyTranslator;
    private SnakeTetrisCallback callback;
    private FieldMap map;
    private int score = 0;
    private GameState gameState = GameState.SNAKE;

    private SnakeCreature snakeCreature;
    private Food food;

    public SnakeTetris(SnakeTetrisCallback callback, int mapWidth, int mapHeight) {
        map = new FieldMap(mapWidth, mapHeight);
        keyTranslator = new KeyActionTranslator(this);
        this.callback = callback;

        snakeCreature = map.spawnSnake(4);
        food = map.spawnFood();
    }


    public void onKeyPress(KeyEvent event) {
        keyTranslator.process(event);
    }

    // tick update method
    public void update() {
        //System.out.println("update");
        callback.onGameUpdate(map, score);
        snakeCreature.moveSnake();

        if (food.isIn(snakeCreature.getHead().getX(), snakeCreature.getHead().getY())){
            map.remove(food);
            food = map.spawnFood();
        }
    }

    @Override
    public void goUp() {
        snakeCreature.setMovingDirection(Direction.UP);
    }

    @Override
    public void goDown() {
        snakeCreature.setMovingDirection(Direction.DOWN);
    }

    @Override
    public void goRight() {
        snakeCreature.setMovingDirection(Direction.RIGHT);
    }

    @Override
    public void goLeft() {
        snakeCreature.setMovingDirection(Direction.LEFT);
    }

    @Override
    public void action() {
        //score = 0;
        //callback.onGameUpdate(map, score);
    }
}
