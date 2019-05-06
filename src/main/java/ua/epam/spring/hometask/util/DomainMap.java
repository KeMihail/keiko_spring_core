package ua.epam.spring.hometask.util;

import java.util.HashMap;
import java.util.Map;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;


public class DomainMap
{
	private static Map<Long, User> userMap = new HashMap<>();
	private static Map<Long, Event> eventMap = new HashMap<>();

	public static Map<Long, User> getUserMap()
	{
		return userMap;
	}

	public static Map<Long, Event> getEventMap()
	{
		return eventMap;
	}
}
