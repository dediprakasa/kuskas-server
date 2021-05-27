package com.deltadirac.kuskas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private Long forumId;
    private String title;
    private String url;
    private String content;
    private String username;
}
