package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expence {
    private Integer id;
    private Integer categoryId;
    private Integer userId;
    private String description;
    private Double amount;
}
