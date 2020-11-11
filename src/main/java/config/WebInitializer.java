package config;


import ch.qos.logback.classic.Logger;
import config.obj.CorsFilterObj;
import config.obj.ShiroFilterObj;
import org.slf4j.LoggerFactory;

public class WebInitializer  extends BaseWebInitializer {
	Logger logger = (Logger) LoggerFactory.getLogger(WebInitializer.class);

    @Override
    protected Class<?>[] getConfigClass() {
        return new Class[] { AppConfig.class };
    }

    @Override
    protected String getSpringMapping() {
        return "/s/*";
    }

	@Override
	protected ShiroFilterObj loadShiroFilter() {
		return null;
	}

	@Override
	protected CorsFilterObj loadCorsFilter() {
		return null;
	}
	
}
