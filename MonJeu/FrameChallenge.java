import java.awt.event.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class FrameChallenge extends JFrame implements ActionListener
{
	private JPanel pnlNiveau;
	private JPanel pnlFleche;
	
	private JLabel lblMonde;
	private JButton[] tabBtn;
	
	private int monde;
	private String fichier;
	private FrameDifficulte frmDif;
	
	public FrameChallenge(FrameDifficulte frmDif)
	{
		this.frmDif = frmDif;
		this.monde = 1;
		this.fichier = "";
		
		this.setTitle   ("Choix Niveau");
		this.setSize    (500, 400);
		this.setLocation( 500, 100 );
		this.setLayout  ( new BorderLayout());
		
		/*------------------------------*/
		/* Création des composants      */
		/*------------------------------*/
		
		this.pnlNiveau = new JPanel();
		this.pnlNiveau.setLayout( new GridLayout(3,5, 5,5));
		
		this.pnlFleche = new JPanel();
		this.pnlFleche.setLayout( new GridLayout(1,2));
		
		this.lblMonde = new JLabel("Monde " + this.monde);
		
		this.tabBtn = new JButton[17];
		for(int cpt=0; cpt < 15; cpt++)
			this.tabBtn[cpt] = new JButton(cpt +"");
		
		Icon icon = new ImageIcon("./image/fl_gauche.gif");
		this.tabBtn[15] = new JButton(icon);
		icon = new ImageIcon("./image/fl_droite.gif");
		this.tabBtn[16] = new JButton(icon);
		
		/*------------------------------*/
		/* Postionnement des composants */
		/*------------------------------*/
		
		for(int cpt=0; cpt < 15; cpt++)
			this.pnlNiveau.add(this.tabBtn[cpt]);
		
		this.pnlFleche.add(this.tabBtn[15]);
		this.pnlFleche.add(this.tabBtn[16]);
		
		this.add(lblMonde,  BorderLayout.NORTH);
		this.add(pnlNiveau, BorderLayout.CENTER);
		this.add(pnlFleche, BorderLayout.SOUTH);
		
		/*------------------------------*/
		/* Activation des composants    */
		/*------------------------------*/
		
		for(int cpt=0; cpt < 17; cpt++)
			this.tabBtn[cpt].addActionListener(this);
		
		this.setVisible(true);
	}
	
	public void actionPerformed ( ActionEvent e)
	{
		if( e.getSource().equals( this.tabBtn[15] ) && this.monde > 1)
			this.monde--;
		if( e.getSource().equals( this.tabBtn[16] ) )
			this.monde++;
		
		for(int cpt=0; cpt<15; cpt++)
        {
            if( e.getSource().equals( this.tabBtn[cpt] ) )
                this.fichier = "challenge_" + this.monde + "_" + (cpt +1);
        }
        
		this.lblMonde.setText("Monde " + this.monde);
        this.frmDif.setChoix(this.fichier);
	}
	
	
}
