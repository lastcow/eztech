package me.chen.eztech;

import me.chen.eztech.filter.GravatarFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EztechApplication {

    public static void main(String[] args) {
        SpringApplication.run(EztechApplication.class, args);
    }

    /**
     * Password encoder
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<GravatarFilter> addAvatarFilter(){
        FilterRegistrationBean<GravatarFilter> gravatarBean = new FilterRegistrationBean<>();
        gravatarBean.setFilter(new GravatarFilter());
        gravatarBean.addUrlPatterns("/student/*", "/professor/*");

        return gravatarBean;
    }

}
