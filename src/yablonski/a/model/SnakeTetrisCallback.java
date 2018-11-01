package yablonski.a.model;

public interface SnakeTetrisCallback {

    void onGameUpdate(FieldMap map, int score);

    void onPauseUpdate(String msg);


}
