package com.wtychn.tmall.config;

import com.alibaba.fastjson.JSON;
import com.wtychn.tmall.service.UserService;
import com.wtychn.tmall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Override
    public void configure(WebSecurity web) {
        //用来配置忽略掉的 URL 地址，一般对于静态文件，我们可以采用此操作。
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // 认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    // 授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/product").permitAll()
                .antMatchers("/review").permitAll()

                .antMatchers("/buy").authenticated()
                .antMatchers("/alipay").authenticated()
                .antMatchers("/payed").authenticated()
                .antMatchers("/cart").authenticated()
                .antMatchers("/bought").authenticated()
                .antMatchers("/confirmPay").authenticated()
                .antMatchers("/orderConfirmed").authenticated()
                .antMatchers("/forebuyone").authenticated()
                .antMatchers("/forebuy").authenticated()
                .antMatchers("/foreaddCart").authenticated()
                .antMatchers("/forecart").authenticated()
                .antMatchers("/forechangeOrderItem").authenticated()
                .antMatchers("/foredeleteOrderItem").authenticated()
                .antMatchers("/forecreateOrder").authenticated()
                .antMatchers("/forepayed").authenticated()
                .antMatchers("/forebought").authenticated()
                .antMatchers("/foreconfirmPay").authenticated()
                .antMatchers("/foreorderConfirmed").authenticated()
                .antMatchers("/foredeleteOrder").authenticated()
                .antMatchers("/forereview").authenticated()
                .antMatchers("/foredoreview").authenticated();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/forelogin")
                .usernameParameter("name")
                .passwordParameter("password")
                .successHandler((req, res, authentication) -> {
                            res.setContentType("application/json;charset=utf-8");
                            PrintWriter out = res.getWriter();
                            out.write(JSON.toJSONString(Result.success()));
                            out.flush();
                            out.close();
                        })
                .failureHandler((req, res, e) -> {
                            res.setContentType("application/json;charset=utf-8");
                            PrintWriter out = res.getWriter();
                            System.out.println(e.getMessage());
                            out.write(JSON.toJSONString(Result.fail("账号密码错误")));
                            out.flush();
                            out.close();
                        });
        http.logout().logoutUrl("/forelogout").permitAll();
    }
}
