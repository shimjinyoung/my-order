package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import javax.persistence.*;

@Entity
@Table(name="ORDER_TABLE")
public class Order {
    @Id
    @GeneratedValue
    Long orderId;
    Long productId;
    int qty;
    String productName;

    @PostPersist  //라이프사이클 hook 설정 어노테이션
    public void eventPublish(){
        OrderChanged orderChanged = new OrderChanged();
        orderChanged.setOrderId(this.getOrderId());
        orderChanged.setProductId(this.getProductId());
        orderChanged.setProductName(this.getProductName());
        orderChanged.setQty(this.getQty());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(orderChanged);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }
        //System.out.println(json);

        Processor processor = DemoApplication.applicationContext.getBean(Processor.class);
        MessageChannel outputChannel = processor.output();

        outputChannel.send(MessageBuilder
                .withPayload(json)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
