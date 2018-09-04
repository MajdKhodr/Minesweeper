import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Majd Khodr 
 */


public class GameController implements ActionListener {

    // ADD YOUR INSTANCE VARIABLES HERE

    private GameModel gameModel;
    private GameView gameView;

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {

    // ADD YOU CODE HERE

        gameModel = new GameModel(width, height, numberOfMines); 
        gameView = new GameView(gameModel, this);
    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        
    // ADD YOU CODE HERE

        if (e.getActionCommand().equals("Reset")) {
            reset();
        }
        
        else if (e.getActionCommand().equals("Quit")) {
            System.exit(0);
        }

        else {

            DotButton clickedButton = (DotButton) e.getSource();

            gameModel.step();

            int x = clickedButton.getColumn();
            int y = clickedButton.getRow();

            play(x, y);
        }
    }


    /**
     * resets the game
     */
    private void reset(){

    // ADD YOU CODE HERE

        gameModel.reset();
        gameView.update();
    }


    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int heigth){

    // ADD YOU CODE HERE

        String[] window = {"Quit", "Play Again"};

        if (!gameModel.hasBeenClicked(heigth, width) && !gameModel.isMined(heigth, width) && !gameModel.isBlank(heigth, width)) {
            gameModel.click(heigth, width);
            gameModel.uncover(heigth, width);
            gameView.update();
        }

        else if (!gameModel.hasBeenClicked(heigth, width) && gameModel.isMined(heigth, width)) {
            gameModel.click(heigth, width);
            gameModel.uncover(heigth, width);
            gameModel.uncoverAll();
            gameView.update();

            int option = JOptionPane.showOptionDialog(null, "Aouch, you lost in  " + gameModel.getNumberOfSteps() + " steps! \n Would you like to play again?", "Boom!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, window, 0);
            
            if (option == 0) {
                System.exit(0);
            }

            else if (option == 1) {
                reset();
            }
        }

        else if (!gameModel.hasBeenClicked(heigth, width) && gameModel.isBlank(heigth, width)) {
            clearZone(gameModel.get(heigth, width));
            gameView.update();
        }

        if (gameModel.isFinished()) {
            gameModel.uncoverAll();
            gameView.update();

            int answer = JOptionPane.showOptionDialog(null, "Congratulations, you won in " + gameModel.getNumberOfSteps() + " steps! \n Would you like to play again?", "Won", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, window, 0);
            
            if (answer == 0) {
                System.exit(0);
            }

            else if (answer == 1) {
                reset();
            }
        }
    }


   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered'' 
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    private void clearZone(DotInfo initialDot) {

    // ADD YOU CODE HERE

        Stack stack = new GenericArrayStack(gameModel.getWidth() * gameModel.getHeigth());
        stack.push(initialDot);

        while (!stack.isEmpty()) {

            DotInfo value = (DotInfo) stack.pop();

            int[] y = { value.getY() - 1, value.getY() - 1, value.getY() - 1, value.getY(), value.getY(), value.getY() + 1, value.getY() + 1, value.getY() + 1 };
            int[] x = { value.getX() - 1, value.getX(), value.getX() + 1, value.getX() - 1, value.getX() + 1, value.getX() - 1, value.getX(), value.getX() + 1 };

            int index = 0;

            for (int item : y) {             
                
                if ((item >= 0) && (item < gameModel.getHeigth()) && (x[index] >= 0) && (x[index] < gameModel.getWidth()) && gameModel.isCovered(item, x[index])) {
                    
                    gameModel.uncover(item, x[index]);
                    gameModel.click(item, x[index]);
                
                    if (gameModel.isBlank(item, x[index])) {                
                        stack.push(gameModel.get(item, x[index]));
                    }
                }

                index++;
            } 
        }
    }


}

