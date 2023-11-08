
public class ControleurJeu
{
	private FrameJeu ihm;
	private PlateauJeu metier;

	private boolean blanc;
	private int difficulte;
	private String type;

	public ControleurJeu(int difficulte, String type)
	{
		this.difficulte = difficulte;
		this.blanc = false;
		this.type = type;

		this.metier = new PlateauJeu(this, this.difficulte, this.type);
		this.ihm    = new FrameJeu ( this );
	}



	/*public void newFrame()
	{
		this.ihm.newFrame();
	}*/



	public FrameJeu getIhm()
	{
		return this.ihm;
	}



	// a modifier plus tard
	public String getImage(int lig, int col)
	{
		if( this.blanc == false)
		{
			switch ( metier.getVal(lig, col) )
			{
				case 'l' : return "./image/lave.gif";
				case 'p' : return "./image/pierre.gif";
				case 'm' : return "./image/obsi.gif";

				case 'd' : return "./image/glow.gif";
				case 'a' : return "./image/or.gif";
				case 'h' : return "./image/hero.gif";	
				
				case 'z' : return "./image/zombie.gif";
				case 's' : return "./image/squelette.gif";
				case 'c' : return "./image/creeper.gif";
				
				case 'j' : return "./image/slime.gif";
				case 't' : return "./image/nether.gif";
			}
		}
		else
		{
			switch ( metier.getVal(lig, col) )
			{
				case 'l' : return "./image/vide.gif";
				case 'p' : return "./image/vide.gif";
				case 'm' : return "./image/vide.gif";

				case 'd' : return "./image/glow.gif";
				case 'a' : return "./image/or.gif";
				case 'h' : return "./image/hero.gif";
				
				case 'z' : return "./image/zombie.gif";
				case 's' : return "./image/squelette.gif";
				case 'c' : return "./image/creeper.gif";
				
				case 'j' : return "./image/slime.gif";
				case 't' : return "./image/nether.gif";
			}
		}
		return "./vide.gif";
	}

	public int getNbLigne  () { return this.metier.getNbLigne (); }
	public int getNbColonne() { return this.metier.getNbColone(); }



	// Méthode appelée sur le clique d'un bouton
	public void permuter ( char type, char sens, int indice )
	{
		this.metier.permuter ( type, sens, indice );
		this.ihm.majIHM();
	}



	//permet d'activer ou désactiver la vision du plateau
	public void modeBlanc( boolean bool)
	{
		this.blanc = bool;
	}



	public void deplacerHero( char dir)
	{
		this.metier.deplacerHero( dir );
		this.ihm.majIHM();
	}



	public void lancerPartie()
	{
		this.ihm.getPlateau().activerBtn(false);
		PanelCommande.modifierText("Visualiser");
		this.metier = new PlateauJeu(this, this.difficulte, this.type);
		
		this.ihm.newFrame();
	}



	public void deplacementHero()
	{
		this.ihm.majIHM();

		PanelCommande.modifierText("Deplacement du héro...");

		this.ihm.getPlateau().activerBtn(false);
	}



	public void deplacementPlaque()
	{
		PanelCommande.modifierText("Terminer");
		
		this.ihm.getPlateau().activerBtn(true);
	}



	public void finPartie()
	{
		this.ihm.getPlateau().activerBtn(false);
		this.ihm.getCommande().activerBtn(false);

		this.modeBlanc(false);
		this.ihm.majIHM();

		this.metier.resetLevel();

		PanelCommande.modifierText("Lancer une partie");

	}



	public int getDifficulte()
	{
		return this.difficulte;
	}



	public static void main(String[] a)
	{
		int dif = 0;
		String type = " ";

		FrameDifficulte frame = new FrameDifficulte();

		while( dif != 404 )
		{
			while( type.equals(" ") )
			{
				try
				{
					Thread.sleep (3000);
				}
				catch (InterruptedException ignore) {}
				
				dif  = frame.getDifficulte();
				type = frame.getChoix();
			}
			new ControleurJeu( dif, type );

			type = " ";
			dif = 0;
			frame.reset();
		}
	}
}
