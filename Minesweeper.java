import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends JPanel implements ActionListener, MouseListener {

    private JFrame frame;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem[] difficulties;
    private JPanel panel, smilePanel;
    private JToggleButton[][] togglers;
    private JLabel timer, mineCount;
    private JButton smiley;
    private ImageIcon empty, mine, flag;
    private ImageIcon[] numIcons;

    private int dimensionRow = 9;
    private int dimensionCol = 9;
    private int grid[][];
    private boolean clicked[][];
    private boolean firstClick;
    private boolean gameOver;
    private int difficulty;

    private int[] mineCounts = {10, 40, 99};

    //TODO: allow clicking of flags, prevent reverting with right click, win conditions, timer, counter 

    public Minesweeper() {
        frame = new JFrame("Minesweeper");
        frame.add(this);
        frame.setSize(1000, 800);
        resizeImages();

        menuBar = new JMenuBar();
        menu = new JMenu("Game");
        difficulties = new JMenuItem[3];
        difficulties[0] = new JMenuItem("Beginner");
        difficulties[0].setToolTipText("9x9, 10 Mines");
        difficulties[1] = new JMenuItem("Intermediate");
        difficulties[1].setToolTipText("16x16, 40 Mines");
        difficulties[2] = new JMenuItem("Expert");
        difficulties[2].setToolTipText("16x30, 99 Mines");

        for (JMenuItem item : difficulties){
            item.addActionListener(this);
            menu.add(item);
        }

        menuBar.add(menu);
        frame.add(menuBar, BorderLayout.NORTH);

        togglers = new JToggleButton[dimensionRow][dimensionCol];
        grid = new int[dimensionRow][dimensionCol];
        clicked = new boolean[dimensionRow][dimensionCol];
        panel = new JPanel();

        smilePanel = new JPanel();
        smilePanel.setLayout(new GridLayout(1, 3));
        timer = new JLabel("000");
        smiley = new JButton();
        mineCount = new JLabel(String.format("%03d",mineCounts[difficulty]));
        smilePanel.add(mineCount);
        smilePanel.add(smiley);
        smilePanel.add(timer);
        menu.add(smilePanel);
        

        createMap();
        frame.add(smilePanel, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void randomizeBoard(int firstClickRow, int firstClickCol){
        firstClick = true;

        for(int i=0; i<mineCounts[difficulty]; i++){
            int randRow, randCol;
            do{
                randRow = randBetween(0, grid.length);
                randCol = randBetween(0, grid[0].length);
            }while(grid[randRow][randCol] != 0 && (randRow !=firstClickRow && randCol!=firstClickCol));
            grid[randRow][randCol] = -1;
        }

        for(int row = 0; row<grid.length; row++){
            for(int col = 0; col<grid[row].length; col++){
                if(grid[row][col] >= 0){
                    int startR = (row > 0) ? row-1 : row;
                    int endR = (row < grid.length-1) ? row+1 : row;
                    int startC = (col > 0) ? col-1 : col;
                    int endC = (col < grid[row].length-1) ? col+1 : col;
                    int mines = 0;

                    for(int r = startR; r<=endR; r++){
                        for(int c = startC; c<=endC; c++){
                            if(grid[r][c] == -1)
                                mines++;
                        }
                    }
                    grid[row][col] = mines;
                }
            }
        }
        printGrid(grid);
    }

    public void clickExpand(int row, int col){
        if(!firstClick)
            randomizeBoard(row, col);
        int val = grid[row][col];
        clicked[row][col] = true;
        if(val >= 0){
            int startR = (row > 0) ? row-1 : row;
            int endR = (row < grid.length-1) ? row+1 : row;
            int startC = (col > 0) ? col-1 : col;
            int endC = (col < grid[row].length-1) ? col+1 : col;

            for(int r = startR; r<=endR; r++){
                for(int c = startC; c<=endC; c++){
                    if(grid[r][c] >= 0){
                        if(grid[r][c] == 0 && !clicked[r][c]) 
                            clickExpand(r,c);
                        clickSpace(r, c);
                    }
                }
            }
        }
    }

    public void clickSpace(int r, int c){
        if(!firstClick)
            randomizeBoard(r, c);
        if (!(togglers[r][c].getIcon()==flag)) {
            togglers[r][c].setSelected(true);
            togglers[r][c].setIcon(numIcons[grid[r][c]]); 
            togglers[r][c].removeActionListener(this);
        }
    }

    public void exposeMines(){
        gameOver = true;
        for(int r=0; r<grid.length; r++){
            for(int c=0; c<grid[r].length; c++){
                if(grid[r][c] == -1){
                    togglers[r][c].setSelected(true);
                    togglers[r][c].setIcon(mine); 
                }
                togglers[r][c].removeActionListener(this);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
    
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        if(!gameOver){
            if (e.getButton() == MouseEvent.BUTTON3) { // Right Click
                for (JToggleButton[] tRow : togglers) {
                    for (JToggleButton button : tRow) {
                        if (e.getSource() == button) {
                            if (!button.isSelected()) {
                                button.setIcon(flag);
                            } else {
                                button.setIcon(empty);
                            }
                        }
                    }
                }
            }
            if (e.getButton() == MouseEvent.BUTTON1) { // Left Click
                for (int r=0; r<togglers.length; r++) {
                    for (int c=0; c<togglers[r].length; c++) {
                        if (e.getSource() == togglers[r][c] && (!clicked[r][c] && togglers[r][c].getIcon() == empty)) {
                            if(grid[r][c] == 0){
                                clickExpand(r, c);
                            }else if(grid[r][c]>0){
                                clickSpace(r,c);
                            }else{
                                exposeMines();
                            }

                        }
                    }
                }
            }
        }
        frame.revalidate();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == difficulties[0]){
            difficulty = 0;
            frame.setSize(1000,800);
            dimensionRow = 9;
            dimensionCol = 9;
            createMap();
        }
        if(e.getSource() == difficulties[1]){
            difficulty = 1;
            frame.setSize(1000,800);
            dimensionRow = 16;
            dimensionCol = 16;
            createMap();
        }
        if(e.getSource() == difficulties[2]){
            difficulty = 2;
            frame.setSize(1875,800);
            dimensionRow = 16;
            dimensionCol = 30;
            createMap();
        }
    }

    public void createMap(){
        togglers = new JToggleButton[dimensionRow][dimensionCol];
        grid = new int[dimensionRow][dimensionCol];
        clicked = new boolean[dimensionRow][dimensionCol];
        firstClick = false;
        gameOver = false;
        frame.remove(panel);
        panel=new JPanel();
        resizeImages();
        panel.setLayout(new GridLayout(dimensionRow, dimensionCol));
        for (int r = 0; r < togglers.length; r++) {
            for (int c = 0; c < togglers[r].length; c++) {
                togglers[r][c] = new JToggleButton();
                togglers[r][c].addMouseListener(this);
                togglers[r][c].setIcon(empty);
                panel.add(togglers[r][c]);
            }
        }
        frame.add(panel,BorderLayout.CENTER);
        frame.revalidate();
        
    }

    public void resizeImages(){
        empty = new ImageIcon("empty.png");
        empty = new ImageIcon(empty.getImage().getScaledInstance(frame.getWidth() / dimensionCol,
                frame.getHeight() / dimensionRow, Image.SCALE_SMOOTH));
        mine = new ImageIcon("mine.png");
        mine = new ImageIcon(mine.getImage().getScaledInstance(frame.getWidth() / dimensionCol,
                frame.getHeight() / dimensionRow, Image.SCALE_SMOOTH));
        flag = new ImageIcon("flag.png");
        flag = new ImageIcon(flag.getImage().getScaledInstance(frame.getWidth() / dimensionCol,
                frame.getHeight() / dimensionRow, Image.SCALE_SMOOTH));
        numIcons = new ImageIcon[9];
        for(int i=0; i<9; i++){
            numIcons[i] = new ImageIcon(i+".png");
            numIcons[i] = new ImageIcon(numIcons[i].getImage().getScaledInstance(frame.getWidth() / dimensionCol,
                frame.getHeight() / dimensionRow, Image.SCALE_SMOOTH));
        }
    }

    //General Utilities
    public int randBetween(int a, int b){
        return (int)(Math.random()*(b-a)) + a;
    }

    public void printGrid(int[][] gr){
        for(int[] row : gr){
            for(int i : row)
                System.out.print(i+" ");
            System.out.println();
        }     
    }

    public static void main(String[] args) {
        Minesweeper mS = new Minesweeper();
    }
}