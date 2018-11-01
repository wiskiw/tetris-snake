package yablonski.a;

import yablonski.a.view.MainView;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static final Long SPEED = 200L;

    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.setVisible(true);

        Timer timer = new Timer("Tick");
        long delay = SPEED + 1000;
        long period = SPEED;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                mainView.update();
            }
        }, delay, period);
    }

}
