package com.infoshare.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name= "user_name")
  //  @NotBlank(message = "pole musi byc wypelnione")
    private String userName;


    @Column(name= "text")
  //  @NotBlank(message = "pole musi byc wypelnione")
    private String text;

    public CommentEntity(String text,String userName) {
        this.text = text;
        this.userName= userName;
    }

//    public static CommentEntity CommentEntity(CommentEntity commentEntity){
//        return new CommentEntity(commentEntity.getText(), commentEntity.getUserName());
//    }
}
