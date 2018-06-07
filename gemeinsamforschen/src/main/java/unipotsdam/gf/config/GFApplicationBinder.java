package unipotsdam.gf.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;

public class GFApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(CommunicationDummyService.class).to(ICommunication.class);
    }
}
