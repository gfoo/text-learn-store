package org.gfoo.textlearnstore;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TextLearnStoreApplication {

	@Value("${amqp.text-learn.queue}")
	private String queueName;

	@Value("${amqp.text-learn.exchange}")
	private String exchangeName;

	@Value("${amqp.text-learn.routing.learn}")
	private String learnRoutingKeyName;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(exchangeName);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(learnRoutingKeyName);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	    MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter learnlistenerAdapter(LearnReceiver receiver) {
		return new MessageListenerAdapter(receiver, LearnReceiver.RECEIVE_METHOD);
	}

	public static void main(String[] args) {
		SpringApplication.run(TextLearnStoreApplication.class, args);
	}

}
