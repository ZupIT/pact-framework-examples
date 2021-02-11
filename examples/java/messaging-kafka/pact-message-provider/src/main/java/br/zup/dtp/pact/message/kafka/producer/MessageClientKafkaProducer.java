package br.zup.dtp.pact.message.kafka.producer;


import static br.zup.dtp.pact.message.kafka.config.KafkaTopicConfig.TOPIC_CLIENT;

import br.zup.dtp.pact.message.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@EnableKafka
@Component
public class MessageClientKafkaProducer {

   @Value(value = "${kafka.bootstrapAddress}")
   private String bootstrapAddress;

   @Value(value = "${kafka.groupId}")
   private String groupId;

   @Autowired
   KafkaTemplate<String, Client> kafkaClientTemplate;

   public void sendMessageClientCreation(Client client) {

      ListenableFuture<SendResult<String, Client>> future = kafkaClientTemplate.send(TOPIC_CLIENT, client);

      future.addCallback(new ListenableFutureCallback<SendResult<String, Client>>() {

         @Override
         public void onSuccess(SendResult<String, Client> result) {
            log.info("Sent create Client Message = [" + client +
                     "] with offset=[" + result.getRecordMetadata().offset() + "]");
         }

         @Override
         public void onFailure(Throwable ex) {

            log.error("Unable to send create Client Message = [" + client + "] due to : " + ex.getMessage(), ex);
         }
      });
   }

}
