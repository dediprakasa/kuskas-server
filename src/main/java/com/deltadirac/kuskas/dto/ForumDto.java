package com.deltadirac.kuskas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForumDto {
    private Long id;
    private String name;
    private String description;
    private Integer postCount;
}
