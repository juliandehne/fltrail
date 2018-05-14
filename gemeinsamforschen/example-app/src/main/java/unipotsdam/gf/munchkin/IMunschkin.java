package unipotsdam.gf.munchkin;

import unipotsdam.gf.munchkin.model.Munschkin;

import javax.ws.rs.PathParam;

/**
 * Created by dehne on 25.04.2018.
 */
public interface IMunschkin {

    Munschkin getMunschkin (int id);
    void letMunchKinFight(Munschkin otherMunchkin);
}
