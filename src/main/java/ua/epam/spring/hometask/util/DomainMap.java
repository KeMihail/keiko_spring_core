package ua.epam.spring.hometask.util;

import java.util.HashMap;
import java.util.Map;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;


public class DomainMap
{
	private static Map<Long, User> userMap = new HashMap<>();
	private static Map<Long, Event> eventMap = new HashMap<>();
	private Map<String, Auditorium> auditoriumMap = new HashMap<>();

	public void setAuditoriumMap(Map<String, Auditorium> auditoriumMap) {
		this.auditoriumMap = auditoriumMap;
	}

	public static Map<Long, User> getUserMap()
	{
		return userMap;
	}

	public static Map<Long, Event> getEventMap()
	{
		return eventMap;
	}

	public Map<String, Auditorium> getAuditoriumMap() {
		return auditoriumMap;
	}
}
