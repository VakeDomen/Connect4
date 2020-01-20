package gfx;

import com.sun.tools.javac.Main;
import game.Game;
import players.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideMenu extends JPanel {

    private JRadioButton buttonPl1h, buttonPl1Rand, buttonPl1MM, buttonPl1AB;
    private JRadioButton buttonPl2h, buttonPl2Rand, buttonPl2MM, buttonPl2AB;

    ButtonGroup player1, player2;

    private JLabel activePlayer, turn;

    private JButton restart;

    private Game game;

    public SideMenu(Game game) {
        //super(new FlowLayout());
        this.game = game;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        buttonPl1h = new JRadioButton("Human");
        buttonPl1h.setActionCommand("human");
        buttonPl1Rand = new JRadioButton("Random AI");
        buttonPl1Rand.setActionCommand("random");
        buttonPl1MM = new JRadioButton("MinMax AI");
        buttonPl1MM.setActionCommand("minmax");
        buttonPl1AB = new JRadioButton("Alpha-Beta AI");
        buttonPl1AB.setActionCommand("alphabeta");
        buttonPl2h = new JRadioButton("Human");
        buttonPl2h.setActionCommand("human");
        buttonPl2Rand = new JRadioButton("Random AI");
        buttonPl2Rand.setActionCommand("random");
        buttonPl2MM = new JRadioButton("MinMax AI");
        buttonPl2MM.setActionCommand("minmax");
        buttonPl2AB = new JRadioButton("Aplha-Beta");
        buttonPl2AB.setActionCommand("alphabeta");

        restart = new JButton("Restart");

        player1 = new ButtonGroup();
        player1.add(buttonPl1h);
        player1.add(buttonPl1Rand);
        player1.add(buttonPl1MM);
        player1.add(buttonPl1AB);

        player2 = new ButtonGroup();
        player2.add(buttonPl2h);
        player2.add(buttonPl2Rand);
        player2.add(buttonPl2MM);
        player2.add(buttonPl2AB);

        JLabel pl1Label = new JLabel("Choose player 1 (Red):");
        JLabel pl2Label = new JLabel("Choose player 2 (Yellow):");
        activePlayer = new JLabel("Choose players");
        turn = new JLabel("Turn: ---" );

        this.add(activePlayer);
        this.add(turn);

        this.add(new JSeparator());


        this.add(pl1Label);
        this.add(buttonPl1h);
        this.add(buttonPl1Rand);
        this.add(buttonPl1MM);
        this.add(buttonPl1AB);

        this.add(new Label(""));

        this.add(pl2Label);
        this.add(buttonPl2h);
        this.add(buttonPl2Rand);
        this.add(buttonPl2MM);
        this.add(buttonPl2AB);

        this.add(new Label(""));

        this.add(restart);

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player pla1 = mapToPlayer(1, player1.getSelection().getActionCommand());
                Player pla2 = mapToPlayer(2, player1.getSelection().getActionCommand());
                System.out.println("hey");
                game.restart(pla1, pla2);

            }
        });

    }

    private Player mapToPlayer(int player, String command) {
        if (command == "human")
            return new HumanPlayer();
        if (command == "random")
            return new RandomAI(player);
        if (command == "minmax")
            return new MiniMaxAI(player, 5);
        if (command == "alphabeta")
            return new AlphaBetaAI(player, 7);
        return null;
    }


    public void refresh() {
        this.activePlayer.setText(playerToColor(this.game.getWinner()));
        this.turn.setText("Turn: " + this.game.getTurn());
        this.activePlayer.paintImmediately(this.activePlayer.getVisibleRect());
        this.turn.paintImmediately(this.turn.getVisibleRect());
    }

    private  String playerToColor(int pl) {
        if (pl == 1)
            return "Red";
        else
            return "Yellow";
    }

}
