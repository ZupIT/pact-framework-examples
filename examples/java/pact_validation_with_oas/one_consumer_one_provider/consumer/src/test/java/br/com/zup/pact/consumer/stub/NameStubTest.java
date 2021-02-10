package br.com.zup.pact.consumer.stub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NameStubTest {

    @Test
    void getRandomFirstNameIsNotNullTest() {
        String firstName = NameStub.getRandomFirstName();
        assertNotNull(firstName);
    }

    @Test
    void getRandomLastNameIsNotNullTest() {
        String lastName = NameStub.getRandomLastName();
        assertNotNull(lastName);
    }
}
