package com.am.rbacsystem.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

//    @Autowired
//    private AuthInterceptor authInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/auth/manager").setViewName("auth_manager");
        registry.addViewController("/403.html").setViewName("403");

    }

    //注入事务管理器
    @Bean
    public DataSourceTransactionManager manager(DataSource dataSource){
        DataSourceTransactionManager manager=new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return  manager;
    }

//    //注册拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration = registry.addInterceptor(authInterceptor);
//        registration.addPathPatterns("/**");
//        registration.order(1);
//    }

    //注册druid过滤器
    @Bean
    public FilterRegistrationBean registrationBean(){

//        AuthFilter authFilter=new AuthFilter();
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
//        registrationBean.setFilter(authFilter);
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(1);

        WebStatFilter webStatFilter =new WebStatFilter();
        Map init = new HashMap();
        init.put("exclusions ","*.js,*.png,*.css,*.jpg");

        registrationBean.setFilter(webStatFilter);


        registrationBean.addUrlPatterns("/*");
        registrationBean.setInitParameters(init);//设置初始化参数
        registrationBean.setOrder(2);

        return registrationBean;
    }

    //负责展示视图
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        StatViewServlet servlet = new StatViewServlet();
        ServletRegistrationBean bean = new ServletRegistrationBean();
        bean.addUrlMappings("/druid/*");
        Map init = new HashMap();
        // init.put("loginUsername","hello");
        // init.put("loginPassword","123");
        init.put("allow","127.0.0.1");
        bean.setInitParameters(init);

        bean.setServlet(servlet);
        return bean;
    }
}
