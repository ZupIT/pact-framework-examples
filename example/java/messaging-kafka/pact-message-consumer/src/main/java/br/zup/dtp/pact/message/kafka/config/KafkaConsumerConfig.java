package br.zup.dtp.pact.message.kafka.config;

import br.zup.dtp.pact.message.model.Client;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

   @Value(value = "${kafka.bootstrapAddress}")
   private String bootstrapAddress;

   @Value(value = "${kafka.groupId}")
   private String groupId;

   Map<String, Object> config = new HashMap<>();

   @PostConstruct
   public void setUP() {
      config.put(
          ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
          bootstrapAddress);
      config.put(
          ConsumerConfig.GROUP_ID_CONFIG,
          groupId);
      config.put(
          ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
          StringDeserializer.class);
      config.put(
          ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
          StringDeserializer.class);
   }

   @Bean(name = "consumerFactory")
   @ConditionalOnMissingBean(name = "listenerContainerFactory")
   public ConsumerFactory<String, Client> consumerFactory() {
      return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(Client.class));
   }

   @Bean(name = "consumerFactoryObject")
   public ConsumerFactory<Object, Object> consumerFactoryObject() {
      return new DefaultKafkaConsumerFactory<>(config);
   }

   @Bean(name = "listenerContainerFactory")
   public ConcurrentKafkaListenerContainerFactory<String, Client> listenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, Client> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());
      return factory;
   }

}
