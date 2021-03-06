package ua.epam.spring.hometask.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.dao.IUserDao;
import ua.epam.spring.hometask.domain.DomainObject;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.util.DomainMap;


public class UserDao implements IUserDao
{
	private Map<Long, User> userMap;

	public void setUserMap(Map<Long, User> userMap) {
		this.userMap = userMap;
	}

	@Nullable
	@Override
	public Collection<User> getUserByEmail(@Nonnull final String email)
	{
		if (!userMap.isEmpty())
		{
			List<User> result = new ArrayList<>();

			for (Map.Entry<Long, User> entry : userMap.entrySet())
			{
				if (entry.getValue().getEmail().equals(email))
				{
					result.add(entry.getValue());
				}
			}
			return result;
		}
		return Collections.emptyList();
	}

	@Override
	public DomainObject save(@Nonnull final DomainObject object)
	{
		userMap.put(object.getId(), (User) object);
		return object;
	}

	@Override
	public void remove(@Nonnull final DomainObject object)
	{
		userMap.remove(object.getId());
	}

	@Override
	public Collection<DomainObject> getById(@Nonnull final Long id)
	{
		if (!userMap.isEmpty())
		{
			List<DomainObject> result = new ArrayList<>();

			for (Map.Entry<Long, User> entry : userMap.entrySet())
			{
				if (entry.getKey().equals(id))
				{
					result.add(entry.getValue());
				}
			}
			return result;
		}
		return Collections.emptyList();
	}

	@Nonnull
	@Override
	public Collection getAll()
	{
		if (!userMap.isEmpty())
		{
			List<User> list = new ArrayList<>();
			for (User user : userMap.values())
			{
				list.add(user);
			}
			return list;
		}
		return Collections.emptyList();
	}
}

