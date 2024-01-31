package com.isat46.isaback.config;


import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Configuration
public class MqttConfig {

    private final MqttMessageHandler mqttMessageHandler;

    public MqttConfig(MqttMessageHandler mqttMessageHandler) {
        this.mqttMessageHandler = mqttMessageHandler;
    }
    @Bean
    @ConfigurationProperties(prefix = "mqtt")
    public MqttConnectOptions mqttConnectOptions() {
        return new MqttConnectOptions();
    }


    @Bean
    public IMqttClient mqttClient(@Value("${mqtt.clientId}") String clientId,
                                  @Value("#{environment['mosquitto.host'] ?: '127.0.0.1'}") String hostname, @Value("${mqtt.port}") int port) throws MqttException {

        IMqttClient mqttClient = new MqttClient("tcp://" + hostname + ":" + port, clientId);
        mqttClient.connect(mqttConnectOptions());

        mqttMessageHandler.setMqttClient(mqttClient);
        mqttClient.setCallback(mqttMessageHandler);

        mqttClient.subscribe("getCompanies");
        mqttClient.subscribe("contract");
        return mqttClient;
    }
}

