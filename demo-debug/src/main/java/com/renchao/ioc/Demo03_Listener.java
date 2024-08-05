package com.renchao.ioc;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class Demo03_Listener {
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
		public SmsApplicationListener smsApplicationListener() {
			return new SmsApplicationListener();
		}
		@Bean
		public EmailApplicationListener emailApplicationListener() {
			return new EmailApplicationListener();
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

	static class SmsApplicationListener implements ApplicationListener<MyEvent> {
		@Override
		public void onApplicationEvent(MyEvent event) {
			System.out.println("发短信================");
		}
	}

	static class EmailApplicationListener implements ApplicationListener<MyEvent> {
		@Override
		public void onApplicationEvent(MyEvent event) {
			System.out.println("发邮件================");
		}
	}


	static class MyEvent extends ApplicationEvent {
		public MyEvent(Object source) {
			super(source);
		}
	}
}
