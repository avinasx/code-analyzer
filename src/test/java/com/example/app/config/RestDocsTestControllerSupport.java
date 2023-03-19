package com.example.app.config;

import com.example.app.config.properties.AppUriPropConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.templates.TemplateFormats;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest
@EnableConfigurationProperties(AppUriPropConfig.class)
@Import(RestDocsTestConfig.class)
@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
@Slf4j
public class RestDocsTestControllerSupport {
    @Autowired
    protected AppUriPropConfig uriPropConfig;
    @Autowired
    protected RestDocumentationResultHandler restDocsHandler;
    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;
    protected String serverUrl;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider provider) {
        this.serverUrl = String.format("%s://%s:%d", uriPropConfig.getScheme(),
                uriPropConfig.getHost(), uriPropConfig.getPort());
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider)
                        .snippets().withEncoding("UTF-8"))
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider)
                        .snippets().withTemplateFormat(TemplateFormats.asciidoctor()))
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider)
                        .uris().withScheme(uriPropConfig.getScheme())
                        .withHost(uriPropConfig.getHost()).withPort(uriPropConfig.getPort()))
                .alwaysDo(this.restDocsHandler)
                .build();
    }

    @AfterEach
    void tearDown() {
    }
}
