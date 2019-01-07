package unipotsdam.gf.modules.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import unipotsdam.gf.modules.group.preferences.survey.SurveyData;
import unipotsdam.gf.modules.group.preferences.groupal.JacksonPojoToJson;

public class SurveyDataTest {
    @Test
    public void surveySerializationText() throws JsonProcessingException {
      /*  SurveyData surveyData = new SurveyData();
        LocalizedText localizedText = new LocalizedText();
        localizedText.setDe("deutshce Variante");
        localizedText.setDefaultText("englische Variante");
        surveyData.setTitle(localizedText);*/

        JacksonPojoToJson.writeExample(SurveyData.class);

    }
}
