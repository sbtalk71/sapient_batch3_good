package com.demo.spring;
import org.springframework.beans.factory.annotation.Autowired;

public class Mail{
@Autowired
private Message message;

public void showMessage(){
System.out.println(message.getDetails());
}
}
