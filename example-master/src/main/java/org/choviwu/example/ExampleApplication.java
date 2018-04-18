package org.choviwu.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"org.choviwu"})
//@EnableTransactionManagement//事务
@MapperScan(basePackages = {"org.choviwu.example.mapper"})
@EnableScheduling//开启定时任务
@EnableAspectJAutoProxy
@EnableWebMvc
@EnableAutoConfiguration//自动化配置
@EnableAsync//开启异步
@EnableSwagger2 //API swagger2
public class ExampleApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(ExampleApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		SpringApplication.run(ExampleApplication.class, args);
	}
}
