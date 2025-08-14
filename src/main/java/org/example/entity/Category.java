package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Integer id;
    private Integer userId;
    private String categoryName;
    private List<Expence> expencesList;
}
