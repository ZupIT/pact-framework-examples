package br.zup.dtp.pact.message.kafka.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

@EnableKafka
@Configuration
public class KafkaTopicConfig {

   public static final String TOPIC_CLIENT = "client_creation";

   public static final String TOPIC_STRING = "string_topic";

   @Value(value = "${kafka.bootstrapAddress}")
   private String bootstrapAddress;

   @Bean
   public KafkaAdmin kafkaAdmin() {
      Map<String, Object> configs = new HashMap<>();
      configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
      return new KafkaAdmin(configs);
   }

   @Bean
   public NewTopic topicClient() {
      return new NewTopic(TOPIC_CLIENT, 1, (short) 1);
   }

   @Bean
   public NewTopic topicString() {
      return new NewTopic(TOPIC_STRING, 1, (short) 1);
   }

}
