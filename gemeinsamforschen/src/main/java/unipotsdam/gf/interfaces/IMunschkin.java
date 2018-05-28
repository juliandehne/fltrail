package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.munchkin.model.Munschkin;

/**
 * Created by dehne on 25.04.2018.
 */
public interface IMunschkin {

    Munschkin getMunschkin (int id);
    void letMunchKinFight(Munschkin otherMunchkin);
}
