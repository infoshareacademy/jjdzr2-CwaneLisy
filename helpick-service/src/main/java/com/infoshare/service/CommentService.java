package com.infoshare.service;

import com.infoshare.domain.CommentEntity;
import com.infoshare.dto.CommentForm;
import com.infoshare.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.infoshare.functions.CommentFunction.commentEntityToCommentForm;
import static com.infoshare.functions.CommentFunction.commentFormToCommentEntity;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentForm saveComment(CommentForm commentForm){
        CommentEntity commentEntity = commentFormToCommentEntity.apply(commentForm);
        return commentEntityToCommentForm.apply(commentRepository.save(commentEntity));
    }

    @Transactional
    public CommentForm findById(UUID id){
        return commentRepository.findById(id).map(commentEntityToCommentForm)
                .orElseThrow();
    }

    @Transactional
    public CommentForm editComment(UUID id, CommentForm commentForm){
        return commentRepository.findById(id)
                .map(commentEntity -> {
                    commentEntity.setUserName(commentForm.getUsername());
                    commentEntity.setText(commentForm.getText());
                    return commentRepository.save(commentEntity);
                })
                .map(commentEntityToCommentForm)
                .orElseThrow();
    }

    @Transactional
    public void deleteComment(UUID id){
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow();
        commentRepository.delete(commentEntity);
    }

    @Transactional
    public List<CommentForm> findAllComments(){
        return commentRepository.findAll().stream().map(commentEntityToCommentForm).collect(Collectors.toList());
    }
}

