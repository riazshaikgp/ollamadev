/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.co.fnb.cjp.sit.controller;

import io.github.amithkoujalgi.ollama4j.core.exceptions.OllamaBaseException;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import za.co.fnb.cjp.sit.model.GeneralPrompt;
import za.co.fnb.cjp.sit.service.Generator;

/**
 *
 * @author F3434591
 */
@RestController
public class Prompt {
    @Autowired
    Generator gen;
    
    @GetMapping(path = "prompt")
    public ResponseEntity<String> codeprompt(String prompt) throws IOException, OllamaBaseException, InterruptedException, URISyntaxException{
        return ResponseEntity.ok(gen.prompt(prompt).getResponse());
    }

    @GetMapping(path = "directories")
    public ResponseEntity<String> directories() throws IOException, OllamaBaseException, InterruptedException, URISyntaxException{
        return ResponseEntity.ok(gen.directories().getResponse());
    } 
    
    @PostMapping(path = "generalprompt")
    public ResponseEntity<String> generalprompt(@RequestBody GeneralPrompt prompts) throws IOException, OllamaBaseException, InterruptedException, URISyntaxException{
        return ResponseEntity.ok(gen.general(prompts.getSystemprompt(), prompts.getUserprompt(), prompts.getImage()).getResponse());
    } 
    
}

