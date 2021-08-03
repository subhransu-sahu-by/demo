package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
@Data
@AllArgsConstructor
@Builder
public class OrderKey implements Serializable {
    private String orderId;
}
