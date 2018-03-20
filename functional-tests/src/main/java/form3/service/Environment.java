package form3.service;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import form3.service.components.ServiceComponent;

public class Environment {
    /**
     * Singleton Pattern using Holder Class
     */
    private static class EnvironmentHolder {
        private static Environment instance = new Environment();
    }

    private final String           environment;
    private final Boolean functionalTestMode;
    private final Config           environmentConfig;
    private final ServiceComponent serviceComponent;

    private Environment(){
        this.environment        = System.getenv().getOrDefault("ENVIRONMENT", "ct");
        this.functionalTestMode = environment.equals("ct");
        this.environmentConfig  = ConfigFactory.load(String.format("application.%s.conf", environment));
        this.serviceComponent   = ServiceComponent.getInstance(environmentConfig.getConfig("service"), this.functionalTestMode);
    }

    public static Environment getInstance(){
        return EnvironmentHolder.instance;
    }

    public String getEnvironment() {
        return environment;
    }

    public Boolean getFunctionalTestMode() {
        return functionalTestMode;
    }

    public Config getEnvironmentConfig() {
        return environmentConfig;
    }

    public ServiceComponent getServiceComponent() {
        return serviceComponent;
    }
}
