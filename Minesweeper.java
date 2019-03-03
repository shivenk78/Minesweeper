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
    private ImageIcon mine, flag;
    private ImageIcon[] numIcons;

    private int dimensionRow = 9;
    private int dimensionCol = 9;
    private int grid[][];
    private int difficulty;

    private int[] mineCounts = {10, 40, 99};

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
        panel = new JPanel();

        smilePanel = new JPanel();
        smilePanel.setLayout(new GridLayout(1, 3));
        timer = new JLabel("000");
        smiley = new JButton();
        mineCount = new JLabel(""+mineCounts[difficulty]);
        smilePanel.add(mineCount);
        smilePanel.add(smiley);

        createMap();
        frame.add(smilePanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void click(int row, int col){
        int val = grid[row][col];
        if(val == 0){
            
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
        if (e.getButton() == MouseEvent.BUTTON3) { // Right Click
            for (JToggleButton[] tRow : togglers) {
                for (JToggleButton button : tRow) {
                    if (e.getSource() == button) {
                        if (!button.isSelected()) {
                            button.setIcon(flag);
                        } else {
                            button.setIcon(null);
                        }
                    }
                }
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1) { // Left Click
            for (JToggleButton[] tRow : togglers) {
                for (JToggleButton button : tRow) {
                    if (e.getSource() == button) {
                        if (!(button.getIcon()==flag)) {
                            button.setSelected(true);
                            button.setIcon(numIcons[0]);
                            button.setEnabled(false);
                        } else {
                            button.setSelected(false);
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
        panel.removeAll();
        panel.updateUI();
        panel.setLayout(new GridLayout(dimensionRow, dimensionCol));
        for (int r = 0; r < togglers.length; r++) {
            for (int c = 0; c < togglers[r].length; c++) {
                togglers[r][c] = new JToggleButton();
                togglers[r][c].addMouseListener(this);
                panel.add(togglers[r][c]);
            }
        }
        frame.revalidate();
        resizeImages();
    }

    public void resizeImages(){
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

    public static void main(String[] args) {
        Minesweeper mS = new Minesweeper();
    }
}