/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units;

/**
 * Units with this interface should also be given the builder type.
 * the UI will change when these units are selected to display building options 1-3
 * Any unit implementing this interface should have a Unit[3] field for use in
 * getProduct method. should use lazy instantiation.
 * @author Joseph
 */
public interface Builder {

   
    /**
     * checks if the place the unit will be spawned is valid. used for basic factories
     * @return result
     */
    public boolean isOutputClear();

    /**
     * checks if place the building will be built is valid. 
     * @param i determines what is being built. (0 = build slot 1, etc)
     * @return result
     */
    public boolean isOutputClearFT(int i);
    
    /**
     * makes the thing from build slot 1
     */
    public void Produce1();
    
    /**
     * makes the thing from build slot 2
     */
    public void Produce2();

    /**
     * makes the thing from build slot 3
     */
    public void Produce3();
    
    /**
     * lazily instantiates products[p] and then returns it
     * @param p
     * @return the unit stored in products[p]
     */
    public Unit getProduct(int p);

}
