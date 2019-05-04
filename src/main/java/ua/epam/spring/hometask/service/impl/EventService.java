package ua.epam.spring.hometask.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.dao.IEventDao;
import ua.epam.spring.hometask.dao.IUserDao;
import ua.epam.spring.hometask.dao.impl.EventDao;
import ua.epam.spring.hometask.dao.impl.UserDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.AmbiguousIdentifierException;
import ua.epam.spring.hometask.exceptions.UnknownIdentifierException;
import ua.epam.spring.hometask.service.IEventService;

import static java.util.stream.Collectors.toCollection;


public class EventService implements IEventService
{
	IEventDao dao = new EventDao();

	@Nullable
	@Override
	public Collection<Event> getByName(@Nonnull final String name) throws UnknownIdentifierException
	{
		//final List<Event> users = new ArrayList<>(dao.getByName(name));

		final ArrayList<Event> users = dao.getByName(name).stream().collect(toCollection(ArrayList::new));

		if (users.isEmpty())
		{
			throw new UnknownIdentifierException("User with name '" + name + " not found!");
		}

		return users;
	}

	@Override
	public Event save(@Nonnull final Event object)
	{
		dao.save(object);
		return object;
	}

	@Override
	public void remove(@Nonnull final Event object)
	{
		dao.remove(object);
	}

	@Override
	public Event getById(@Nonnull final Long id) throws UnknownIdentifierException, AmbiguousIdentifierException
	{
		//final ArrayList<User> users = dao.getById(id).stream().collect(toCollection(ArrayList::new));

		final List<Event> users = new ArrayList<>(dao.getById(id));

		if (users.isEmpty())
		{
			throw new UnknownIdentifierException("User with id '" + id + " not found!");
		}

		if (users.size() > 1)
		{
			throw new AmbiguousIdentifierException("User with id '" + id + "  is not unique, " + users.size() + " users found!");
		}

		return users.get(0);
	}

	@Nonnull
	@Override
	public Collection<Event> getAll()
	{
		return dao.getAll();
	}
}
