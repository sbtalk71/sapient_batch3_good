package com.demo.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		
		ApplicationContext ctx=new ClassPathXmlApplicationContext("context.xml");
		
		Mail mail=ctx.getBean(Mail.class,"myMail");
		
		System.out.println(mail.getMessage().getBody());
		
		//Spring Core module
	}
}
