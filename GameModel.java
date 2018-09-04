import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Majd Khodr
 */
public class GameModel {

     // ADD YOUR INSTANCE VARIABLES HERE

	private int heigthOfGame;
	private int widthOfGame;
	private int numberOfMines;
	private int numberUncovered;
	private int numberOfSteps;
	private DotInfo model[][];
    private java.util.Random generator;


    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {
        
    // ADD YOU CODE HERE

    	this.widthOfGame = width;
    	this.heigthOfGame = heigth;
    	this.numberOfMines = numberOfMines;
    	this.numberUncovered = 0;
    	this.numberOfSteps = 0;
    	this.model = new DotInfo[heigthOfGame][widthOfGame];
        this.generator = new java.util.Random();

    	for (int i = 0; i < this.heigthOfGame; i++) {
    		for (int j = 0; j < this.widthOfGame; j++) {
    			this.model[i][j] = new DotInfo(j, i);                
    		}
    	}

        int x = 0;
        int y = 0; 

        int counter = 0;

        while (counter < this.numberOfMines) {
            y = generator.nextInt(this.heigthOfGame);
            x = generator.nextInt(this.widthOfGame);

            if (!model[y][x].isMined()) {
                model[y][x].setMined();
                counter++;
            }
        } 
    }

 
    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){

    // ADD YOU CODE HERE

        for (int i = 0; i < heigthOfGame; i++) {
            for (int j = 0; j < widthOfGame; j++) {
                model[i][j] = new DotInfo(j, i);
            }
        }

        int x = 0;
        int y = 0; 

        int counter = 0;

        while (counter < this.numberOfMines) {
            y = generator.nextInt(this.heigthOfGame);
            x = generator.nextInt(this.widthOfGame);

            if (!model[y][x].isMined()) {
                model[y][x].setMined();
                counter++;
            }
        } 

        this.numberOfSteps = 0;
    }


    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        
    // ADD YOU CODE HERE

    	return this.heigthOfGame;
    }


    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        
    // ADD YOU CODE HERE

    	return this.widthOfGame;
    }


    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isMined(int i, int j){
        
    // ADD YOU CODE HERE

    	return model[i][j].isMined();
    }


    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean hasBeenClicked(int i, int j){
        
    // ADD YOU CODE HERE

    	return model[i][j].hasBeenClicked();
    }


    /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighboor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isBlank(int i, int j){
        
    // ADD YOU CODE HERE

    	if ((getNeighbooringMines(i, j) == 0) && !model[i][j].isMined()) { 
    		return true;
    	}

    	else {
    		return false;
    	}
    }


    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCovered(int i, int j){
        
    // ADD YOU CODE HERE

    	return model[i][j].isCovered();
    }


    /**
     * returns the number of neighbooring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */   
    public int getNeighbooringMines(int i, int j){
        
    // ADD YOU CODE HERE

        int counter = 0;
        
        if (((i - 1) >= 0) && ((j - 1) >= 0) && (model[i - 1][j - 1].isMined())) {
            counter++;
        }

        if (((i - 1) >= 0) && (model[i - 1][j].isMined())) {
            counter++;
        }

        if (((i - 1) >= 0) && ((j + 1) < getWidth()) && (model[i - 1][j + 1].isMined())) {
            counter++;
        }

        if (((j - 1) >= 0) && (model[i][j - 1].isMined())) {
            counter++;
        }

        if (((j + 1) < getWidth()) && (model[i][j + 1].isMined())) {
            counter++;
        }

        if (((i + 1) < getHeigth()) && ((j - 1) >= 0) && (model[i + 1][j - 1].isMined())) {
            counter++;
        }

        if (((i + 1) < getHeigth()) && (model[i + 1][j].isMined())) {
            counter++;
        }

        if (((i + 1) < getHeigth()) && ((j + 1) < getWidth()) && (model[i + 1][j + 1].isMined())) {
            counter++;
        }

        model[i][j].setNeighbooringMines(counter);

    	return model[i][j].getNeighbooringMines();
    }


    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void uncover(int i, int j){
        
    // ADD YOU CODE HERE

    	model[i][j].uncover();
        this.numberUncovered++;
    }


    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void click(int i, int j){
        
    // ADD YOU CODE HERE

    	model[i][j].click();
    }


     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){
        
    // ADD YOU CODE HERE

    	for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model[0].length; j++) {
                if (!hasBeenClicked(i, j)) { 
                    click(i, j);
                }
            }
        }
    }


    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        
    // ADD YOU CODE HERE

    	return this.numberOfSteps;
    }
  

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        
    // ADD YOU CODE HERE

        return model[i][j];
    }


   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        
    // ADD YOU CODE HERE

     	this.numberOfSteps++;
    }
 

   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        
    // ADD YOU CODE HERE

        int counter = 0;

        for (int i = 0; i < model.length; i++){
            for (int j = 0; j < model[0].length; j++){
                if (model[i][j].isCovered()) {
                    counter++;
                }
            }
        }
        
        return counter == this.numberOfMines;
    }


   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        
    // ADD YOU CODE HERE

        return "The game being played is " + this.widthOfGame + "X" + this.heigthOfGame + " and has " + this.numberOfMines + " mines. The player has uncovered " + this.numberUncovered + " tiles and has done it in " + this.numberOfSteps + " steps.";
    }
}

