package com.exam.helper;

public class UserFoundException extends Exception{
	
	public UserFoundException()
	{
		super("Username already present !!");
	}
	public UserFoundException(String msg)
	{
		super(msg);
	}

}
