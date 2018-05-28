package unipotsdam.gf.modules.communication.service;

import unipotsdam.gf.modules.communication.model.SampleAnswer;

public class SampleService {

    public SampleAnswer provideSampleAnswer(String name) {
        SampleAnswer sampleAnswer = new SampleAnswer();
        sampleAnswer.setAnswer("Hello " + name);
        return sampleAnswer;
    }
}
