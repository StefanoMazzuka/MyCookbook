/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author McPuto
 */
public class Recipe {

    private String name;
    private String preparation;
    
    public void setName(String name) {
		this.name = name;
	}   
    public String getName() {
        return name;
    }
	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}
    public String getPreparation() {
        return preparation;
    }  
}
