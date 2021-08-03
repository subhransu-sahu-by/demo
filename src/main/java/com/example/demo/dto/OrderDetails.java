package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.ZonedDateTime;
@Data
@Builder
@Document(collection = "order1")
public class OrderDetails implements Serializable {
    private String orgId;
    private String sellingChannel;
    @Id
    private OrderKey orderKey;
    private String sellerOrgId;
    private String buyerOrgId;
    private String partialFillAllowed;
    private String cartId;
    private String orderType;
    private ZonedDateTime orderDate;
}
