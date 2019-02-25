import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends JPanel implements ActionListener, MouseListener {

    private JFrame frame;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem[] difficulties;
    private JPanel panel;
    private JToggleButton[][] togglers;
    private ImageIcon mine;

    private int dimensionRow = 9;
    private int dimensionCol = 9;

    public Minesweeper() {
        frame = new JFrame("Minesweeper");
        frame.add(this);
        frame.setSize(1000, 800);
        mine = new ImageIcon("mine.png");
        mine = new ImageIcon(mine.getImage().getScaledInstance(frame.getWidth() / dimensionCol,
                frame.getHeight() / dimensionRow, Image.SCALE_SMOOTH));

        menuBar = new JMenuBar();
        menu = new JMenu("Game");
        difficulties = new JMenuItem[3];
        difficulties[0] = new JMenuItem("Beginner");
        difficulties[0].setToolTipText("9x9, 10 Mines");
        difficulties[0].addActionListener(this);
        difficulties[1] = new JMenuItem("Intermediate");
        difficulties[1].setToolTipText("16x16, 40 Mines");
        difficulties[1].addActionListener(this);
        difficulties[2] = new JMenuItem("Expert");
        difficulties[2].setToolTipText("16x30, 99 Mines");
        difficulties[2].addActionListener(this);

        for (JMenuItem item : difficulties)
            menu.add(item);

        menuBar.add(menu);
        frame.add(menuBar, BorderLayout.NORTH);

        togglers = new JToggleButton[dimensionRow][dimensionCol];
        panel = new JPanel();
        panel.setLayout(new GridLayout(dimensionRow, dimensionCol));
        for (int r = 0; r < togglers.length; r++) {
            for (int c = 0; c < togglers[r].length; c++) {
                togglers[r][c] = new JToggleButton();
                togglers[r][c].addMouseListener(this);
                togglers[r][c].setBackground(Color.BLACK);
                panel.add(togglers[r][c]);
            }
        }
        frame.add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) { // Right Click
            for (JToggleButton[] tRow : togglers) {
                for (JToggleButton button : tRow) {
                    if (e.getSource() == button) {
                        if (!button.isSelected()) {
                            button.setSelected(true);
                            button.setIcon(mine);
                        }
                    } else {
                        button.setSelected(false);
                        button.setIcon(null);
                    }
                }
            }
        }
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        Minesweeper mS = new Minesweeper();
    }
}