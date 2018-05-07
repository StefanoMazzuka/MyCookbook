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
public class Ingredient {

    private String name;
    private double quantity;
    private String units;
    private String recipeName;
    
    public Ingredient(String name, double quantity, String units, String recipeName) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
        this.recipeName = recipeName;
    }
    
    public String getName() {
        return name;
    }
    public double getQuantity() {
        return quantity;
    }
    public String getUnits() {
        return units;
    }
    public String getRecipeName() {
        return recipeName;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}