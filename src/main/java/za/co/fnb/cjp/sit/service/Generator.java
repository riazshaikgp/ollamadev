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
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author F3434591
 */
@Service
public class Generator {
    @Autowired
    OllamaAPI api;
    @Autowired
    OllamaChatRequestBuilder builder;
    
    @Autowired
    OptionsBuilder codebuilder;
    @Autowired
    OllamaChatRequestBuilder codeprompt;
    String answer;
    
    public OllamaChatResult prompt(String prompt) throws IOException, OllamaBaseException, InterruptedException, URISyntaxException{
        api.setVerbose(true);
        OllamaChatRequestModel request = codeprompt.withMessage(OllamaChatMessageRole.USER, prompt).build();
        OllamaChatResult result = api.chat(request);
        answer = result.getResponse();
        return result;
    }
    
    public OllamaChatResult directories() throws IOException, OllamaBaseException, InterruptedException, URISyntaxException{
        api.setVerbose(true);
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(OllamaModelType.CODELLAMA);
        builder.withMessage(OllamaChatMessageRole.SYSTEM, 
                "You are an expert coder and understand different programming languages. "
                + "Given a question, answer ONLY with commands required to create the directory structure required for the project. "
                + "Produce clean, formatted and indented code in markdown format. " 
                + "Your answer must be for " + System.getProperty("os.name")
                + ". IMPORTANT RULES TO FOLLOW!! "
                + "DO NOT include ANY extra text apart from commands to be used. Follow this instruction very strictly! "
                + "Do NOT give an answer which expects the user to copy and paste code into files! "
                + "The commands given must create each files full content as well."
                + "Use echo or an equivalent for the operating system to output each line of code to the respective file. Do not wrap multiple lines with one output command."
                + "Ensure the commands are properly escaped for the relevant operating system so that they will successfully create the files with the correct code.");
                
        String prompt = "Given the following code and wanting to create this project on a " + System.getProperty("os.name") + " system what would be the commands to create a directory structure for this project and insert all the relevant code into the respective files using only the command line. " + answer;
        OllamaChatRequestModel request = builder.withMessage(OllamaChatMessageRole.USER, prompt).build();
        return api.chat(request);
    }

}
