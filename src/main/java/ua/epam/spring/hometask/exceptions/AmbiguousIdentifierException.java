package ua.epam.spring.hometask.exceptions;

public class AmbiguousIdentifierException extends Exception
{
	public AmbiguousIdentifierException(String errorMessage)
	{
		super(errorMessage);
	}
}
