package Objects;

import Panels.MyRecipes;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connections conn = new Connections();
		if(conn.connect()) {
			MyRecipes mr = new MyRecipes(conn);
			mr.setVisible(true);
		}
	}
}
