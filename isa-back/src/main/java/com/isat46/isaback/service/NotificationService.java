package com.isat46.isaback.service;

import com.isat46.isaback.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final ApplicationContext context;

    @Autowired
    public NotificationService(ApplicationContext context) {
        this.context = context;
    }

    public void sendNoInventoryMessage(Contract contract) {
        String topic = "inventory/low";
        String payload = "The inventory for equipment " + contract.getEquipment() + " is low.";
        getMqttPublisherService().publish(topic, payload);
    }

    public void sendSuccessfulDeliveryMessage(Contract contract) {
        String topic = "delivery/success";
        String payload = "The equipment " + contract.getEquipment() + " has been successfully delivered.";
        getMqttPublisherService().publish(topic, payload);
    }

    private MqttPublisherService getMqttPublisherService() {
        return context.getBean(MqttPublisherService.class);
    }
}