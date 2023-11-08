import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;

public class PanelCommande extends JPanel implements ActionListener
{
	private ControleurJeu ctrl;

	private JPanel pnl1;
	private JPanel pnl2;
	
	private JLabel lblrecord;
	private JLabel lblniveau;

	private int record;
	private int niveau;

	private static JButton start;
	private JButton[] direction;


	public PanelCommande( ControleurJeu ctrl )
	{
		this.ctrl = ctrl;
		this.record = 0;
		this.niveau = 0;

		this.setLayout(new GridLayout(1, 2, 10, 10));

		/*------------------------------*/
		/* Création des composants      */
		/*------------------------------*/

		this.pnl1 = new JPanel();
		this.pnl2 = new JPanel();

		this.pnl2.setLayout(new GridLayout(2, 3));
		this.pnl1.setLayout(new GridLayout(2, 2));

		this.lblrecord = new JLabel("Record : 0");
		this.lblniveau = new JLabel("Niveau 0");

		PanelCommande.start = new JButton("Lancer une partie"); //mettre une image ??

		Icon icon;
		String[] fleche = new String[] {"image/fl_haut.gif", "image/fl_gauche.gif",
		                                "image/fl_bas.gif",  "image/fl_droite.gif"};

		this.direction = new JButton[4];
		for(int cpt=0; cpt<4; cpt++)
		{
			icon = new ImageIcon(fleche[cpt]);
			this.direction[cpt] = new JButton(icon);
		}

		/*------------------------------*/
		/* Postionnement des composants */
		/*------------------------------*/

		this.add(this.pnl1);
		this.add(this.pnl2);
		
		this.pnl1.add( this.lblniveau);
		this.pnl1.add( this.lblrecord);

		this.pnl1.add( PanelCommande.start);

		this.pnl2.add(new JLabel());
		this.pnl2.add(this.direction[0]);
		this.pnl2.add(new JLabel());

		for(int cpt=1; cpt<4; cpt++)
		{
			this.pnl2.add(this.direction[cpt]);
		}

		/*------------------------------*/
		/* Activation des composants    */
		/*------------------------------*/

		PanelCommande.start.addActionListener(this);
		
		for(int cpt=0; cpt<4; cpt++)
		{
			this.direction[cpt].addActionListener(this);
		}

		this.activerBtn(false);

	}

	public void actionPerformed ( ActionEvent e)
	{
		if( e.getSource().equals(PanelCommande.start) )
		{
			if( PanelCommande.start.getText().equals("Lancer une partie"))
			{
				this.niveau = 0;
				this.lblniveau.setText("Niveau " + this.niveau);

				this.activerBtn(false);

				this.ctrl.lancerPartie();
			}

			if( PanelCommande.start.getText().equals("Niveau suivant"))
			{
				this.niveau++;
				this.lblniveau.setText("Niveau " + this.niveau);

				if (this.record < this.niveau) {this.record = this.niveau;}
				this.lblrecord.setText( "Record : " + this.record);

				this.activerBtn(false);

				this.ctrl.lancerPartie();
			}

			if( PanelCommande.start.getText().equals("Terminer"))
			{
				this.activerBtn(true);
				
				this.ctrl.deplacementHero();
			}

			if( PanelCommande.start.getText().equals("Visualiser"))
			{
				this.ctrl.modeBlanc(false);
				this.ctrl.getIhm().majIHM();

				//mettre un timer ?
				/*try{
					Thread.sleep (10000);
				}catch (InterruptedException ignore) {
				}*/

				this.ctrl.modeBlanc(true);
				//this.ctrl.getIhm().majIHM();
				
				this.ctrl.deplacementPlaque();
			}
		}		

		if( e.getSource().equals(this.direction[0]))
			this.ctrl.deplacerHero('N');
		if( e.getSource().equals(this.direction[1]))
			this.ctrl.deplacerHero('O');
		if( e.getSource().equals(this.direction[2]))
			this.ctrl.deplacerHero('S');
		if( e.getSource().equals(this.direction[3]))
			this.ctrl.deplacerHero('E');
	}

	public static void modifierText( String text)
	{
		PanelCommande.start.setText( text );
	}

	public void activerBtn( boolean bool)
	{
		for( int cpt=0; cpt < this.direction.length; cpt++)
			this.direction[cpt].setEnabled(bool);
	}

	public int getNiveau()
	{
		return this.niveau;
	}
}
