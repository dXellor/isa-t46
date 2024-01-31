package com.isat46.isaback.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.contract.ContractDto;
import com.isat46.isaback.service.CompanyService;
import com.isat46.isaback.service.ContractService;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class MqttMessageHandler implements MqttCallback {

    private final CompanyService companyService;
    private final ContractService contractService;
    private IMqttClient mqttClient;

    public MqttMessageHandler(CompanyService companyService, ContractService contractService) {
        this.companyService = companyService;
        this.contractService = contractService;
    }

    public void setMqttClient(IMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void connectionLost(Throwable cause) { }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (topic.equals("getCompanies")) {
            try {
                Pageable pageable = PageRequest.of(0, 100);
                Page<CompanyDto> companiesPage = companyService.findAllPaged(pageable);

                ObjectMapper mapper = new ObjectMapper();
                // jer zasto bi datumi radili kako treba 
                mapper.registerModule(new JavaTimeModule());
                String responseJson =mapper.writeValueAsString(companiesPage);
                System.out.println("sent " + responseJson);

                mqttClient.publish("companies", new MqttMessage(responseJson.getBytes()));
            } catch (Exception e) {
                System.out.println("Exception occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
        else if(topic.equals("contract")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                ContractDto contractDto = mapper.readValue(message.toString(), ContractDto.class);


                contractService.createContract(contractDto);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) { }
}