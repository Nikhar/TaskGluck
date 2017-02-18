package com.connectfour.configuration;

import com.connectfour.resource.GameResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ConnectFourApplication extends Application<ConnectFourConfiguration>{
	
    public static void main(String[] args) throws Exception {
        new ConnectFourApplication().run(args);
    }
    
    @Override
    public void initialize(Bootstrap<ConnectFourConfiguration> bootstrap) {
		

    }
    
	@Override
	public void run(ConnectFourConfiguration configuration, Environment environment) throws Exception {
		environment.jersey().register(new GameResource());
		
	}

}
