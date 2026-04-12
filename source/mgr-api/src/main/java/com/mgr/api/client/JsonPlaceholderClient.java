package com.mgr.api.client;

import com.mgr.api.config.CustomFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "jsonPlaceholder", url = "https://jsonplaceholder.typicode.com", configuration = CustomFeignConfig.class)
public interface JsonPlaceholderClient {

    @GetMapping("/posts")
    List<Map<String, Object>> getPosts();

    @GetMapping("/posts/{id}")
    Map<String, Object> getPostById(@PathVariable("id") Long id);
}
