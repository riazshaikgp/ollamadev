/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.co.fnb.cjp.sit;

import io.github.amithkoujalgi.ollama4j.core.OllamaAPI;
import io.github.amithkoujalgi.ollama4j.core.models.chat.OllamaChatMessageRole;
import io.github.amithkoujalgi.ollama4j.core.models.chat.OllamaChatRequestBuilder;
import io.github.amithkoujalgi.ollama4j.core.types.OllamaModelType;
import io.github.amithkoujalgi.ollama4j.core.utils.OptionsBuilder;
import io.github.amithkoujalgi.ollama4j.core.utils.PromptBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author riazs
 */
@Configuration
public class Configure {

    @Bean
    public OllamaAPI api(){
        OllamaAPI api = new OllamaAPI("http://asus.terratech.co.za:11434/");
        api.setVerbose(true);
        api.setRequestTimeoutSeconds(600000);
        return api;
    }
    
    @Bean
    public OptionsBuilder codebuilder(){
        return new OptionsBuilder().setMirostat(2).setMirostatEta(0.5f).setMirostatTau(0.4f).setNumCtx(4096).setNumPredict(-1);
    }
    
    
    public static OllamaChatRequestBuilder codeOnlyChatRequestBuilder(){
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(OllamaModelType.CODELLAMA);
        builder.withMessage(OllamaChatMessageRole.SYSTEM, 
                "You are an expert coder and understand different programming languages.\n"
                + "Given a question, answer ONLY with code.\n"
                + "There must be implementation code to solve the given problem.\n"
                + "Produce clean, formatted and indented code in markdown format.\n" 
                + "If there's any additional information you want to add, use comments within code.\n"
                + "The only additional information you will give is what the project structure will look like based on the code that is generated.\n"
                + "IMPORTANT RULES TO FOLLOW!!\n"
                + "DO NOT include ANY extra text apart from code. Follow this instruction very strictly!\n"       
                + "Show ALL files required for the project to build and run!\n"
                + "Ensure that good code layering like controllers DTO and service classes are created!\n"
                + "Show all package declarations and package imports.\n"
                + "Do NOT give an explanation for the code generated!");
        return builder;
    }
    
    public static OllamaChatRequestBuilder cmdOnlyChatRequestBuilder(){
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(OllamaModelType.LLAVA);
        builder.withMessage(OllamaChatMessageRole.SYSTEM, 
                "You are an expert coder and understand different programming languages.\n"
                + "Given a question, answer ONLY with a script to create the directory structure required for the project.\n"
                + "Produce clean, formatted and indented code in markdown format.\n"
                + "Your answer must be for " + System.getProperty("os.name")+ ".\n"
                + "Assume the script is running from within the project directory.\n"
                + "IMPORTANT RULES TO FOLLOW!!\n"
                + "DO NOT include ANY extra text apart from script to be used. Follow this instruction very strictly!\n"
                + "DO NOT give commands to create files\n"
                + "DO NOT try to use one command to create a folder and its sub-directories. Each directory must be created using a new command.");
        return builder;
    }
}
