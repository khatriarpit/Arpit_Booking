package com.emxcel.dms.core.business.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class ApplicationWebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

	/*@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic/");
		config.setApplicationDestinationPrefixes("/app");
	}*/

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
	    /*.setHandshakeHandler(new CurrentLoggeInUserHandshakeHandler())*/
		registry.addEndpoint("/ws").withSockJS();
	}

	/*class CurrentLoggeInUserHandshakeHandler extends DefaultHandshakeHandler {
		@Override
		protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
				Map<String, Object> attributes) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = user.getUsername();
			return new UsernamePasswordAuthenticationToken(username, null);
		}
	}*/
}
