package com.wtychn.tmall.config;

import com.alibaba.fastjson.JSON;
import com.wtychn.tmall.handler.MyAuthenticationFailureHandler;
import com.wtychn.tmall.handler.MyAuthenticationSuccessHandler;
import com.wtychn.tmall.interceptor.LoginFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                .and()
                .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.logout().logoutUrl("/forelogout").permitAll();
    }

    @Bean
    LoginFilter loginFilter() throws Exception{
        LoginFilter loginFilter=new LoginFilter();
        //设置认证成功返回
        loginFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
        //设置认证失败返回
        loginFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/forelogin");
        return loginFilter;
    }
}
