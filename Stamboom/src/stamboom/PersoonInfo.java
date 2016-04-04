/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stamboom;

/**
 *
 * @author Roy
 */
public class PersoonInfo {
    String infoString;
    int generatie;

    public PersoonInfo(String infoString, int generatie)
    {
        this.infoString = infoString;
        this.generatie = generatie;
    }

    public String getInfoString()
    {
        return infoString;
    }

    public int getGeneratie()
    {
        return generatie;
    }
    
    
    
}
