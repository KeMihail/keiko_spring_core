package ua.epam.spring.hometask.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.dao.IUserDao;
import ua.epam.spring.hometask.dao.impl.UserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.AmbiguousIdentifierException;
import ua.epam.spring.hometask.exceptions.UnknownIdentifierException;
import ua.epam.spring.hometask.service.IUserService;

import static java.util.stream.Collectors.toCollection;


public class UserService implements IUserService
{
	IUserDao dao = new UserDao();

	@Nullable
	@Override
	public User getUserByEmail(@Nonnull final String email) throws UnknownIdentifierException,AmbiguousIdentifierException
	{
		//final List<User> users = new ArrayList<>(dao.getUserByEmail(email));

		ArrayList<User> users = dao.getUserByEmail(email).stream().collect(toCollection(ArrayList::new));

		if (users.isEmpty())
		{
			throw new UnknownIdentifierException("User with email '" + email + " not found!");
		}

		if (users.size() > 1)
		{
			throw new AmbiguousIdentifierException("User with email '" + email + "  is not unique, " + users.size() + " users found!");
		}

		return users.get(0);
	}

	@Override
	public User save(@Nonnull final User object)
	{
		return null;
	}

	@Override
	public void remove(@Nonnull final User object)
	{

	}

	@Override
	public User getById(@Nonnull final Long id)
	{
		return null;
	}

	@Nonnull
	@Override
	public Collection<User> getAll()
	{
		return null;
	}
}
