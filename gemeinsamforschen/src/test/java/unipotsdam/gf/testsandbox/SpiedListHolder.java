package unipotsdam.gf.testsandbox;

import unipotsdam.gf.core.testsandbox.TestListInterface;
import unipotsdam.gf.interfaces.Feedback;

import javax.inject.Inject;

public class SpiedListHolder {
    private TestListInterface spiedList;


    @Inject
    public SpiedListHolder(TestListInterface spiedList) {
        this.spiedList = spiedList;
    }

    public TestListInterface getSpiedList() {
        return spiedList;
    }
}
