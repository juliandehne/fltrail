package unipotsdam.gf.core.database;

import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.mysql.MysqlConnect;

public class TestGFApplicationBinder extends GFApplicationBinder {

    @Override
    protected void configure() {
        super.configure();
        bind(InMemoryMySqlConnect.class).to(MysqlConnect.class);

    }
}
