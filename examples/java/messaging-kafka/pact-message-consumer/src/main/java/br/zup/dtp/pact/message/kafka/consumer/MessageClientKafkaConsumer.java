package br.zup.dtp.pact.message.kafka.consumer;

import static br.zup.dtp.pact.message.kafka.config.KafkaTopicConfig.TOPIC_CLIENT;
import static br.zup.dtp.pact.message.kafka.config.KafkaTopicConfig.TOPIC_STRING;
import static org.springframework.kafka.support.KafkaHeaders.GROUP_ID;

import br.zup.dtp.pact.message.model.Client;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@EnableKafka
@Component
public class MessageClientKafkaConsumer {

   ObjectMapper mapper = new ObjectMapper();
   {
      mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
      mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
      mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
   }


   @KafkaListener(topics = TOPIC_CLIENT, containerFactory = "listenerContainerFactory", groupId = GROUP_ID)
   public Client clientListener(Client client) throws ValidationException {
      log.info("Consuming Client Message: "+client);
      validate(client);
      return client;
   }

   @KafkaListener(topics = { TOPIC_STRING },groupId = GROUP_ID)
   public Client stringListener(String message) throws ValidationException {
      log.info("Consuming String message: "+message);
      try {
         Client client = mapper.readValue(message, Client.class);
         validate(client);
         return client;
      } catch (Exception ex){
         throw new ValidationException("Error to deserialize and validate message as Client payload",ex);
      }
   }


   public void validate(Client client) throws ValidationException {
      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      Validator validator = factory.getValidator();

      Set<ConstraintViolation<Client>> violations = validator.validate(client);
      if(violations.size() > 0){
         violations.stream().forEach(v -> log.error(v.getMessage()));
         throw new ValidationException("Error to read Client Message: "+client.toString());
      }
   }

}
