package uz.pdp.appg4duonotaryserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import uz.pdp.appg4duonotaryserver.security.JwtErrors;
import uz.pdp.appg4duonotaryserver.security.JwtTokenFilter;
import uz.pdp.appg4duonotaryserver.service.AuthService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthService authService;
    @Autowired
    JwtErrors jwtErrors;

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }


    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean factoryBean() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates");
        return bean;
    }


    @Bean
    JwtTokenFilter tokenFilter() {
        return new JwtTokenFilter();
    }

//    @Primary
//    @Bean
//    public FreeMarkerConfigurationFactoryBean factoryBean() {
//        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
//        bean.setTemplateLoaderPath("classpath:/templates");
//        return bean;
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtErrors)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/csrf",
                        "/webjars/**")
                .permitAll()
                .antMatchers("/api/auth/**", "/api/state", "/api/zipCode/**",
                        "/api/loyaltyDiscountTariff/**", "/api/blog/**", "/api/mainServiceWorkTime/**"
                        , "/api/mainService/**", "/api/country/**", "/api/sharingDiscount/**"
                        , "/api/smsVerify/**", "/api/agentSchedule/**", "/api/additionalServicePrice/**","/api/order/**","/api/additionalService/**","/api/test")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/auth/**", "/api/state", "/api/holiday/**", "/api/blog/**", "/api/mainServiceWorkTime/**", "/api/smsVerify/**", "/api/mainService/**", "/api/agentSchedule/**", "/api/country/**", "/api/additionalServicePrice/**", "/api/sharingDiscount/**","/api/order/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/auth/**", "/api/javers/**", "/api/smsVerify/**", "/api/stripe/**","/api/mainService/**")
                .permitAll()
                .anyRequest()
//                .antMatchers("/api/**")
                .authenticated();

//MANA SHU YERDA YO'LLAR TOKENGA QARAB OCHILADI
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
