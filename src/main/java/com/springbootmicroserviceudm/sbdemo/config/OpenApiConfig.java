package com.springbootmicroserviceudm.sbdemo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.web.bind.annotation.RestController;

@RestController
@OpenAPIDefinition(info =
@Info(
        title = "SB Demo",
        version = "1.0",
        description = "Documentation for our SBDemo API Application",
        license = @License(name = "Apache 2.0", url = "http://foo.bar"),
        contact = @Contact(url = "https://www.abc.com", name = "Abc", email = "abc@gmail.com")
)
)
public class OpenApiConfig {
}
