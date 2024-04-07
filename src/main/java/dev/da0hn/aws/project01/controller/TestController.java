package dev.da0hn.aws.project01.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {

  @GetMapping("/dog/{name}")
  public ResponseEntity<?> dogTest(@PathVariable String name) {
    log.info("m=dogTest(name={})", name);

    return ResponseEntity.ok("name: " + name);
  }

}
