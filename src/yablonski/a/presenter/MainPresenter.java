package yablonski.a.presenter;

import yablonski.a.view.MainViewInterface;

import java.awt.*;

public class MainPresenter {

    private MainViewInterface view;

    public MainPresenter(MainViewInterface view) {
        this.view = view;
    }

    public void init() {
        view.updateSquare(5, 6, new Color(0, 222, 33));
        view.updateSquare(5, 7, new Color(250, 126, 3));
        view.updateSquare(6, 7, new Color(3, 126, 126));

    }


}
