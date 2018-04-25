package unipotsdam.gf.munchkin.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dehne on 24.04.2018.
 */

@XmlRootElement(name = "Munschkin")
public class Munschkin {
    private int munschkinId;
    private String lastName;
    private String firstName;
    private String BadThings;
    private int strength;

    public Munschkin() {

    }

/*
    public Munschkin(int munschkinId, String lastName, String firstName, String badThings, int strength) {
        this.munschkinId = munschkinId;
        this.lastName = lastName;
        this.firstName = firstName;
        BadThings = badThings;
        this.strength = strength;
    }
*/



    public int getMunschkinId() {
        return munschkinId;
    }

    public void setMunschkinId(int munschkinId) {
        this.munschkinId = munschkinId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBadThings() {
        return BadThings;
    }

    public void setBadThings(String badThings) {
        BadThings = badThings;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Munschkin{");
        sb.append("munschkinId=").append(munschkinId);
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", BadThings='").append(BadThings).append('\'');
        sb.append(", strength=").append(strength);
        sb.append(", badThings='").append(getBadThings()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
