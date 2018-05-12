package Objects;

import Panels.Menu;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connections conn = new Connections();
		if(conn.connect()) {
			Menu menu = new Menu(conn);
			menu.setVisible(true);
		}
	}
}
