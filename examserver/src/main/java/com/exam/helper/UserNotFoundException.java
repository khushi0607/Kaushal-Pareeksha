package com.exam.helper;

public class UserNotFoundException extends Exception{
	
	public UserNotFoundException()
	{
		super("Username not present !!");
	}
	public UserNotFoundException(String msg)
	{
		super(msg);
	}
}
