package Goweb.FormMaker.config;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addContextCustomizers(new TomcatContextCustomizer() {
            @Override
            public void customize(Context context) {
                context.addLifecycleListener(event -> {
                    if (event.getType().equals(Lifecycle.BEFORE_STOP_EVENT)) {
                        try {
                            ((Tomcat) event.getLifecycle()).getServer().stop();
                        } catch (LifecycleException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
