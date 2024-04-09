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
        OllamaAPI api = new OllamaAPI("http://172.27.254.49:11434/");
        api.setVerbose(true);
        api.setRequestTimeoutSeconds(600000);
        return api;
    }
    
    @Bean
    public OptionsBuilder codebuilder(){
        return new OptionsBuilder().setMirostat(2).setMirostatEta(0.5f).setMirostatTau(0.4f).setNumCtx(4096).setNumPredict(-1);
    }
    
    @Bean
    public OllamaChatRequestBuilder codeOnlyChatRequestBuilder(){
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(OllamaModelType.CODELLAMA);
        builder.withMessage(OllamaChatMessageRole.SYSTEM, 
                "You are an expert coder and understand different programming languages. "
                + "Given a question, answer ONLY with code. "
                + "Produce clean, formatted and indented code in markdown format. " 
                + "If there's any additional information you want to add, use comments within code. "
                + "The only additional information you will give is what the project structure will like based on the code that is generated. "
                + "IMPORTANT RULES TO FOLLOW!! "
                + "DO NOT include ANY extra text apart from code. Follow this instruction very strictly! "
                + "Show ALL files required for the project to build and run! "
                + "Ensure that good code layering like controllers DTO and service classes are created! "
                + "Show all package declarations and package imports. "
                + "Do NOT give an explanation for the code generated!");
        return builder;
    }
}
