package com.mgr.api.controller;

import com.mgr.api.client.JsonPlaceholderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pub/test-feign")
public class TestFeignController {

    @Autowired
    private JsonPlaceholderClient jsonPlaceholderClient;

    @GetMapping("/posts")
    public List<Map<String, Object>> getAllPosts() {
        return jsonPlaceholderClient.getPosts();
    }

    @GetMapping("/posts/{id}")
    public Map<String, Object> getPost(@PathVariable Long id) {
        return jsonPlaceholderClient.getPostById(id);
    }
}
