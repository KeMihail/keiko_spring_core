package ua.epam.spring.hometask.exceptions;

public class UnknownIdentifierException extends Exception
{
	public UnknownIdentifierException(String errorMessage)
	{
		super(errorMessage);
	}
}
