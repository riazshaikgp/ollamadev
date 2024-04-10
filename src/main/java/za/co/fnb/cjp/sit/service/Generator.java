/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.co.fnb.cjp.sit.service;

import io.github.amithkoujalgi.ollama4j.core.OllamaAPI;
import io.github.amithkoujalgi.ollama4j.core.exceptions.OllamaBaseException;
import io.github.amithkoujalgi.ollama4j.core.models.chat.OllamaChatMessageRole;
import io.github.amithkoujalgi.ollama4j.core.models.chat.OllamaChatRequestBuilder;
import io.github.amithkoujalgi.ollama4j.core.models.chat.OllamaChatRequestModel;
import io.github.amithkoujalgi.ollama4j.core.models.chat.OllamaChatResult;
import io.github.amithkoujalgi.ollama4j.core.types.OllamaModelType;
import io.github.amithkoujalgi.ollama4j.core.utils.OptionsBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.fnb.cjp.sit.Configure;

/**
 *
 * @author F3434591
 */
@Service
public class Generator {
    @Autowired
    OllamaAPI api;
    String answer;
    
    public OllamaChatResult prompt(String prompt) throws IOException, OllamaBaseException, InterruptedException, URISyntaxException{
        api.setVerbose(true);
        OllamaChatRequestModel request = Configure.codeOnlyChatRequestBuilder().withMessage(OllamaChatMessageRole.USER, prompt).build();
        OllamaChatResult result = api.chat(request);
        answer = result.getResponse();
        return result;
    }
    
    public OllamaChatResult directories() throws IOException, OllamaBaseException, InterruptedException, URISyntaxException{
        api.setVerbose(true);
        
                
        String prompt = "Given the following code and wanting to create this project on a " + System.getProperty("os.name") + " system what would be the commands to create a directory structure for this project using only the command line. " + answer;
        OllamaChatRequestModel request = Configure.cmdOnlyChatRequestBuilder().withMessage(OllamaChatMessageRole.USER, prompt).build();
        return api.chat(request);
    }
    
    public OllamaChatResult general(String systemprompt, String userprompt, String file) throws IOException, OllamaBaseException, InterruptedException, URISyntaxException{
        api.setVerbose(true);
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(OllamaModelType.LLAVA);
        System.out.println(systemprompt);
        System.out.println(userprompt);
        builder.withMessage(OllamaChatMessageRole.SYSTEM, systemprompt);
        List<File> images = new ArrayList();
        images.add(new File(file));
        OllamaChatRequestModel request = builder.withMessage(OllamaChatMessageRole.USER, userprompt, images).build();
        return api.chat(request);
    }

}
