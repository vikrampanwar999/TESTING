package com.example.websocket.conf;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
@EnableKafka
@ConditionalOnProperty(name="kafka.enabled")
public class KafkaConfig {

	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${kafka.consumer-topic-transaction}")
	private String orderTransaction;
	
	@Value("${kafka.consumer-topic-ordersuffix}")
	private String orderTopicSuffix;
	@Value("${kafka.consumer-topic-ExecutionInternal}")
	private String ExeInternal;
	@Value("${kafka.producer-topic-prefix-orderInternal}")
	private String orInternal;

	public String getOrInternal() {
		return orInternal;
	}

	public void setOrInternal(String orInternal) {
		this.orInternal = orInternal;
	}

	public String getExeInternal() {
		return ExeInternal;
	}

	public void setExeInternal(String exeInternal) {
		ExeInternal = exeInternal;
	}

	@Value("${kafka.group-id}")
	private String groupId;

	@Value("${kafka.auto-offset-reset}")
	private String autoOffsetReset;

	@Value("${kafka.consumer-topic-prefix}")
	private String consumerTopicPrefix;
	
	@Value("${kafka.producer-topic-prefix}")
	private String producerTopicPrefix;
	
	@Value("${kafka.key-deserializer}")
	private String keyDeserializer;
	
	@Value("${kafka.value-deserializer}")
	private String valueDeserializer;
	
	@Value("${kafka.key-serializer}")
	private String keySerializer;
	
	@Value("${kafka.value-serializer}")
	private String valueSerializer;
	
	public String getBootstrapServers() {
		return bootstrapServers;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getAutoOffsetReset() {
		return autoOffsetReset;
	}


	public Map<String, Object> consumerConfigs() throws ClassNotFoundException {
		Map<String, Object> props = new HashMap<>();
		// list of host:port pairs used for establishing the initial connections to the
		// Kafka cluster
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Class.forName(this.keyDeserializer));
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Class.forName(valueDeserializer));
		// allows a pool of processes to divide the work of consuming and processing
		// records
		//props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());

		// automatically reset the offset to the earliest offset
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);

		// Disable the auto commit
		// props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		
		System.out.println("Receiver configuration: " + props.toString());
		return props;
	}

	public ConsumerFactory<String, String> consumerFactory() throws ClassNotFoundException {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}
	
	public Map<String, Object> producerConfigs() throws ClassNotFoundException {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Class.forName(this.keySerializer));
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Class.forName(this.valueSerializer));

		System.out.println("Sender configuration: " + props.toString());
		return props;
	}

    public ProducerFactory<String, String> producerFactory() throws ClassNotFoundException {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() throws ClassNotFoundException {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        return kafkaTemplate;
    }



	public void setBootstrapServers(String bootstrapServers) {
		this.bootstrapServers = bootstrapServers;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setAutoOffsetReset(String autoOffsetReset) {
		this.autoOffsetReset = autoOffsetReset;
	}

	public String getConsumerTopicPrefix() {
		return consumerTopicPrefix;
	}

	public void setConsumerTopicPrefix(String consumerTopicPrefix) {
		this.consumerTopicPrefix = consumerTopicPrefix;
	}

	public String getProducerTopicPrefix() {
		return producerTopicPrefix;
	}

	public void setProducerTopicPrefix(String producerTopicPrefix) {
		this.producerTopicPrefix = producerTopicPrefix;
	}

	public String getorderTopicSuffix() {
		return orderTopicSuffix;
	}

	public void setorderTopicSuffix(String orderTopicSuffix) {
		this.orderTopicSuffix = orderTopicSuffix;
	}

	public String getOrderTransaction() {
		return orderTransaction;
	}

	public void setOrderTransaction(String orderTransaction) {
		this.orderTransaction = orderTransaction;
	}
	

}

