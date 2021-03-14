package com.infoshare.functions;

import com.infoshare.domain.CommentEntity;
import com.infoshare.dto.CommentForm;

import java.util.function.Function;

public class CommentFunction {

    public static final Function<CommentEntity, CommentForm> commentEntityToCommentForm = commentEntity ->
            new CommentForm(
                    commentEntity.getId(),
                    commentEntity.getUserName(),
                    commentEntity.getText()
            );
    public static final Function<CommentForm,CommentEntity> commentFormToCommentEntity = commentForm ->
            new CommentEntity(
                    commentForm.getUsername(),
                    commentForm.getText()
            );
}
