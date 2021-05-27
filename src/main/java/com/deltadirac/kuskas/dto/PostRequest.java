package com.deltadirac.kuskas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long id;
    private String title;
    private String url;
    private String content;
    private Long forumId;
}
