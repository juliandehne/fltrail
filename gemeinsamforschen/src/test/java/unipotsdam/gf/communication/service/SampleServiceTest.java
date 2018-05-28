package unipotsdam.gf.communication.service;


import org.junit.Test;
import unipotsdam.gf.communication.model.SampleAnswer;

import static org.junit.Assert.assertEquals;


public class SampleServiceTest {

    @Test
    public void returnCorrectMessage() {
        SampleService sampleService = new SampleService();
        SampleAnswer sampleAnswer = sampleService.provideSampleAnswer("test");

        assertEquals(sampleAnswer.getAnswer(), "Hello test");
    }
}
