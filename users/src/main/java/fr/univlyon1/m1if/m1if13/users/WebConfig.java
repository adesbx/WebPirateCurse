package fr.univlyon1.m1if.m1if13.users;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Web config.
 */
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

//    @Override
//    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
//        // Converters are added here with jackSon
//        // JSON -> XML
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
//                MediaType.APPLICATION_XML));
//        converters.add(converter);
//    }
}
