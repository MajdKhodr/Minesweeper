import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>DotButton</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Majd Khodr
 */

public class GameView extends JFrame {

     // ADD YOUR INSTANCE VARIABLES HERE

    private DotButton[][] board;
    private GameModel gameModel;
    private JLabel nbreOfStepsLabel;


    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        
    // ADD YOU CODE HERE

        this.gameModel = gameModel;
        this.nbreOfStepsLabel = new JLabel("Number of Steps: " + gameModel.getNumberOfSteps());
        board = new DotButton[gameModel.getHeigth()][gameModel.getWidth()];
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel(new GridLayout(gameModel.getHeigth(), gameModel.getWidth())); 


        JButton reset = new JButton ("Reset");
        reset.addActionListener(gameController);

        JButton quit = new JButton ("Quit");
        quit.addActionListener(gameController);

        panel.add(nbreOfStepsLabel);
        panel.add(reset);
        panel.add(quit);

        add(panel, BorderLayout.SOUTH);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                DotButton button = new DotButton(j, i, 11);
                button.addActionListener(gameController);
                button.setPreferredSize(new Dimension(25, 25));
                panel2.add(button);
                board[i][j] = button;
            }
        }

        add(panel2);

        setResizable(false);
        getRootPane().setBorder(BorderFactory.createMatteBorder(8, 8, 4, 8, Color.WHITE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */
    public void update(){
        
    // ADD YOU CODE HERE

        this.nbreOfStepsLabel.setText("Number of Steps: " + gameModel.getNumberOfSteps());

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                    int numOfMines = getIcon(i, j);
                    board[i][j].setIconNumber(numOfMines);       
            }
        }
       
        repaint();
    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */   
    private int getIcon(int i, int j){
        
    // ADD YOU CODE HERE

       //Couple of if statements

        if (gameModel.hasBeenClicked(i,j)) {

            if (!(gameModel.isCovered(i, j)) && gameModel.isMined(i, j)) {
                return board[i][j].CLICKED_MINE;
            }

            else if (gameModel.isMined(i ,j)){
                return board[i][j].MINED;
            }

            else{
                return gameModel.getNeighbooringMines(i, j);
            }
        }

        return board[i][j].COVERED;  
    }

}

