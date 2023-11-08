import java.awt.GridLayout;
import javax.swing.*;


import java.awt.event.*;

public class PanelPlateau extends JPanel implements ActionListener
{
	private ControleurJeu ctrl;

	private JLabel[][] tabLblCase;
	private JButton[]  tabButton;


	public PanelPlateau(ControleurJeu ctrl)
	{
		String[][] modele;

		int cas;
		int cptBtn=0;
		Icon icon;

		this.ctrl = ctrl;
		this.setLayout ( new GridLayout ( this.ctrl.getNbLigne()+2, this.ctrl.getNbColonne()+2, 1, 1 ) );

		modele = this.getModele();


		/*------------------------------*/
		/* Création des composants      */
		/*------------------------------*/

		// Création des Labels
		this.tabLblCase  = new JLabel [ this.ctrl.getNbLigne() ] [ this.ctrl.getNbColonne() ];

		for (int lig=0;lig<tabLblCase.length; lig++ )
			for (int col=0;col<tabLblCase[lig].length; col++ )
			{
				icon = new ImageIcon( this.ctrl.getImage(lig, col));
				this.tabLblCase[lig][col] = new JLabel(icon);
				//this.tabLblCase[lig][col].setOpaque(true) ;
			}


		// Création des Boutons
		this.tabButton = new JButton[ 2*this.ctrl.getNbLigne() + 2*this.ctrl.getNbColonne() ];

		cptBtn = 0;

		for (int lig=0;lig<modele.length; lig++ )
		{
			for (int col=0;col<modele[lig].length; col++ )
			{
				if ( modele[lig][col] != null && modele[lig][col].startsWith ("fl_" ) )
				{
					
					if (col == modele[lig].length - 1)
					{
						icon = new ImageIcon("./image/fl_droite.gif");
						this.tabButton[cptBtn++] = new JButton(icon);
					}
					if (lig == 0)
					{
						icon = new ImageIcon("./image/fl_haut.gif");
						this.tabButton[cptBtn++] = new JButton(icon);
					}
					if (col == 0)
					{
						icon = new ImageIcon("./image/fl_gauche.gif");
						this.tabButton[cptBtn++] = new JButton(icon);
					}
					if (lig == modele.length - 1)
					{
						icon = new ImageIcon("./image/fl_bas.gif");
						this.tabButton[cptBtn++] = new JButton(icon);
					}
				}
			}
		}


		/*------------------------------*/
		/* Postionnement des composants */
		/*------------------------------*/
		cptBtn = 0;

		icon = new ImageIcon("./image/vide.gif");

		for (int lig=0; lig<modele.length; lig++ )
		{
			for (int col=0; col<modele[lig].length; col++ )
			{
					 if ( modele[lig][col] == null)                     cas=0;
				else if ( modele[lig][col].startsWith ("fl_" )) cas=1;
				else                                                    cas=2;

				switch ( cas )
				{
					case 0 -> this.add( new JLabel(icon));
					case 1 -> this.add( this.tabButton[cptBtn++] ); 
					case 2 -> this.add( this.tabLblCase[lig -1][col - 1] );
				}
			}
		}


		/*------------------------------*/
		/* Activation des composants    */
		/*------------------------------*/
		for (int cpt=0; cpt<this.tabButton.length; cpt++)
			this.tabButton[cpt].addActionListener(this);

	}


	public void majIHM()
	{
		Icon icon;
		for ( int lig=0; lig< this.tabLblCase.length; lig++)
		{
			for ( int col=0; col< this.tabLblCase[lig].length; col++)
			{
				icon = new ImageIcon( ctrl.getImage(lig, col));
				this.tabLblCase[lig][col].setIcon( icon );
			}
		}
	}

	public void actionPerformed ( ActionEvent e)
	{
		
		//A modifier si on veux faire des tableau de différente taille
		char type, sens;
		int cpt = 0;
		while ( ! (e.getSource().equals(this.tabButton[cpt]) ) )
		{
			cpt++;
		}

		if(cpt < ctrl.getNbColonne() || cpt > ctrl.getNbColonne() + ctrl.getNbLigne() * 2 - 1)
			type = 'c';
		else
			type = 'l';
		
		if( ctrl.getNbColonne() % 2 == 0)
		{
			if(cpt >= ctrl.getNbColonne() && cpt % 2 == 0)
				sens = '-';
			else
				sens = '+';
		}
		else
		{
			if(cpt >= ctrl.getNbColonne() && cpt % 2 == 0)
				sens = '+';
			else
				sens = '-';
		}

		if(cpt <  ctrl.getNbColonne())                            
			this.ctrl.permuter( type, '+',  cpt);

		if(cpt >= ctrl.getNbColonne() + ctrl.getNbLigne() * 2)      
			this.ctrl.permuter( type, '-',  cpt - (ctrl.getNbColonne() + ctrl.getNbLigne() * 2));

		if(cpt >= ctrl.getNbColonne() && cpt < ctrl.getNbColonne() + ctrl.getNbLigne() * 2) 
			this.ctrl.permuter( type, sens, (cpt - ctrl.getNbColonne()) / 2);
	}

	private String[][] getModele()
	{
		/* Voici un exemple de Modele généré pour une grille de 6 x 6

		{ {null,        "fl_haut", "fl_haut", "fl_haut", "fl_haut", "fl_haut", "fl_haut", null        },
		  {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {null,        "fl_bas",  "fl_bas",  "fl_bas",  "fl_bas",  "fl_bas",  "fl_bas",   null       }  };
		*/


		// Construction du Modele correspondant à la taille de notre Grille.
		String[][] tabModele = new String[ctrl.getNbLigne()+2][ctrl.getNbColonne()+2];

		for (int lig = 1; lig < tabModele.length-1; lig++ )
		{
			tabModele[lig][0]                          = "fl_gauche";
			tabModele[lig][tabModele[lig].length - 1 ] = "fl_droite";
		}

		for (int col = 1; col < tabModele[0].length-1; col++ )
		{
			tabModele[0]                     [col] = "fl_haut";
			tabModele[tabModele.length - 1 ] [col] = "fl_bas";
		}

		for (int lig=1; lig < tabModele.length-1; lig++ )
			for (int col = 1; col < tabModele[0].length-1; col++ )
				tabModele[lig][col] = "val";

		return tabModele;
	}

	public void activerBtn( boolean bool)
	{
		for( int cpt=0; cpt < this.tabButton.length; cpt++)
			this.tabButton[cpt].setEnabled(bool);
	}

}
