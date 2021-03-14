package com.infoshare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentForm {

    private UUID id;

    //@NotBlank
    private String username;

   // @NotBlank
    private String text;
}
