package unipotsdam.gf.core.database;

import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.mysql.MysqlConnect;

public class TestGFApplicationBinder extends GFApplicationBinder {



    @Override
    protected void bindMore() {
        bind(MysqlTestConnect.class).to(MysqlConnect.class);
    }
}
