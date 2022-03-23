package io.github.antolius.jackson.record;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassTest {

    public static final class Request {

        @JacksonXmlElementWrapper(localName = "messages")
        @JacksonXmlProperty(localName = "message")
        private final List<Message> messages;

        private Request() {
            this.messages = null;
        }

        public Request(List<Message> messages) {
            this.messages = messages;
        }

        public List<Message> getMessages() {
            return messages;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Request request)) return false;
            return Objects.equals(messages, request.messages);
        }

        @Override
        public int hashCode() {
            return Objects.hash(messages);
        }

        @Override
        public String toString() {
            return "Request{" +
                    "messages=" + messages +
                    '}';
        }
    }

    public static final class Message {
        private final String text;

        private Message() {
            this.text = null;
        }

        public Message(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Message)) return false;
            Message message = (Message) o;
            return Objects.equals(text, message.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }

        @Override
        public String toString() {
            return "Message{" +
                    "text='" + text + '\'' +
                    '}';
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
