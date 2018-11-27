package hello;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


//
// 27/11/18 - experimenting ...
// Can comment out this in its entirety and it will compile and run
// but it doesn't work!! This bit seems pivotal in joining stuff together ...
//
// ?? Was this code auto generated, or did I need to copy/paste it from somewhere ??
//

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    // Choices for setPortTypeName & BEAN_NAME have little impact (beyond the wsdl name offered)
//    static final String PORT_TYPE_NAME="CountriesPort";
//    static final String BEAN_NAME="countries";
    static final String PORT_TYPE_NAME="VintagePort";
    static final String BEAN_NAME="sherries";

    // Adding a second
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        // This value matters in terms of getting the WSDL and getting country details
        //        return new ServletRegistrationBean(servlet, "/wc/*");
        return new ServletRegistrationBean(servlet, "/ws/*");
    }


    // Not sure the name of this bean matters a jot. Maybe when there are multiple XSD's floating around
    // documentation suggest it will determine what wsdl is generated & made available localhost:8080/ws/<bean names>.wsdl
    //
    // removing this doesn't stop the ability to return a requested country
    // but does bugger up the ability to display the wsdl
    //
    @Bean(name = BEAN_NAME)
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(PORT_TYPE_NAME);
        // this value doesn't seem to matter much
        wsdl11Definition.setLocationUri("/ws");
// TODO - TNS
          wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
//        wsdl11Definition.setTargetNamespace("http://bb.spring.io/guides");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
    }


}