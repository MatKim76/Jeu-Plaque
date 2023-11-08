import java.awt.event.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class FrameDifficulte extends JFrame implements ActionListener
{
    private JPanel[]  tabPnl;
    private JButton[] tabBtn;
    private JLabel[]  tabLab;

    private int nb;
    private String type;

    public FrameDifficulte()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setTitle   ("Choix Difficulté");
        this.setSize    (500, 600);
        this.setLocation( 500, 100 );
        this.setLayout  ( new GridLayout(4, 1, 1, 25));
        
        this.nb = 0;
        this.type = " ";

        /*--------------------------------------------*/
		/* Création et activation des composants      */
		/*--------------------------------------------*/
        
        this.tabPnl = new JPanel[4];
        for(int cpt=0; cpt<4; cpt++)
		{
			this.tabPnl[cpt] = new JPanel();
            this.tabPnl[cpt].setLayout( new BorderLayout(0,5) );
		}
        

        String[] textBtn = new String[] {"Comment Jouer", "Mode Progressif", "Challenges", "Facile", "Normal", "Difficile", "Enfer"};
        
        this.tabBtn = new JButton[7];
		for(int cpt=0; cpt<7; cpt++)
		{
			this.tabBtn[cpt] = new JButton( textBtn[cpt] );
            this.tabBtn[cpt].addActionListener(this);
		}

        String[] textLbl = new String[] {"Guide d'Aprentissage pour Débuter", "Niveaux de plus en plus dur selon votre Score",
                                         "Défis créés de toute Pièce", "Niveaux de Difficulté Constante"};
        this.tabLab = new JLabel [4];
        for(int cpt=0; cpt<4; cpt++)
		{
			this.tabLab[cpt] = new JLabel( textLbl[cpt] );
            this.tabLab[cpt].setOpaque(false);
		}

        JPanel panelTmp = new JPanel();
        panelTmp.setLayout( new GridLayout(1, 4) );

        /*this.tabBtn[3].setBackground(new Color(6  ,190,0  ));
        this.tabBtn[4].setBackground(new Color(255,137,36 ));
        this.tabBtn[5].setBackground(new Color(255,36 ,36 ));*/
        this.tabBtn[6].setBackground(new Color(175,56 ,255));

        this.tabBtn[2].setBackground(Color.BLUE);
        this.tabBtn[1].setBackground(Color.CYAN);
        this.tabBtn[0].setBackground(Color.GREEN);

        this.tabBtn[3].setBackground(Color.YELLOW);
        this.tabBtn[4].setBackground(Color.ORANGE);
        this.tabBtn[5].setBackground(Color.RED);

        /* 
        this.tabLab[0].setBackground(new Color(126,255,120));
        this.tabLab[1].setBackground(new Color(255,227,126));
        this.tabLab[2].setBackground(new Color(255,126,126));
        this.tabLab[3].setBackground(new Color(255,146,255));
        */


        /*------------------------------*/
		/* Postionnement des composants */
		/*------------------------------*/

        for(int cpt=3; cpt<7; cpt++)
			panelTmp.add(tabBtn[cpt]);
        
        for(int cpt=0; cpt<4; cpt++)
        {
            this.tabPnl[cpt].add( this.tabLab[cpt], BorderLayout.NORTH );
            if( cpt == 3) { this.tabPnl[cpt].add( panelTmp,         BorderLayout.CENTER ); }
            else          { this.tabPnl[cpt].add( this.tabBtn[cpt], BorderLayout.CENTER ); }
        }

        for(int cpt=0; cpt<4; cpt++)
			this.add( this.tabPnl[cpt] );
		

        this.setVisible ( true );
    }

    public void actionPerformed ( ActionEvent e)
    {
        int test = 0;
        for(int cpt=0; cpt<7; cpt++)
        {
            if( e.getSource().equals( this.tabBtn[cpt] ) )
                test = cpt;
        }

        switch (test)
        {
            case 0 : this.type = "tutoriel";
            break;
            case 1 : this.type = "progressif";
            break;
            case 2 : new FrameChallenge(this);
            break;
            case 3 : this.type = "constant";
                     this.nb = 1;
            break;
            case 4 : this.type = "constant";
                     this.nb = 2;
            break;
            case 5 : this.type = "constant";
                     this.nb = 3;
            break;
            case 6 : this.type = "constant";
                     this.nb = 4;
            break;
        }

    }

    public String getChoix()
    {
        return this.type;
    }

    public int getDifficulte()
    {
        return this.nb;    
    }

    public void reset()
    {
        this.nb = 0;
        this.type = " ";
    }
    
    public void setChoix(String choix)
    {
    	this.type = choix;
    }
}
