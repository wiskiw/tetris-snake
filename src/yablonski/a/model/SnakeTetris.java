package yablonski.a.model;

import yablonski.a.model.input.KeyActionTranslator;
import yablonski.a.model.map.items.Creature;
import yablonski.a.model.map.items.Food;
import yablonski.a.model.map.items.TetrisCreature;

import java.awt.event.KeyEvent;

public class SnakeTetris implements KeyActionTranslator.Callback {

    private KeyActionTranslator keyTranslator;
    private SnakeTetrisCallback callback;
    private FieldMap map;
    private int score = 0;
    private CreatureState creatureState = CreatureState.SNAKE;

    private Creature creature;
    private Food food;

    public SnakeTetris(SnakeTetrisCallback callback, int mapWidth, int mapHeight) {
        map = new FieldMap(mapWidth, mapHeight);
        keyTranslator = new KeyActionTranslator(this);
        this.callback = callback;
        spawnSnakeFood();
    }

    private void spawnSnakeFood() {
        creature = map.spawnSnake(4);
        if (food == null) {
            food = map.spawnFood();
        }
    }

    public void onKeyPress(KeyEvent event) {
        keyTranslator.process(event);
    }

    // tick update method
    public void update() {
        switch (creatureState) {
            case SNAKE:
                if (map.isNextStepAllow(creature)) {
                    creature.moveSnake();
                    if (food.isIn(creature.getHead().getX(), creature.getHead().getY())) {
                        // есть еду
                        map.remove(food);
                        food = null;
                        creatureState = CreatureState.TETRIS_FALLING;
                        creature.recolor(TetrisCreature.PIEC_COLOR);
                    }
                } else {
                    creatureState = CreatureState.TETRIS_FALLING;
                    creature.recolor(TetrisCreature.PIEC_COLOR);
                }
                break;

            case TETRIS_FALLING:
                if (map.isPillarBellow(creature)) {
                    spawnSnakeFood();
                    map.checkLayers(creature, food);
                    creatureState = CreatureState.SNAKE;
                } else {
                    creature.moveTetrisDown();
                }
                break;
        }
        callback.onGameUpdate(map, score);
    }

    @Override
    public void goUp() {
        creature.setMovingDirection(Direction.UP);
    }

    @Override
    public void goDown() {
        creature.setMovingDirection(Direction.DOWN);
    }

    @Override
    public void goRight() {
        creature.setMovingDirection(Direction.RIGHT);
    }

    @Override
    public void goLeft() {
        creature.setMovingDirection(Direction.LEFT);
    }

    @Override
    public void action() {
        //score = 0;
        //callback.onGameUpdate(map, score);
    }
}
