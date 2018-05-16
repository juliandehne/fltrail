package up.gemeinsamforschen.service;


import org.junit.Test;
import up.gemeinsamforschen.model.SampleAnswer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class SampleServiceTest {

    @Test
    public void returnCorrectMessage() {
        SampleService sampleService = new SampleService();
        SampleAnswer sampleAnswer = sampleService.provideSampleAnswer("test");

        assertThat(sampleAnswer.getAnswer(), is("Hello test"));
    }
}
