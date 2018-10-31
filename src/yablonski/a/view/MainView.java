package yablonski.a.view;

import javafx.util.Pair;
import yablonski.a.presenter.MainPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainView extends JFrame implements MainViewInterface {

    private MainPresenter presenter;

    private static final int CONTROLS_BAR_HEIGHT = 50;

    private static final int WINDOW_HEIGHT = 550;
    private static final int WINDOW_WIDTH = 400;


    private static final double GAME_SQUARE_SIZE_FACTOR = 1.3;

    private static int gameFieldSizeX = (int) (25 / GAME_SQUARE_SIZE_FACTOR);
    private static int gameFieldSizeY = (int) (30 / GAME_SQUARE_SIZE_FACTOR);


    private JLabel scoreLabel = new JLabel("Score: -- ");
    private JPanel gameFieldPane;
    private JPanel controlPane;

    private JPanel[][] gameSquaresHolder = new JPanel[gameFieldSizeX][gameFieldSizeY];

    public MainView() throws HeadlessException {
        super("Snake-Tetris");
        presenter = new MainPresenter(this);

        this.setBounds(500, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Game field
        prepareGameField();

        // Control/stats field
        prepareControlBar();


        JPanel panesHolder = new JPanel();
        panesHolder.setLayout(new BoxLayout(panesHolder, BoxLayout.Y_AXIS));
        panesHolder.add(gameFieldPane);
        panesHolder.add(controlPane);

        this.add(panesHolder);


        presenter.init();
    }

    private void prepareGameField() {
        gameFieldPane = new JPanel();
        gameFieldPane.setMaximumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT - CONTROLS_BAR_HEIGHT));

        GridLayout layout = new GridLayout(gameFieldSizeY, gameFieldSizeX);
        gameFieldPane.setLayout(layout);

        JLabel l;
        for (int y = 0; y < gameFieldSizeY; y++) {
            for (int x = 0; x < gameFieldSizeX; x++) {
                gameSquaresHolder[x][y] = new JPanel();


                /*
                //debug grid
                l = new JLabel(x + ":" + y);
                l.setFont(new Font(l.getFont().getName(), Font.PLAIN, 8));
                gameSquaresHolder[x][y].setBackground(new Color(y * 5, x * 0, 0));
                gameSquaresHolder[x][y].add(l);
                */

                gameFieldPane.add(gameSquaresHolder[x][y]);
            }
        }
    }

    private void prepareControlBar() {
        controlPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPane.setBackground(new Color(250, 126, 3));
        controlPane.add(scoreLabel);
        controlPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, CONTROLS_BAR_HEIGHT));


        // Changing Score text size, padding
        scoreLabel.setFont(new Font(scoreLabel.getFont().getName(), Font.PLAIN, 16));
        scoreLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    @Override
    public boolean updateSquare(int x, int y, Color color) {
        if (x < 0 || y < 0) {
            return false;
        }
        if (y < gameSquaresHolder.length && x < gameSquaresHolder[y].length) {
            gameSquaresHolder[y][x].setBackground(color);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateScore(int score) {
        if (score >= 0) {
            scoreLabel.setVisible(true);
            scoreLabel.setText("Score: " + score);
        } else {
            scoreLabel.setText("Score: 0");
            scoreLabel.setVisible(false);
        }
    }

    @Override
    public void showMessage(String msg) {
        scoreLabel.setVisible(true);
        scoreLabel.setText(msg);
    }

    @Override
    public Pair<Integer, Integer> getGameFieldSize() {
        return new Pair<>(gameFieldSizeX, gameFieldSizeY);
    }
}
