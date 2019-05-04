package ua.epam.spring.hometask.dao;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.dao.impl.AbstractDomainObjectDao;
import ua.epam.spring.hometask.domain.Event;


public interface IEventDao extends AbstractDomainObjectDao
{
	/**
	 * Finding event by name
	 *
	 * @param name
	 * 		Name of the event
	 * @return found event or <code>null</code>
	 */
	public @Nullable
	Collection<Event> getByName(@Nonnull String name);
}
