package com.example.demo.config;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.PathProvider;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ImplicitGrant;
import springfox.documentation.service.LoginEndpoint;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author DeepakKumar
 * Class to generate swagger documentation
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    public static final String GOOGLE_ID_TOKEN = "google_id_token";
    private static final String GOOGLE_API_KEY = "api_key";
    private static final String AUTH_SCHEME = "auth_scheme";

    @Autowired
    public SwaggerConfig() {
    }

   /* @Bean
    public Docket api(PathProvider pathProvider) {
        List<SecurityContext> securityContexts = Collections.singletonList(getSecurityContext());
        List<SecurityScheme> securitySchemes = Collections.singletonList(getSecurityScheme());
        Docket retval = new Docket(DocumentationType.SWAGGER_2)
                .pathProvider(pathProvider)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/springboot/demo/v1.*"))
                .build()
                .securityContexts(securityContexts)
                .securitySchemes(securitySchemes).
                apiInfo(apiInfo());
        logger.debug(String.format("Loaded Swagger %s", securitySchemes));
        return retval;
    }*/
    
    //without security 
    @Bean
    public Docket api(PathProvider pathProvider) {
        Docket retval = new Docket(DocumentationType.SWAGGER_2)
                .pathProvider(pathProvider)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/springboot/demo/v1.*"))
                .build()
                .apiInfo(apiInfo());
        return retval;
    }

    @Bean
    public PathProvider pathProvider() {
        return new AbstractPathProvider() {
            @Override
            protected String applicationPath() {
                return "//";
            }

            @Override
            protected String getDocumentationPath() {
                return "//";
            }
        };
    }

    private SecurityContext getSecurityContext() {
        return SecurityContext
                .builder()
                .securityReferences(Collections.singletonList(getSecurityReference()))
                .forPaths(PathSelectors.regex("/springboot/demo/v1(?!/health).*"))
                .build();
    }

    private SecurityReference getSecurityReference() {
        if(GOOGLE_API_KEY.equalsIgnoreCase(System.getProperty(AUTH_SCHEME))) {
            return new SecurityReference(GOOGLE_API_KEY, new AuthorizationScope[0]);
        }
        return new SecurityReference(GOOGLE_ID_TOKEN, new AuthorizationScope[0]);
    }

    private SecurityScheme getSecurityScheme() {
        if(GOOGLE_API_KEY.equalsIgnoreCase(System.getProperty(AUTH_SCHEME))) {
            return new ApiKey(GOOGLE_API_KEY, "key", "query");
      }
      return new OAuth(
              GOOGLE_ID_TOKEN,
              Collections.emptyList(),
              Collections.singletonList(new ImplicitGrant(new LoginEndpoint(""), GOOGLE_ID_TOKEN)),
              Collections.singletonList(new StringVendorExtension("x-google-issuer", "https://accounts.google.com"))
      );
  }
    
    ApiInfo apiInfo() {
        String title = "GCP service Account Authentication";
        if (GOOGLE_API_KEY.equalsIgnoreCase(System.getProperty(AUTH_SCHEME))) {
            title = "GCP API key Aunthentication";
        }
        return new ApiInfoBuilder()
                .title(title)
                .description(title)
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .build();
    }

}
