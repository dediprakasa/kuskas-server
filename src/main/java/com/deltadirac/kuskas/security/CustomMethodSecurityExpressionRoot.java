package com.deltadirac.kuskas.security;

import com.deltadirac.kuskas.exception.PostNotFoundException;
import com.deltadirac.kuskas.model.Post;
import com.deltadirac.kuskas.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private PostRepository postRepository;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean isAuthor(Long postId) {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));

        return principal.getUsername().equals(post.getUser().getUsername());
    }

    @Override
    public void setFilterObject(Object filterObject) {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getFilterObject() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getReturnObject() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getThis() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

}
