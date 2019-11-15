package com.miracle.cloud.config;

import com.alibaba.fastjson.JSON;
import com.miracle.cloud.result.RestResult;
import com.miracle.cloud.result.RestResultBuilder;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月04日 01:38:00
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/login**").permitAll()
                .antMatchers("/guest/**").permitAll()
                .anyRequest().authenticated()
                //这里必须要写formLogin()，不然原有的UsernamePasswordAuthenticationFilter不会出现，也就无法配置我们重新的UsernamePasswordAuthenticationFilter
                .and().formLogin().loginProcessingUrl("/login").permitAll()
                .and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true)
                .and().enableSessionUrlRewriting(true).sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().csrf().disable();

        http.addFilterAt(customizeUsernamePasswordFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtTokenFilter(), CustomizeUsernamePasswordFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/swagger-ui.html")
                .antMatchers("*.js");
    }


    @Bean
    public CustomizeUsernamePasswordFilter customizeUsernamePasswordFilter() throws Exception {
        CustomizeUsernamePasswordFilter filter = new CustomizeUsernamePasswordFilter();
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            RestResult result = new RestResultBuilder<>().success(authentication).build();
            response.getOutputStream().write(JSON.toJSONString(result).getBytes());
        });
        filter.setAuthenticationFailureHandler((req, res, ex) -> {
            RestResult result = new RestResultBuilder<>().failed(HttpStatus.UNAUTHORIZED.value(), "登陆认证失败").build();
            res.getOutputStream().write(JSON.toJSONString(result).getBytes());
        });
        filter.setFilterProcessesUrl("/login");
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(bCryptPasswordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String s = "test";
        test(encoder);
        pass(s);
        System.out.println(encoder);
        System.out.println(s);
    }

    public static void test(Object encoder) {
        System.out.println(encoder);
    }

    public static void pass(String s) {
        s = "newnewnwn";
        System.out.println(s);
    }


}
