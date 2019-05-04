package ua.epam.spring.hometask.util;

import java.util.Map;

import ua.epam.spring.hometask.domain.User;


public class DomainMap
{
	private static Map<Long, User> userMap;

	public static Map<Long, User> getUserMap()
	{
		return userMap;
	}
}
