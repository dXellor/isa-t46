package com.isat46.isaback.service;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherService {

    private final IMqttClient mqttClient;

    @Autowired
    public MqttPublisherService(IMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public void publish(String topic, String payload) {
        MqttMessage msg = new MqttMessage(payload.getBytes());
        msg.setQos(2);
        msg.setRetained(false);
        try {
            mqttClient.publish(topic, msg);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}