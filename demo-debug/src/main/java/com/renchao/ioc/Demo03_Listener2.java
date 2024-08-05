package com.renchao.ioc;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

public class Demo03_Listener2 {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

		MyService bean = context.getBean(MyService.class);
		bean.doBus();
	}


	@Configuration
	static class Config {
		@Bean
		public MyService myService(ApplicationEventPublisher publisher) {
			return new MyService(publisher);
		}
		@Bean
		public SmsService smsApplicationListener() {
			return new SmsService();
		}
		@Bean
		public EmailService emailApplicationListener() {
			return new EmailService();
		}

	}

	static class MyService {
		private final ApplicationEventPublisher publisher;

		MyService(ApplicationEventPublisher publisher) {
			this.publisher = publisher;
		}

		public void doBus() {
			System.out.println("==========主线业务==========");
			publisher.publishEvent(new MyEvent("事件源：MyService#doBus"));
		}
	}

	static class SmsService {
		@EventListener
		public void listener(MyEvent event) {
			System.out.println("发短信================  ");
		}
	}

	static class EmailService {

		@EventListener
		public void listener(MyEvent event) {
			System.out.println("发邮件================");
		}
	}


	static class MyEvent extends ApplicationEvent {
		public MyEvent(Object source) {
			super(source);
		}


	}
}
