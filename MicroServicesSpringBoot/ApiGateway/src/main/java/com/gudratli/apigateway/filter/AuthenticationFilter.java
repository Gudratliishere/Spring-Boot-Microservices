package com.gudratli.apigateway.filter;

import com.gudratli.apigateway.config.Config;
import com.gudratli.apigateway.config.RouteValidator;
import com.gudratli.apigateway.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<Config>
{
    @Autowired
    private RouteValidator validator;
    @Autowired
    public JwtService jwtService;


    public AuthenticationFilter ()
    {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply (Config config)
    {
        return (((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest()))
            {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey("Authorization"))
                    throw new RuntimeException("Missing access token");

                String token = Objects.requireNonNull(exchange.getRequest().getHeaders().get("Authorization")).get(0);
                if (token != null && token.startsWith("Bearer "))
                {
                    token = token.substring(7);
                }

                if (!jwtService.isAccessTokenValid(token))
                    throw new RuntimeException("token is invalid");
            }
            return chain.filter(exchange);
        }));
    }
}
