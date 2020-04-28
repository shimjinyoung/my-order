package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
@EnableBinding(Processor.class) //app  yml에서 설정된  in/out 등록
public class DemoApplication {
	public static ApplicationContext applicationContext; //스프링 빈객체를 다른데서 가져올수있게.(Autowried)
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(DemoApplication.class, args);
	}


}
