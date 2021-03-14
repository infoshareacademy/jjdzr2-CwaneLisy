package com.infoshare.controller;


import com.infoshare.dto.CommentForm;
import com.infoshare.repository.CommentRepository;
import com.infoshare.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    public CommentController(CommentRepository commentRepository, CommentService commentService) {
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public CommentForm saveComment(@RequestBody CommentForm commentForm){
        return commentService.saveComment(commentForm);
    }

    @GetMapping("/{id}")
    public CommentForm findCommentById(@PathVariable UUID id){
        return commentService.findById(id);
    }

    @GetMapping
    public List<CommentForm> findAll(){
        return commentService.findAllComments();
    }

    @PutMapping("/{id}")
    public CommentForm edit(@PathVariable UUID id, @RequestBody CommentForm commentForm){
        return commentService.editComment(id,commentForm);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        commentService.deleteComment(id);
    }

//    @GetMapping("/{id}")
//    public String displayComment(@PathVariable UUID id, Model model){
//        Optional<CommentEntity> comment = commentService.getComment(id);
//        model.addAttribute("show", comment);
//        return "comment-view";
//    }

//    @GetMapping("/show-all-comments")
//    public String showAllComments(Model model){
//        model.addAttribute("comments",commentService.getAllComments());
//        return "all-comments";
//    }


//    @GetMapping("/add")
//    public String createComment(CommentEntity commentEntity, Model model) {
//        model.addAttribute("add", new CommentEntity());
//        commentRepository.save(commentEntity);
//        return "add-comment";
//    }

//    @PostMapping("submit")
//    public String updateComment(@Valid @ModelAttribute CommentEntity commentEntity, BindingResult br) {
//        if (br.hasErrors()) {
//            return "add-comment";
//        } else {
//            commentRepository.save(commentEntity);
//            return "redirect/comment-view";
//        }
//    }

}

