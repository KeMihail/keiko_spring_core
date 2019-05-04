package ua.epam.spring.hometask.dao;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.dao.impl.AbstractDomainObjectDao;
import ua.epam.spring.hometask.domain.User;


public interface IUserDao extends AbstractDomainObjectDao
{

	/**
	 * Finding user by email
	 *
	 * @param email
	 * 		Email of the user
	 * @return found user or <code>null</code>
	 */
	public @Nullable
	Collection<User> getUserByEmail(@Nonnull String email);

}
