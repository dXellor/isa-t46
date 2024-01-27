package com.isat46.isaback.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttService {

    @Autowired
    private IMqttClient mqttClient;

    @Value("${pos.sim.command.delay}")
    public Integer delay;

    @Value("${pos.sim.command.topic}")
    public String MQTT_COMMAND_TOPIC;
    protected final Log LOGGER = LogFactory.getLog(getClass());

    public void publish(String topic, String payload){
        MqttMessage message = new MqttMessage();
        message.setPayload(payload.getBytes());
        message.setQos(2);
        message.setRetained(true);

        try{
            mqttClient.publish(topic, message);
            LOGGER.info("Mqtt message published on the topic: " + topic);
        } catch (MqttException ex){
            LOGGER.error("Mqtt publishing error: " + ex);
        }
    }
}
