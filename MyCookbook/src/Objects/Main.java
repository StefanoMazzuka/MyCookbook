package Objects;

import java.awt.TextArea;

import Panels.MyRecipes;
import Panels.Test;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connections conn = new Connections();
		if(conn.connect()) {
			MyRecipes mr = new MyRecipes(conn);
			mr.setVisible(true);
//			Test t = new Test();
//			t.setVisible(true);
		}
	}
}
