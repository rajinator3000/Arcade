/**
 * The implementation of the game Tetris.
 * @author Logan Bryant and Raj Patel
 */

package cs1302.arcade;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;

public class Tetris extends JPanel {
    int pRow;
    int pCol;
    Point origPiece;
    int round = 0;
    int pieceRow = 0;
    int pieceColumn = 0;
    int orientation = 0;
    Point [][] temp = null;
    int randomVal;
    Color pieceColor;
    Color [][] board = new Color[20][10];
    private int score = 0;
    private boolean gameStatus = true;
    int brickDropCount = 0;
    private int difficulty = 1000;
    int level = 1;

    //Different Rotation of each piece. Draw on grid if confused
    Point [] iRotation = {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)};
    Point [] iRotation2 = {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)};
    Point [] iRotation3 = {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)};
    Point [] iRotation4 = {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)};

    //Adding Different rotations to a 2d Array
    Point[][] i =  {
            iRotation,iRotation2,iRotation3,iRotation4
    };

    Point [] jRotation =  {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)};
    Point [] jRotation2 = {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)};
    Point [] jRotation3 = {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2)};
    Point [] jRotation4 = {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0)};

    Point[][] j = {
            jRotation,jRotation2,jRotation3,jRotation4
    };

    Point[] lRotation =  {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)};
    Point[] lRotation2 = {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2)};
    Point[] lRotation3 = {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0)};
    Point[] lRotation4 = {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0)};

    Point[][] l = {
            lRotation,lRotation2,lRotation3,lRotation4
    };

    Point[] oRotation = {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)};
    Point[] oRotation2 = {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)};
    Point[] oRotation3 = {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)};
    Point[] oRotation4 = {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)};
    Point[][] o = {
            oRotation,oRotation2,oRotation3,oRotation4
    };
    Point[] sRotation= {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)};
    Point[] sRotation2={new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)};
    Point[] sRotation3={new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)};
    Point[] sRotation4={new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)};
    Point[][] s = {
            sRotation, sRotation2, sRotation3, sRotation4
    };

    Point[] tRotation = {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)};
    Point[] tRotation2=	{new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)};
    Point[] tRotation3=	{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)};
    Point[] tRotation4=	{new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)};
    Point[][] t = {
            tRotation,tRotation2,tRotation3,tRotation4
    };

    Point[] zRotation = {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)};
    Point[] zRotation2 = {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)};
    Point[] zRotation3 = {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)};
    Point[] zRotation4 = {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)};
    Point[][] z = {
            zRotation,zRotation2,zRotation3,zRotation4
    };


    //Adding 2d Arrays of pieces to 3d array
    Point[][][] tetrominos = {
            i,j,l,o,s,t,z
    };
    //Different colors of pieces in order of above
    Color[] colors = {
            Color.CYAN, Color.BLUE, Color.ORANGE, Color.YELLOW,
            Color.GREEN, Color.MAGENTA, Color.RED
    };

    public void setLevel(int i) {
        level = i;
    }

    /**
     * Returns a value from the array of colors
     * @param i the integer value that will be used to get the index of the color
     * @return  the color value from the color array
     */
    public Color getColor(int i) {
        return colors[i];
    }

    /**
     * Tells if the game is actually over or not
     * @return gameStatus a boolean that tells if the game is over or not
     */
    public boolean getGameOver() {
        return gameStatus;
    }

    /**
     * Checks to see if the top row of the blocks is full.
     * if the top row is not white, then a block cannot be
     * added and the game will be over.
     */
    public void gameOver() {
        for(int i =0; i< 2; i++) {
            for(int j = 5; j<9; j++) {
                if(board[j][i] != Color.WHITE) {
                    gameStatus = false;
                }
            }
        }
    }

    /**
     * Gets how many bricks are dropped to tell what level the game is on
     * @return brickDropCount an int that represents how many bricks have fallen
     */
    public int getBrickDropCount() {
        return brickDropCount;
    }

    //Initializes instance variables to values of random piece

    /**
     * Randoms which, out of the 7, piece will be the one that will appear
     * on the board and drop. The piece will get its color value, and
     * the index at which on the grid it should drop.
     */
    public void startNewPiece() {
        brickDropCount++;
        Random r = new Random();
        randomVal = r.nextInt(7);
        pCol = 5;
        pRow = 0;
        pieceColor = getColor(randomVal);
        //origPiece is where piece begins
        origPiece = new Point(5,0);
        switch(randomVal) {
            case 0:
                temp = i;
                pieceRow=temp.length;
                pieceColumn = 4;
                break;
            case 1:
                temp = j;
                pieceRow=temp.length;
                pieceColumn = 3;
                break;
            case 2:
                temp = l;
                pieceRow=temp.length;
                pieceColumn = 3;
                break;
            case 3:
                temp = o;
                pieceRow=temp.length;
                pieceColumn = 2;
                break;
            case 4:
                temp = s;
                pieceRow=temp.length;
                pieceColumn = 3;
                break;
            case 5:
                temp = t;
                pieceRow=temp.length;
                pieceColumn = 3;
                break;
            case 6:
                temp = z;
                pieceRow=temp.length;
                pieceColumn = 3;
                break;
        }

    }

    /**
     * Sets the difficulty for the game, as the
     * game continues and more pieces are dropped, the
     * game becomes harder.
     * @return difficulty the int that tells how fast to drop pieces
     */
    public int setDifficulty() {
        if(brickDropCount ==12){
            brickDropCount =0;
            difficulty -=100;
            level++;
        }
        return difficulty;
    }

    /**
     * Creates the board that the user plays on
     * with the correct colors and boundaries.
     */
    public void init() {

        board = new Color[12][21];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 21; j++) {
                if (i != 0 && i != 11 && j != 20) {
                    board[i][j] = Color.WHITE;
                } else {
                    board[i][j] = Color.LIGHT_GRAY;
                }
            }
        }
        startNewPiece();
    }

    /**
     * Key listeners that respond to user
     * @return the key that the user has entered
     */
    public KeyListener getKey() {
        return k;
    }

    KeyListener k = new KeyListener() {
        public void keyTyped(KeyEvent e) {
        }
        public void keyReleased(KeyEvent e) {
        }
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                //If next grid to the left is not white, does not move
                int pointCount = 0;
                for(Point p: tetrominos[randomVal][orientation]) {
                    if(board[(origPiece.x-1) + p.x][p.y] != Color.WHITE) {
                        pointCount++;
                    }
                }
                if(pointCount == 0)
                    origPiece.x -=1;
                repaint();
            }

            if (key == KeyEvent.VK_RIGHT) {
                //If next grid to the right is not white, does not move
                int pointCount = 0;
                for(Point p: tetrominos[randomVal][orientation]) {
                    if(board[p.x+origPiece.x+1][p.y] != Color.WHITE) {
                        pointCount++;
                    }
                }
                if(pointCount == 0)
                    origPiece.x +=1;
                repaint();
            }
            if (key == KeyEvent.VK_DOWN) {
                if(!willTouchBottom()) {
                    score+=1;
                    origPiece.y+=1;
                }
                repaint();
            }

            if (key == KeyEvent.VK_UP) {
                try {
                    if(orientation == 0) {
                        int blockCount = 0;
                        for(Point p: tetrominos[randomVal][1]) {
                            if(board[p.x+origPiece.x+1][p.y] != Color.WHITE || board[p.x+origPiece.x-1][p.y] != Color.WHITE) {
                                blockCount++;
                            }
                        }
                        if(blockCount==0)
                            orientation =1;
                    } else if(orientation ==1) {
                        int blockCount = 0;
                        for(Point p: tetrominos[randomVal][2]) {
                            if(board[p.x+origPiece.x+1][p.y] != Color.WHITE || board[p.x+origPiece.x-1][p.y] != Color.WHITE) {
                                blockCount++;
                            }
                        }
                        if(blockCount==0)
                            orientation =2;

                    } else if(orientation ==2) {
                        int blockCount = 0;
                        for(Point p: tetrominos[randomVal][3]) {
                            if(board[p.x+origPiece.x+1][p.y] != Color.WHITE || board[p.x+origPiece.x-1][p.y] != Color.WHITE) {
                                blockCount++;
                            }
                        }
                        if(blockCount==0)
                            orientation =3;

                    } else if(orientation ==3) {
                        int blockCount = 0;
                        for(Point p: tetrominos[randomVal][0]) {
                            if(board[p.x+origPiece.x+1][p.y] != Color.WHITE || board[p.x+origPiece.x-1][p.y] != Color.WHITE) {
                                blockCount++;
                            }
                        }
                        if(blockCount==0)
                            orientation =0;
                    }

                    repaint();
                } catch(Exception e3) {}
            }
        }
    };

    /**
     * Used to shift all of the rows above the completed
     * row down so that there is no blank row.
     * @param row the integer of the row that needs to be moved           ?
     */
    public void moveRow(int row) {
        for(int k = row; k > 0; k--) {
            for(int j = 1; j <11; j++) {
                board[j][k]=board[j][k-1];
            }
        }
    }

    /**
     * Checks to see if all the blocks in the row
     * are colored, which means the row is completed and can be removed.
     * If there are white spaces, the row is not completed.
     */
    public void completedRow() {
        int rowsCompleted = 0;
        int times = 20;
        while(times != 0) {

            for(int i = 19; i>0;i--) {


                boolean isComplete = true;
                for(int j = 10; j > 0; j--) {
                    if(board[j][i] == Color.WHITE) {
                        isComplete=false;

                    }
                }

                if(isComplete) {
                    rowsCompleted++;

                    int row= i;
                    for (int j = 1; j < 11; j++) {
                        board[j][i]=Color.WHITE;
                    }
                    moveRow(i);
                }

            }
            times--;
        }

      /*
       The following is used for scoring. The more lines the user
       clears, the higher score they will gain.
      */
        if(rowsCompleted ==1) {
            score += 40;
        } else if(rowsCompleted ==2) {
            score += 100;
        } else if(rowsCompleted ==3) {
            score += 300;
        }
        else if(rowsCompleted ==4) {
            score += 1200;
        }
    }

    /**
     * Returns what the final score that the user gets is
     * @return score, int that is the score value
     */
    public int getScore() {
        return score;
    }

    /**
     * Makes the piece a part of the board. So, if a piece stops moving,
     * it is drawn to make it a part of the board so that it is immovable/ solid.
     * @param pic3 graphics object that has the piece that is to be drawn on the board
     */
    public void draw(Graphics pic3) {
        pic3.setColor(pieceColor);
        for (Point p : tetrominos[randomVal][orientation]) {
            pic3.fillRect((p.x + origPiece.x) * 26,
                    (p.y + origPiece.y) * 26,
                    25, 25);
            if(willTouchBottom()) {
                for (Point p2 : tetrominos[randomVal][orientation]) {
                    board[p2.x+origPiece.x][p2.y+origPiece.y] = pieceColor;
                }
                completedRow();

                repaint();
                gameOver();
                if(gameStatus) {
                    startNewPiece();
                }
            }

        }

    }


    /**
     * Paints the actual board with the white background
     * and the black borders. Also adds the side panel that
     * has the scoring and score for the game.
     * @param pic graphics object that is used to make the board and boundaries
     */
    public void paint(Graphics pic)
    {
        super.paint(pic);
        pic.fillRect(0, 0, 327, 590);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 21; j++) {
                pic.setColor(board[i][j]);
                pic.fillRect(26*i, 26*j, 25, 25);
            }
        }

        //Strings that help the user and print out the score

        pic.drawString("Welcome to Tetris.", 19*18, 15);
        pic.drawString("Here is how the scoring works.", 19*18, 55);
        pic.drawString("One line cleared: 40", 19*18, 85);
        pic.drawString("Two lines cleared: 100", 19*18, 115);
        pic.drawString("Three line cleared: 300", 19*18, 145);
        pic.drawString("Four lines cleared: 1200", 19*18, 175);
        pic.drawString("Level: " + level, 19*18, 305);
        pic.drawString("Here is your SCORE: " + score, 19*18, 505);
        pic.setColor(Color.WHITE);
        draw(pic);
    }

    /**
     * Makes the piece fall by one row, so that the
     * user has a set amount of time before the piece stops
     */
    public void dropByOne() {
        if(!willTouchBottom()) {
            origPiece.y+=1;
        }
        repaint();
    }

    /**
     * If the next area under the piece is not white, then the
     * piece will stop moving and stay in place.
     * @return boolean if the piece is able to stop or not
     */
    public boolean willTouchBottom() {
        int pointCount = 0;
        for(Point p: tetrominos[randomVal][orientation]) {
            if(board[p.x+origPiece.x][p.y+1+origPiece.y] != Color.WHITE) {
                pointCount++;
            }
        }

        if(pointCount == 0)
            return false;
        else
            return true;
    }
}