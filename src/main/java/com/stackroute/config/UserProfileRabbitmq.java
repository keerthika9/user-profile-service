package com.stackroute.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class UserProfileRabbitmq {
  /*-----------------registration------------------------------ */
  @Value("${userProfileRegister.rabbitmq.queue}")
  String queueName;

  @Value("${userProfileRegister.rabbitmq.exchange}")
  String exchange;

  @Value("${userProfileRegister.rabbitmq.routingkey}")
  String routingkey;

  @Bean
  Queue queueRegister() {
    return new Queue(queueName, true);
  }

  @Bean
  TopicExchange exchangeRegister() {
    return new TopicExchange(exchange);
  }

  @Bean
  Binding bindingRegister(Queue queueRegister, TopicExchange exchangeRegister) {
    return  BindingBuilder.bind(queueRegister).to(exchangeRegister).with(routingkey);
  }

  /* -----------------profile------------------- */
  @Value("${userprofileProfile.rabbitmq.queue}")
  String profileQueueName;

  @Value("${userprofileProfile.rabbitmq.exchange}")
  String profileExchange;

  @Value("${userprofileProfile.rabbitmq.routingkey}")
  String profilRoutingkey;


  @Bean
  Queue queueProfile() {
    return new Queue(profileQueueName, true);
  }

  @Bean
  TopicExchange exchangeProfile() {
    return new TopicExchange(profileExchange);
  }

  @Bean
  Binding bindingProfile(Queue queueProfile, TopicExchange exchangeProfile) {
    return  BindingBuilder.bind(queueProfile).to(exchangeProfile).with(profilRoutingkey);
  }
  /*--------------------------------------------------------------------------------------------------*/

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  ConnectionFactory connectionFactory() {
    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
    cachingConnectionFactory.setUsername("guest");
    cachingConnectionFactory.setPassword("guest");
    return cachingConnectionFactory;
  }


  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

}


