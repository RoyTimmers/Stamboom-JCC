/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stamboom;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Roy
 */
public class Persoon implements Serializable {

    int id;
    String name;
    int leeftijd;
    String geslacht;
    Persoon vader;
    Persoon moeder;

    public Persoon(int id, String name, int leeftijd, String geslacht, Persoon vader, Persoon moeder)
    {
        this.id = id;
        this.name = name;
        this.leeftijd = leeftijd;
        this.geslacht = geslacht;
        this.vader = vader;
        this.moeder = moeder;
    }

    
    void getStamboom(ArrayList<PersoonInfo> lijst, int g)
    {
        lijst.add(new PersoonInfo(this.toString(), g));
        if (this.getVader() != null) {
            g++;
            this.getVader().getStamboom(lijst, g);
            if (this.getMoeder() != null) {
                this.getMoeder().getStamboom(lijst, g);
            }
        } else if (this.getMoeder() != null) {
            g++;
            this.getMoeder().getStamboom(lijst, g);
        }
    }

    public String stamboomAlsString()
    {
        StringBuilder builder = new StringBuilder();
        ArrayList<PersoonInfo> lijstPersoonInfos = new ArrayList<>();
        getStamboom(lijstPersoonInfos, 0);
        for (PersoonInfo pi : lijstPersoonInfos) {
            for (int i = 0; i < pi.getGeneratie(); i++) {
                builder.append("__");
            }
            builder.append(pi.getInfoString());
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    @Override
    public String toString()
    {
        String s = name + " (" + geslacht + ") " + leeftijd + " jaar";
        return s;
    }

    public Persoon getVader()
    {
        return vader;
    }

    public void setVader(Persoon vader)
    {
        this.vader = vader;
    }

    public Persoon getMoeder()
    {
        return moeder;
    }

    public void setMoeder(Persoon moeder)
    {
        this.moeder = moeder;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getLeeftijd()
    {
        return leeftijd;
    }

    public String getGeslacht()
    {
        return geslacht;
    }

}
