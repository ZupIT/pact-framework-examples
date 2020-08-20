package br.zup.dtp.pact.message.kafka.producer;


import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.AmpqTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import br.zup.dtp.pact.message.model.Client;
import br.zup.dtp.pact.message.utils.MessageGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Slf4j
@Provider("ClientCreationMessageProvider")
@PactBroker(host = "localhost", port = "80")
public class KafkaContractAsyncTest {


   static ObjectMapper mapper = new ObjectMapper();
   static {
      mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
      mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
      mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
   }

   @TestTemplate
   @ExtendWith(PactVerificationInvocationContextProvider.class)
   void testTemplateInteraction(Pact pact, Interaction interaction, PactVerificationContext context) {
      log.info("TestTemplate called: " + pact.getProvider().getName() + ", " + interaction);
      context.verifyInteraction();
   }

   @BeforeEach
   void before(PactVerificationContext context) {
      System.setProperty("pact.verifier.publishResults", "true");
      context.setTarget(new AmpqTestTarget());
   }

   @State("ClientProviderState")
   public void someProviderState() {
      log.info("ClientProviderState CallBack Json");
   }

   @State("ClientProviderState2")
   public void someProviderState2() {
      log.info("ClientProviderState CallBack Map");
   }


   @PactVerifyProvider("Client Created Message")
   public String verifyMessageForClientCreation() {

      try {
         Client client = MessageGenerator.generateCLients(1).get(1);

         String body = mapper.writeValueAsString(client);

         return body;
      } catch (JsonProcessingException e) {
         e.printStackTrace();
         return null;
      }
   }

   @PactVerifyProvider("ClientProviderState CallBack Json")
   public String verifyMessageForClientCreationJson() {
      try {
         Client client = MessageGenerator.generateCLients(1).get(1);

         String body = mapper.writeValueAsString(client);

         return body;
      } catch (JsonProcessingException e) {
         e.printStackTrace();
         return null;
      }

   }

   @PactVerifyProvider("ClientProviderState CallBack Map")
   public String verifyMessageForClientCreationMap() {
      try {
         Client client = MessageGenerator.generateCLients(5).get(3);

         String body = mapper.writeValueAsString(client);

         return body;
      } catch (JsonProcessingException e) {
         e.printStackTrace();
         return null;
      }
   }

}