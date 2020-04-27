package lobby;

import java.awt.event.ActionListener;

import Game.Model;

public class UserController {
	private Model model;
	private View view;
	
	public UserController(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	public void listenerAssignament() {
		ActionListener startGameL;
		ActionListener createDeckL;
		
		startGameL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modello.startGame();
				//seguirebbe rendering sulla vista
			}
		};
		
		//immaginando un bottone start game nella view lobby assegno al bottone
		vista.getBtnstartGame().addActionListener(startGameL);
		
		createDeckL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modello.createDeck();
				//seguirebbe rendering sulla vista nel caso
			}
		};
		
		vista.getBtcardSelected().addActionListener(createDeckL);
		
	}
	
	
}
