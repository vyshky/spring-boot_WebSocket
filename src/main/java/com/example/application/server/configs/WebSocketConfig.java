package com.example.application.server.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // при нажатии кнопки send отправляеть запрос в /server/get/hello.
        // Можно сократить до /server, тогда он будет коннектится(выполнять) со множеством методов, которые начинаются server
        // с помощью этой настройки можно скрывать админские панели, меню, кнопки
        // предоставляет доступ клиентскому приложению к методам сервера
        config.enableSimpleBroker("/server/get/hello"); // будет вызван только greeting
        // config.enableSimpleBroker("/server"); // будут вызваны все методы начинающиеся с /server
        // config.setApplicationDestinationPrefixes("/prefix");                  // добавляет префикс для запроса от клиента вместо /send/hello/name будет /prefix/send/hello/name (в app.js)
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/serverWebSocket").withSockJS();  // создается некий WebSocket для подключения по ендпоинту /serverWebSocket (в app.js)
    }
}