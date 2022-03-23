package io.github.antolius.jackson.record;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordTest {

public static record Request(
        @JacksonXmlElementWrapper(localName = "messages")
        @JacksonXmlProperty(localName = "message")
        List<Message> messages
) {
    public Request {
    }

    private Request() {
        this(null);
    }
}

public static record Message(String text) {
    public Message {
    }

    private  Message() {
        this(null);
    }
}

    private final ObjectMapper mapper = new XmlMapper();

    @Test
    void shouldSerialize() throws Exception {
        var givenRequest = new Request(List.of(new Message("given text")));
        var actualXml = mapper.writeValueAsString(givenRequest);
        assertEquals(
                "<Request><messages><message><text>given text</text></message></messages></Request>",
                actualXml
        );
    }

    @Test
    void shouldDeserialize() throws Exception {
        var givenXml = "<Request><messages><message><text>given text</text></message></messages></Request>";
        var actualRequest = mapper.readValue(givenXml, Request.class);
        assertEquals(new Request(List.of(new Message("given text"))), actualRequest);
    }

}
