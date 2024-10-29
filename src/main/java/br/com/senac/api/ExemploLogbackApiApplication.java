package br.com.senac.api;

import jakarta.jms.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@SpringBootApplication
public class ExemploLogbackApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExemploLogbackApiApplication.class, args);
	}

	// Os método Bean abaixo definem como as mensagens vão ser trafegadas no ActiveMQ, instancia automaticamente
	@Bean
	public JmsListenerContainerFactory<?> defaultFactory(
			ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {

		DefaultJmsListenerContainerFactory factory =
				new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	// Tenta interpretar automaticamente as mensagens JSON, convertendo-as para um objeto
	@Bean
	public MappingJackson2MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter =
				new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

}


