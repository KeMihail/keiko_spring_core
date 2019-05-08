package ua.epam.spring.hometask.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.dao.IUserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.AmbiguousIdentifierException;
import ua.epam.spring.hometask.exceptions.UnknownIdentifierException;
import ua.epam.spring.hometask.service.IUserService;

import static java.util.stream.Collectors.toCollection;


public class UserService implements IUserService
{
	IUserDao dao;

	public void setDao(IUserDao dao) {
		this.dao = dao;
	}

	@Nullable
	@Override
	public User getUserByEmail(@Nonnull final String email) throws UnknownIdentifierException, AmbiguousIdentifierException
	{
		//final List<User> users = new ArrayList<>(dao.getUserByEmail(email));

		final ArrayList<User> users = dao.getUserByEmail(email).stream().collect(toCollection(ArrayList::new));

		if (users.isEmpty())
		{
			throw new UnknownIdentifierException("User with email '" + email + " not found!");
		}

		if (users.size() > 1)
		{
			throw new AmbiguousIdentifierException(
					"User with email '" + email + "  is not unique, " + users.size() + " users found!");
		}

		return users.get(0);
	}

	@Override
	public User save(@Nonnull final User object)
	{
		dao.save(object);
		return object;
	}

	@Override
	public void remove(@Nonnull final User object)
	{
		dao.remove(object);
	}

	@Override
	public User getById(@Nonnull final Long id) throws UnknownIdentifierException, AmbiguousIdentifierException
	{

		//final ArrayList<User> users = dao.getById(id).stream().collect(toCollection(ArrayList::new));

		final List<User> users = new ArrayList<>(dao.getById(id));

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
	public Collection<User> getAll()
	{
		return dao.getAll();
	}
}
