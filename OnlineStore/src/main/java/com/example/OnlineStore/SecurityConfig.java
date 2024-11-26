package com.example.OnlineStore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signin", "/signup").permitAll()
                        // Categories
                        .requestMatchers(HttpMethod.GET, "/categories/{id}").hasAnyRole("ADMIN", "USER") 
                        .requestMatchers(HttpMethod.PUT, "/categories/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/categories").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                        
                        // Products
                        .requestMatchers(HttpMethod.GET, "/products/{id}").hasAnyRole("ADMIN", "USER") 
                        .requestMatchers(HttpMethod.PUT, "/products/{id}").hasRole("ADMIN") 
                        .requestMatchers(HttpMethod.DELETE, "/products/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/products").hasAnyRole("ADMIN", "USER") 
                        .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN") 

                        // Users
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN") 
                        .requestMatchers(HttpMethod.POST, "/users").hasRole("USER") 
                        .requestMatchers(HttpMethod.GET, "/users/{userId}").hasAnyRole("ADMIN", "USER") 
                        .requestMatchers(HttpMethod.PUT, "/users/{userId}").hasRole("USER") 
                        .requestMatchers(HttpMethod.DELETE, "/users/{userId}").hasRole("USER") 
                       
                        // ShoppingCart
                        .requestMatchers("/shopping-carts/{cartId}/{userId}/add-product/{productId}").hasRole("USER") 
                        .requestMatchers("/shopping-carts/{cartId}/remove-product/{productId}").hasRole("USER") 
                        .requestMatchers("/shopping-carts/{cartId}").hasAnyRole("ADMIN", "USER") 
                        .requestMatchers("/shopping-carts").hasRole("ADMIN") 

                        // ShippingMethods
                        .requestMatchers(HttpMethod.GET, "/shipping_methods/{shippingMethodsId}").hasAnyRole("ADMIN", "USER")  
                        .requestMatchers(HttpMethod.PUT, "/shipping_methods/{shippingMethodsId}").hasRole("ADMIN")  
                        .requestMatchers(HttpMethod.DELETE, "/shipping_methods/{shippingMethodsId}").hasRole("ADMIN")  
                        .requestMatchers(HttpMethod.GET, "/shipping_methods").hasAnyRole("ADMIN", "USER")  
                        .requestMatchers(HttpMethod.POST, "/shipping_methods").hasRole("ADMIN") 

                        // Orders
                        .requestMatchers(HttpMethod.GET, "/orders/{orderId}").hasAnyRole("ADMIN", "USER")  
                        .requestMatchers(HttpMethod.PUT, "/orders/{orderId}").hasRole("USER")  
                        .requestMatchers(HttpMethod.DELETE, "/orders/{orderId}").hasRole("USER")  
                        .requestMatchers(HttpMethod.POST, "/orders/{shoppingCartId}/{shippingMethodId}").hasRole("USER")  
                        .requestMatchers(HttpMethod.GET, "/orders").hasRole("ADMIN")                      

                        // Payments
                        .requestMatchers(HttpMethod.GET, "/payments/{paymentId}").hasAnyRole("ADMIN", "USER") 
                        .requestMatchers(HttpMethod.PUT, "/payments/{paymentId}").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/payments/{paymentId}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/payments/{orderId}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/payments").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .formLogin(withDefaults())
                .rememberMe(withDefaults())
                .logout(logout -> logout.logoutUrl("/signout").permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withUsername("userOne")
                .password(passwordEncoder().encode("userOne"))
                .roles("USER") 
                .build());
        userDetailsManager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN") 
                .build());
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
