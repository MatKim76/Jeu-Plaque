import java.util.Scanner;

import java.io.FileInputStream;

public class PlateauJeu
{
	private int NB_CHALLENGE = 4;
	
	private char[][] plateau;
	
	private ControleurJeu ctrl;

	private char temp;
	private boolean bool;

	private int difficulte;
	private String type;
	private static int level = 0;


	public PlateauJeu(ControleurJeu ctrl, int difficulte, String type)
	{
		this.temp ='d';
		this.bool = false;
		this.ctrl = ctrl;
		this.difficulte = difficulte;
		this.type = type;

		if( PlateauJeu.level == 0)
			this.plateau = this.chargerPlateau();
		else
		{
			if( this.type.equals("tutoriel") || this.type.startsWith("challenge"))
				this.plateau = this.chargerPlateau();
			else
				this.plateau = this.genererPlateau(1, 1);
		}
		
	}


	
	public char[][] genererPlateau (int lig, int col)
	{
		int nbPlateforme = 9;
		int nbMur = 0;
		int nbElem = 0;
		int nbMonstre = 0;

		if( this.type.equals("progressif"))
		{
			lig = col = 5;
			for(int cpt = 1; cpt < PlateauJeu.level; cpt++)
			{
				if( cpt % 4 == 0)  
				{
					if(lig >= col)
						col++;
					else
						lig++;
					
					nbPlateforme += (lig + col) / 2 - 1;
				}
				if( cpt % 3 == 0)  { nbMur++; }
				if( cpt % 10 == 0) { nbMonstre++; }
				if( cpt % 2 == 0)  { nbPlateforme--; }
				if( cpt % 7 == 0)  { nbElem++; }

			}
			PlateauJeu.level++;
		}
		
		
		//modifie les parametre du plateau en fonction de la difficulté
		if( this.type.equals("constant"))
		{
			switch (this.difficulte)
			{
				case 1 : lig = col = 5;
						nbPlateforme = 8;
						nbMur = 0;
						nbElem = 1;
						nbMonstre = 0;
						break;
						
				case 2 : lig = col = 6;
						nbPlateforme = 14;
						nbMur = 0;
						nbElem = 1;
						nbMonstre = 0;
						break;
						
				case 3 : lig = col = 8;
						nbPlateforme = 20;
						nbMur = 4;
						nbElem = 2;
						nbMonstre = 1;
						break;
						
				case 4 : lig = col = 10;
						nbPlateforme = 25;
						nbMur = 8;
						nbElem = 1;
						nbMonstre = 2;
						break;
			}
		}

		//création du plateau et remplissage avec de la lave
		char[][] plateau = new char[lig][col];
		
		for(int cptLig=0; cptLig < lig; cptLig++)
			for(int cptCol=0; cptCol < col; cptCol++)
				plateau[cptLig][cptCol] = 'l';
		
		
		//ajout des élements sur le plateau
		plateau = this.ajouterElem( plateau, 'd', 1);
		plateau = this.ajouterElem( plateau, 'a', 1);
		plateau = this.ajouterElem( plateau, 'p', nbPlateforme);
		plateau = this.ajouterElem( plateau, 'm', nbMur);
		plateau = this.ajouterElem( plateau, '$', nbElem);
		plateau = this.ajouterElem( plateau, '@', nbMonstre);
		if( this.difficulte == 4 || PlateauJeu.level >= 25)
			plateau = this.ajouterElem( plateau, 't', 2);

		return plateau;
	}



	public char[][] chargerPlateau()
	{
		String fichier = ""; 

		if( this.type.equals("tutoriel") )
			fichier = "./charger/tutoriel/tuto_" + PlateauJeu.level +".data";

		if( this.type.startsWith ("challenge"))
			fichier = "./charger/challenge/"+ this.type +".data";

		if(PlateauJeu.level == 0)
		{
			fichier = "./charger/intro.data";
			PlateauJeu.level++;
		}

		try
		{
			Scanner sc = new Scanner ( new FileInputStream ( fichier ) );

			String taille = sc.nextLine();
			int lig = Integer.parseInt(taille.charAt(0) +"");
			int col = Integer.parseInt(taille.charAt(2) +"");

			char[][] plateauT = new char[lig][col];
			
			int cptLig =0;
			while(cptLig < lig )
			{
				String ligne = sc.nextLine();
				
				int cptCol = 0;
				while(cptCol < col)
				{
					plateauT[cptLig][cptCol] = ligne.charAt(cptCol);
					cptCol++;
				}
				cptLig++;
			}
			sc.close();

			return plateauT;
		}
		catch (Exception e){ e.printStackTrace(); }

		return null;
	}



	public char[][] ajouterElem(char[][] plateau, char type, int nb)
	{
		int rlig, rcol;
		char cara = ' ';
		
		int cpt = 0;
		while (cpt < nb)
		{
			//choix d'une case au hazars
			rlig = (int) (Math.random()* plateau.length);
			rcol = (int) (Math.random()* plateau[0].length);
			
			//définition du caractere aléatoirement pour les monstres et les objets
			if( type >= 'a' && type <= 'z' ) { cara = type; }
			else
			{
				if( type == '@' )
				{
					switch ( (int) (Math.random()*3) )
					{
						case 0 -> cara = 'z';
						case 1 -> cara = 's';
						case 2 -> cara = 'c';
					}
				}
				if( type == '$' )
				{
					cara = 'j';
				}
			}

			//vérification que la case n'est pas déja prise
			if( plateau[rlig][rcol] == 'l')
			{
				plateau[rlig][rcol] = cara;
				cpt++;
			}
		}
		return plateau;
	}



	public char getVal (int lig, int col)
	{
		return this.plateau[lig][col];
	}


	public int getNbLigne () { return this.plateau   .length; }
	public int getNbColone() { return this.plateau[0].length; }


	public void resetLevel() { PlateauJeu.level = 1;}



	public void permuter ( char type, char sens, int indice )
	{
		char tmp = ' ', tmp2;
		int cpt;

		if ( type == 'l' )
		{
			if ( sens =='+' )
			{
				tmp = this.plateau[indice][this.plateau[0].length - 1];
				for ( cpt=this.plateau[0].length - 1; cpt> 0; cpt--)
				{
					this.plateau[indice][cpt] = this.plateau[indice][cpt - 1];
				}
				this.plateau[indice][0] = tmp;

				tmp2 =' ';
				for ( cpt=0; cpt< this.plateau[0].length; cpt++)
				{
					if( this.plateau[indice][cpt]== 'a' || this.plateau[indice][cpt]== 'd' || this.plateau[indice][cpt] == 'm' || this.plateau[indice][cpt]== 't')
					{
						if(cpt == 0)
						{
							tmp2 = this.plateau[indice][cpt];
							int cpt2 =  this.plateau[0].length - 1;
							while( (this.plateau[indice][cpt2] == 'a' || this.plateau[indice][cpt2] == 'd' || this.plateau[indice][cpt2] == 'm' || this.plateau[indice][cpt2]== 't') && cpt2 > 0)
							{
								cpt2--;
							}
							this.plateau[indice][cpt] = this.plateau[indice][cpt2];
						}
						else
						{
							tmp = this.plateau[indice][cpt];
							this.plateau[indice][cpt] = this.plateau[indice][cpt - 1];
							this.plateau[indice][cpt - 1] = tmp;
						}
					
					}
				}
				if( tmp2 != ' ')
				{
					this.plateau[indice][this.plateau.length - 1] = tmp2;
				}
				
			}
			else
			{
				tmp = this.plateau[indice][0];
				for ( cpt=0; cpt< this.plateau[0].length - 1; cpt++)
				{
					this.plateau[indice][cpt] = this.plateau[indice][cpt + 1];
				}
				this.plateau[indice][this.plateau[0].length - 1] = tmp;

				tmp2 =' ';
				for ( cpt=this.plateau[0].length -1; cpt >= 0; cpt--)
				{
					if( this.plateau[indice][cpt]== 'a' || this.plateau[indice][cpt]== 'd' || this.plateau[indice][cpt] == 'm' || this.plateau[indice][cpt]== 't')
					{
						if(cpt == this.plateau[0].length -1)
						{
							tmp2 = this.plateau[indice][cpt];
							int cpt2 = 0;
							while( (this.plateau[indice][cpt2] == 'a' || this.plateau[indice][cpt2] == 'd' || this.plateau[indice][cpt2] == 'm' || this.plateau[indice][cpt2]== 't') && cpt2 < this.plateau[0].length -1)
							{
								cpt2++;
							}
							this.plateau[indice][cpt] = this.plateau[indice][cpt2];
						}
						else
						{
							tmp = this.plateau[indice][cpt];
							this.plateau[indice][cpt] = this.plateau[indice][cpt + 1];
							this.plateau[indice][cpt + 1] = tmp;
						}
					
					}
				}
				if( tmp2 != ' ')
				{
					this.plateau[indice][0] = tmp2;
				}
			}
		}
		else
		{
			if ( sens =='+' )
			{
				tmp = this.plateau[0][indice];
				for ( cpt=0; cpt< this.plateau.length - 1; cpt++)
				{
					this.plateau[cpt][indice] = this.plateau[cpt + 1][indice];
				}
				this.plateau[this.plateau.length - 1][indice] = tmp;

				tmp2 =' ';
				for ( cpt=this.plateau.length -1; cpt >= 0; cpt--)
				{
					if( this.plateau[cpt][indice] == 'a' || this.plateau[cpt][indice] == 'd' || this.plateau[cpt][indice] == 'm' || this.plateau[cpt][indice] == 't')
					{
						if(cpt == this.plateau.length -1)
						{
							tmp2 = this.plateau[cpt][indice];
							int cpt2 = 0;
							while( (this.plateau[cpt2][indice] == 'a' || this.plateau[cpt2][indice] == 'd' || this.plateau[cpt2][indice] == 'm' || this.plateau[cpt2][indice]== 't') && cpt2 < this.plateau.length)
							{
								cpt2++;
							}
							this.plateau[cpt][indice] = this.plateau[cpt2][indice];
						}
						else
						{
							tmp = this.plateau[cpt][indice];
							this.plateau[cpt][indice] = this.plateau[cpt + 1][indice];
							this.plateau[cpt + 1][indice] = tmp;
						}
					
					}
				}
				if( tmp2 != ' ')
				{
					this.plateau[0][indice] = tmp2;
				}
			}
			else
			{
				tmp = this.plateau[this.plateau.length - 1][indice];
				for ( cpt=this.plateau.length - 1; cpt> 0; cpt--)
				{
					this.plateau[cpt][indice] = this.plateau[cpt - 1][indice];
				}
				this.plateau[0][indice] = tmp;

				tmp2 =' ';
				for ( cpt=0; cpt< this.plateau.length; cpt++)
				{
					if( this.plateau[cpt][indice]== 'a' || this.plateau[cpt][indice]== 'd' || this.plateau[cpt][indice] == 'm' || this.plateau[cpt][indice] == 't')
					{
						if(cpt == 0)
						{
							tmp2 = this.plateau[cpt][indice];
							int cpt2 =  this.plateau.length - 1;
							while( (this.plateau[cpt2][indice] == 'a' || this.plateau[cpt2][indice] == 'd' || this.plateau[cpt2][indice] == 'm' || this.plateau[cpt2][indice]== 't') && cpt2 > 0)
							{
								cpt2--;
							}
							this.plateau[cpt][indice] = this.plateau[cpt2][indice];
						}
						else
						{
							tmp = this.plateau[cpt][indice];
							this.plateau[cpt][indice] = this.plateau[cpt - 1][indice];
							this.plateau[cpt - 1][indice] = tmp;
						}
					
					}
				}
				if( tmp2 != ' ')
				{
					this.plateau[this.plateau.length - 1][indice] = tmp2;
				}
			}
		}
		this.deplacerMonstre();
	}



	public void deplacerHero( char dir )
	{
		int lig=0, col=0;
		
		//recherche de la posision de hero sur le plateau
		for(int cptLig=0; cptLig < this.plateau.length; cptLig++)
		{
			for(int cptCol=0; cptCol <  this.plateau[0].length; cptCol++)
			{
				if( this.bool == false)
				{
					if (this.plateau[cptLig][cptCol] == 'd')
					{
						lig = cptLig;
						col = cptCol;
						this.bool = true;
					}
				}
				else
				{
					if (this.plateau[cptLig][cptCol] == 'h')
					{
						lig = cptLig;
						col = cptCol;
					}
				}
			}
		}
		
		this.plateau[lig][col] = this.temp;
		
		//deplacement normal du hero
		if( this.temp != 'j')
		{
			switch ( dir )
			{
				case 'O' -> col--;
				case 'E' -> col++;
				case 'S' -> lig++;
				case 'N' -> lig--;
			}
		}

		//vérification que le hero ne sort pas du tableau
		//if (col < 0 || col >= this.plateau[lig].length || lig < 0 || lig >= this.plateau.length) 
		//	this.ctrl.getIhm().modifierText("Vous ne pouvez pas sortir du plateau...");
			
		if( col < 0) {col = 0;}
		if( col >= this.plateau[0].length) {col = this.plateau[0].length -1;}
		if( lig < 0) {lig = 0;}
		if( lig >= this.plateau.length) {lig = this.plateau.length -1;}
		
		//déplacement du hero sur une case slime
		if( this.temp == 'j')
		{
			switch ( dir )
			{
				case 'O' :	if( col - 1 >= 0 )
							{
								col--;
								if( col - 1 >= 0 && this.plateau[lig][col - 1] != 'm')
									{col--;}
								else 
								{
									if(this.plateau[lig][col] == 'm')
										col++;
								}
							}
							else { col++; }
							break;
							
				case 'E' :	if( col + 1 < this.plateau[lig].length)
							{
								col++;
								if( col + 1 < this.plateau.length && this.plateau[lig][col +1] != 'm')
									{col++;}
								else 
								{
									if(this.plateau[lig][col] == 'm')
										col--;
								}
							}
							else { col--; }
							break;
							
				case 'S' :	if( lig + 1 < this.plateau.length)
							{
								lig++;
								if( lig + 1 < this.plateau.length && this.plateau[lig + 1][col] != 'm')
									{lig++;}
								else 
								{
									if(this.plateau[lig][col] == 'm')
										lig--;
								}
							}
							else { lig--; }
							break;
							
				case 'N' :	if( lig - 1 >= 0 )
							{
								lig--;
								if( lig - 1 >= 0 && this.plateau[lig -1][col] != 'm')
									{lig--;}
								else 
								{
									if(this.plateau[lig][col] == 'm')
										lig++;
								}
							}
							else { lig++; }
							break;
			}
		}
		
		//vérification que le hero ne se deplace pas dans un mur
		if ( this.plateau[lig][col] == 'm')
		{
			this.ctrl.getIhm().modifierText("Vous ne pouvez pas traverser les murs...");
			switch ( dir )
			{
				case 'O' -> col++;
				case 'E' -> col--;
				case 'S' -> lig--;
				case 'N' -> lig++;
			}
		}
		
		//positionnement du hero et recuperation de la case ou il se situe
		this.temp = this.plateau[lig][col];
		this.plateau[lig][col] = 'h';

		//téléprtatiooonnn
		if( this.temp == 't')
		{
			for(int cptLig=0; cptLig < this.plateau.length; cptLig++)
				for(int cptCol=0; cptCol <  this.plateau[0].length; cptCol++)
					if( this.plateau[cptLig][cptCol] == 't' ) { this.plateau[cptLig][cptCol] = 'h'; }
			
			this.plateau[lig][col] = 't';
			this.ctrl.getIhm().modifierText("Téléportation !");
		}

		//analyse des cases qui tue le hero
		if( this.temp == 'l' || this.temp == 'z' || this.temp == 's' || this.temp == 'c') 
		{
			switch(this.temp)
			{
				case 'l' -> this.ctrl.getIhm().modifierText("Vous êtes tombé dans la lave...");
				case 'z' -> this.ctrl.getIhm().modifierText("Vous avez foncer dans un zombie...");
				case 's' -> this.ctrl.getIhm().modifierText("Vous avez foncer dans un squelette...");
				case 'c' -> this.ctrl.getIhm().modifierText("Vous avez foncer dans un creeper...");
			}
			this.ctrl.finPartie();
		}

		//analyse de la case arrivée
		if( this.temp == 'a')
		{
			PanelCommande.modifierText("Niveau suivant");
			this.ctrl.getIhm().modifierText("Bravo vous avez réussis !");
			this.ctrl.modeBlanc(false);
			this.ctrl.getIhm().getCommande().activerBtn(false);
			this.ctrl.getIhm().majIHM();
			
			if( this.type.startsWith("challenge"))
			{
				try
				{
					Thread.sleep (3000);
				}
				catch (InterruptedException ignore) {}
				this.ctrl.getIhm().dispose();
			}
		}

		//action des deplacement des monstres
		if( this.actionMonstre() == false );
		{
			this.deplacerMonstre();
			this.actionMonstre();
		}
	}



	public void deplacerMonstre()
	{
		//recupération du nombre de monstre
		int nbMon = 0;
		for(int cptLig=0; cptLig < this.plateau.length; cptLig++)
			for(int cptCol=0; cptCol <  this.plateau[0].length; cptCol++)
				if (this.plateau[cptLig][cptCol] == 'z' ||
				    this.plateau[cptLig][cptCol] == 's' ||
				    this.plateau[cptLig][cptCol] == 'c' )
					nbMon++;
		
		//création de tableau pour stocker les monstres
		char[] tabMonstre  = new char[nbMon];
		int[][] posMonstre = new int [2][nbMon];
		
		//récupération du type et la position des monstres
		int cpt = 0;
		for(int cptLig=0; cptLig < this.plateau.length; cptLig++)
		{
			for(int cptCol=0; cptCol <  this.plateau[0].length; cptCol++)
			{
				if (this.plateau[cptLig][cptCol] == 'z' ||
				    this.plateau[cptLig][cptCol] == 's' ||
				    this.plateau[cptLig][cptCol] == 'c' )
				{
					tabMonstre[cpt] = this.plateau[cptLig][cptCol];
					
					posMonstre[0][cpt]   = cptLig;
					posMonstre[1][cpt++] = cptCol;
				}
			}
		}
		
		//déplacement des monstres
		int lig, col;
		cpt = 0;
		while ( cpt < nbMon )
		{
			lig = posMonstre[0][cpt];
			col = posMonstre[1][cpt];
			
			//Le monstre peut il se déplacer a coté
			boolean bool =false;
			
			if(lig + 1 < this.plateau.length)
				if(this.plateau[lig + 1][col] == 'p')
					bool = true;
			
			if(lig - 1 >= 0 )
				if(this.plateau[lig - 1][col] == 'p')
					bool = true;
			
			if(col + 1 < this.plateau[lig].length)
				if(this.plateau[lig][col + 1] == 'p')
					bool = true;
			
			if(col - 1 >= 0 )
				if(this.plateau[lig][col - 1] == 'p')
					bool = true;
			
			//déplacement si il existe une case a coté
			if (bool == true)
			{
				int test = -1;
				while ( this.plateau[lig][col] == tabMonstre[cpt] )
				{
					test = (int) (Math.random()*4);
					switch ( test )
					{
						case 0 : if(lig + 1 < this.plateau.length)
									if(this.plateau[lig + 1][col] == 'p')
									{
										this.plateau[lig][col] = 'p';
										this.plateau[lig + 1][col] = tabMonstre[cpt];
									}
								break;
								
						case 1 : if(lig - 1 >= 0 )
									if(this.plateau[lig - 1][col] == 'p')
									{
										this.plateau[lig][col] = 'p';
										this.plateau[lig - 1][col] = tabMonstre[cpt];
									}
								break;
								
						case 2 : if(col + 1 < this.plateau.length)
									if(this.plateau[lig][col + 1] == 'p')
									{
										this.plateau[lig][col] = 'p';
										this.plateau[lig][col + 1] = tabMonstre[cpt];
									}
								break;
								
						case 3 : if(col - 1 >= 0 )
									if(this.plateau[lig][col - 1] == 'p')
									{
										this.plateau[lig][col] = 'p';
										this.plateau[lig][col - 1] = tabMonstre[cpt];
									}
								break;
					}
				}
			}
			cpt++;
		}
	}


	public boolean actionMonstre()
	{
		//recupération du nombre de monstre
		int nbMon = 0;
		for(int cptLig=0; cptLig < this.plateau.length; cptLig++)
			for(int cptCol=0; cptCol <  this.plateau[0].length; cptCol++)
				if (this.plateau[cptLig][cptCol] == 'z' ||
				    this.plateau[cptLig][cptCol] == 's' ||
				    this.plateau[cptLig][cptCol] == 'c' )
					nbMon++;
		
		//création de tableau pour stocker les monstres
		char[] tabMonstre  = new char[nbMon];
		int[][] posMonstre = new int [2][nbMon];
		
		//récupération du type et la position des monstres
		int cpt = 0;
		for(int cptLig=0; cptLig < this.plateau.length; cptLig++)
		{
			for(int cptCol=0; cptCol <  this.plateau[0].length; cptCol++)
			{
				if (this.plateau[cptLig][cptCol] == 'z' ||
				    this.plateau[cptLig][cptCol] == 's' ||
				    this.plateau[cptLig][cptCol] == 'c' )
				{
					tabMonstre[cpt] = this.plateau[cptLig][cptCol];
					
					posMonstre[0][cpt]   = cptLig;
					posMonstre[1][cpt++] = cptCol;
				}
			}
		}

		boolean estMort = false;
		for(cpt = 0; cpt < tabMonstre.length; cpt++)
		{
			//Attaque du zombie de 1 case dans les 4 directions
			if( tabMonstre[cpt] == 'z')
			{
				if( posMonstre[0][cpt] - 1 >= 0 ) 
					if( this.plateau[posMonstre[0][cpt] - 1][posMonstre[1][cpt]] == 'h')
					{
						this.ctrl.getIhm().modifierText("Vous vous êtes fais mordre par un zombie...");
						estMort = true;
					}
				if( posMonstre[0][cpt] + 1 < this.plateau.length ) 
					if( this.plateau[posMonstre[0][cpt] + 1][posMonstre[1][cpt]] == 'h')
					{
						this.ctrl.getIhm().modifierText("Vous vous êtes fais mordre par un zombie...");
						estMort = true;
					}
				if( posMonstre[1][cpt] - 1 >= 0 ) 
					if( this.plateau[posMonstre[0][cpt]][posMonstre[1][cpt] -1] == 'h')
					{
						this.ctrl.getIhm().modifierText("Vous vous êtes fais mordre par un zombie...");
						estMort = true;
					}
				if( posMonstre[1][cpt] + 1 < this.plateau[0].length ) 
					if( this.plateau[posMonstre[0][cpt]][posMonstre[1][cpt] + 1] == 'h')
					{
						this.ctrl.getIhm().modifierText("Vous vous êtes fais mordre par un zombie...");
						estMort = true;
					}
			}

			//Attaque du squelette qui tire 1 fleche dans 1 direction au hazard
			if( tabMonstre[cpt] == 's')
			{
				int lig = 0;
				int col = 0;
				int compt1 = 0;
				int compt2 = 0;
				
				//Tirage de la direction
				int rand = (int)(Math.random()* 4);
				switch( rand )
				{
					case 0 -> compt1 = 1;
					case 1 -> compt1 = -1;
					case 2 -> compt2 = 1;
					case 3 -> compt2 = -1;
				}
				

				//Regarde si la fleche touche le hero
				while( posMonstre[0][cpt] + lig < this.plateau.length && posMonstre[0][cpt] + lig >= 0 &&
				       posMonstre[1][cpt] + col < this.plateau[0].length && posMonstre[1][cpt] + col >= 0)//cest tout cassé!
				{
					if( this.plateau[ posMonstre[0][cpt] + lig ][ posMonstre[1][cpt] + col] == 'h')
					{
						estMort = true;
						this.ctrl.getIhm().modifierText("Vous vous êtes pris une flèche d'un squelette...");
					}
					lig += compt1;
					col += compt2;
				}
			}

			//Attaque du creeper
			if( tabMonstre[cpt] == 'c')
			{
				
			}
		}

		if( estMort ) this.ctrl.finPartie();
		return estMort;
	}

}

