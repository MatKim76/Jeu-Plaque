import javax.swing.*;
import java.awt.BorderLayout;

public class FrameJeu extends JFrame
{
	private ControleurJeu ctrl;

	private JLabel label;
	private PanelPlateau panelPlateau;
	private PanelCommande panelCommande;

	public FrameJeu(ControleurJeu ctrl)
	{
		this.ctrl = ctrl;
		
		this.label = new JLabel("Commencer une Partie !");
		this.panelPlateau  = new PanelPlateau  ( this.ctrl );
		this.panelCommande = new PanelCommande ( this.ctrl );

		this.setTitle   ( "Jeu" );
		this.setLocation( 400, 0 );
		
		this.setSize ( (this.ctrl.getNbColonne()+2)* 65,
				       (this.ctrl.getNbLigne()  +2)* 65 +160);

		this.setLayout( new BorderLayout() );

		this.add ( this.label,         BorderLayout.NORTH  );
		this.add ( this.panelPlateau,  BorderLayout.CENTER );
		this.add ( this.panelCommande, BorderLayout.SOUTH  );

		this.setVisible ( true );
	}


	public void newFrame()
	{
		this.remove( this.panelPlateau);
		this.setSize ( (this.ctrl.getNbColonne()+2)* 65, (this.ctrl.getNbLigne()  +2)* 65 +160);
		this.panelPlateau = new PanelPlateau(this.ctrl);
		this.add ( this.panelPlateau, BorderLayout.CENTER );

		this.majIHM();
	}


	public PanelPlateau getPlateau()
	{
		return this.panelPlateau;
	}


	public PanelCommande getCommande()
	{
		return this.panelCommande;
	}


	public void modifierText(String text)
	{
		this.label.setText(text);
	}


	public void majIHM()
	{
		this.panelPlateau.majIHM();
	}
}
