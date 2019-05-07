package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.IEventDao;
import ua.epam.spring.hometask.domain.DomainObject;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.util.DomainMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;


public class EventDao implements IEventDao
{
	private Map<Long, Event> eventMap;

	public void setEventMap(Map<Long, Event> eventMap) {
		this.eventMap = eventMap;
	}

	@Nullable
	@Override
	public Collection<Event> getByName(@Nonnull final String name)
	{
		if (!eventMap.isEmpty())
		{
			List<Event> result = new ArrayList<>();

			for (Map.Entry<Long, Event> entry : eventMap.entrySet())
			{
				if (entry.getValue().getName().equals(name))
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
	public Collection<Event> getForDateRange(@Nonnull final LocalDateTime from, @Nonnull final LocalDateTime to)
	{
		List<Event> result = new ArrayList<>();
		final List <Event> events = new ArrayList<>(getAll());

		for (final Event event : events){
			for (final LocalDateTime date : event.getAirDates()){
				if (date.isAfter(from.minusMinutes(1)) && date.isBefore(to.plusMinutes(1))){
					result.add(event);
				}
			}
		}
		return result;
	}

	@Nonnull
	@Override
	public Collection<Event> getNextEvents(@Nonnull final LocalDateTime to)
	{
		List<Event> result = new ArrayList<>();
		final List <Event> events = new ArrayList<>(getAll());

		for (final Event event : events){
			for (final LocalDateTime date : event.getAirDates()){
				if (date.isAfter(LocalDateTime.now().minusMinutes(1)) && date.isBefore(to.plusMinutes(1))){
					result.add(event);
				}
			}
		}
		return result;
	}

	@Override
	public DomainObject save(@Nonnull final DomainObject object)
	{
		eventMap.put(object.getId(), (Event) object);
		return object;
	}

	@Override
	public void remove(@Nonnull final DomainObject object)
	{
		eventMap.remove(object.getId());
	}

	@Override
	public Collection<DomainObject> getById(@Nonnull final Long id)
	{
		if (!eventMap.isEmpty())
		{
			List<DomainObject> result = new ArrayList<>();

			for (Map.Entry<Long, Event> entry : eventMap.entrySet())
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
		if (!eventMap.isEmpty())
		{
			List<Event> list = new ArrayList<>();
			for (Event event : eventMap.values())
			{
				list.add(event);
			}
			return list;
		}
		return Collections.emptyList();
	}
}
