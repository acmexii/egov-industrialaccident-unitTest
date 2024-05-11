package industrialaccident;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import industrialaccident.config.kafka.KafkaProcessor;
import industrialaccident.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReceiptTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        ReceiptTest.class
    );

    @Autowired
    private KafkaProcessor processor;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public AssessmentRepository repository;

    @Test
    @SuppressWarnings("unchecked")
    public void test0() {
        //given:
        Assessment entity = new Assessment();

        entity.setId("1");
        entity.setAccidentId("1");
        entity.setBusinessCode("bc_1");
        entity.setEmployeeId("user01");
        entity.setAssessorId("1");
        entity.setHospitalCode("hp_1");
        entity.setDoctorNote("골절");
        entity.setResults(null);
        entity.setDate("N/A");
        entity.setComments("N/A");

        repository.save(entity);

        //when:

        MedicalBenefitApplied event = new MedicalBenefitApplied();

        event.setId("1");
        event.setBusinessCode("bc_01");
        event.setEmployeeId("user01");
        event.setName("user");
        event.setHospitalCode("hp_1");
        event.setDoctorNote("골절");
        event.setAccidentType("교통사고");
        event.setStatus("요양급여신청됨");
        event.setApplyDt("2024-05-14");

        AssessmentApplication.applicationContext = applicationContext;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String msg = objectMapper.writeValueAsString(event);

            processor
                .inboundTopic()
                .send(
                    MessageBuilder
                        .withPayload(msg)
                        .setHeader(
                            MessageHeaders.CONTENT_TYPE,
                            MimeTypeUtils.APPLICATION_JSON
                        )
                        .setHeader("type", event.getEventType())
                        .build()
                );

            //then:

            Message<String> received = (Message<String>) messageCollector
                .forChannel(processor.outboundTopic())
                .poll();

            assertNotNull("Resulted event must be published", received);

            LOGGER.info("Response received: {}", received.getPayload());

            assertEquals(outputEvent.getId(), "1");
            assertEquals(outputEvent.getAccidentId(), "1");
            assertEquals(outputEvent.getBusinessCode(), "bc_1");
            assertEquals(outputEvent.getEmployeeId(), "user01");
            assertEquals(outputEvent.getAssessorId(), "1");
            assertEquals(outputEvent.getHospitalCode(), "hp_1");
            assertEquals(outputEvent.getDoctorNote(), "골절");
            assertEquals(outputEvent.getResults(), "N/A");
            assertEquals(outputEvent.getDate(), "2024-05-16");
            assertEquals(outputEvent.getComments(), "N/A");
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            assertTrue("exception", false);
        }
    }
}
