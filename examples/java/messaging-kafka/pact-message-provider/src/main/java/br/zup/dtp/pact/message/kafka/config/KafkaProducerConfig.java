package br.zup.dtp.pact.message.kafka.config;

import br.zup.dtp.pact.message.model.Client;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

   @Value(value = "${kafka.bootstrapAddress}")
   private String bootstrapAddress;

   @Value(value = "${kafka.groupId}")
   private String groupId;


   @Bean
   public ProducerFactory<String, Client> producerFactory() {
      Map<String, Object> configProps = new HashMap<>();
      {
         configProps.put(
             ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
             bootstrapAddress);
         configProps.put(
             ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
             StringSerializer.class);
         configProps.put(
             ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
             JsonSerializer.class);
      }
      return new DefaultKafkaProducerFactory(configProps);
   }

   @Bean
   public KafkaTemplate<String, Client> kafkaTemplate() {
      return new KafkaTemplate<>(producerFactory());
   }

}
