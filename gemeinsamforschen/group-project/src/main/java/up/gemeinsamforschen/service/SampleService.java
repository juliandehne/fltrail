package up.gemeinsamforschen.service;

import up.gemeinsamforschen.model.SampleAnswer;

public class SampleService {

    public SampleAnswer provideSampleAnswer(String name) {
        SampleAnswer sampleAnswer = new SampleAnswer();
        sampleAnswer.setAnswer("Hello " + name);
        return sampleAnswer;
    }
}
