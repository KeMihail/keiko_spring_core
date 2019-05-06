package ua.epam.spring.hometask.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ua.epam.spring.hometask.exceptions.AmbiguousIdentifierException;
import ua.epam.spring.hometask.exceptions.UnknownIdentifierException;
import ua.epam.spring.hometask.service.IEventService;
import ua.epam.spring.hometask.service.impl.EventService;
import ua.epam.spring.hometask.util.DomainMap;

import static java.util.stream.Collectors.toCollection;


/**
 * @author Yuriy_Tkach
 */
public class TestEvent
{

	private Event event;
	private IEventService service = new EventService();
	private Map<Long, Event> eventMap = DomainMap.getEventMap();

	private static final Long EVENT_ID = Long.valueOf(1);
	private static final Double EVENT_PRICE = Double.valueOf(1.1);
	private static final String EVENT_NAME = "aaa";
	private static final EventRating EVENT_RATING = EventRating.HIGH;
	private static final Long FAIL_ID = Long.valueOf(2);
	private static final String FAIL_NAME = "bbb";

	@Before
	public void initEvent()
	{
		event = new Event();
		event.setId(EVENT_ID);
		event.setBasePrice(EVENT_PRICE);
		event.setName(EVENT_NAME);
		event.setRating(EVENT_RATING);

		LocalDateTime now = LocalDateTime.now();

		event.addAirDateTime(now);
		event.addAirDateTime(now.plusDays(1));
		event.addAirDateTime(now.plusDays(2));
	}

	@Test
	public void testAddRemoveAirDates()
	{
		int size = event.getAirDates().size();

		LocalDateTime date = LocalDateTime.now().plusDays(5);

		event.addAirDateTime(date);

		assertEquals(size + 1, event.getAirDates().size());
		assertTrue(event.getAirDates().contains(date));

		event.removeAirDateTime(date);

		assertEquals(size, event.getAirDates().size());
		assertFalse(event.getAirDates().contains(date));
	}

	@Test
	public void testCheckAirDates()
	{
		assertTrue(event.airsOnDate(LocalDate.now()));
		assertTrue(event.airsOnDate(LocalDate.now().plusDays(1)));
		assertFalse(event.airsOnDate(LocalDate.now().minusDays(10)));

		assertTrue(event.airsOnDates(LocalDate.now(), LocalDate.now().plusDays(10)));
		assertTrue(event.airsOnDates(LocalDate.now().minusDays(10), LocalDate.now().plusDays(10)));
		assertTrue(event.airsOnDates(LocalDate.now().plusDays(1), LocalDate.now().plusDays(1)));
		assertFalse(event.airsOnDates(LocalDate.now().minusDays(10), LocalDate.now().minusDays(5)));

		LocalDateTime time = LocalDateTime.now().plusHours(4);
		event.addAirDateTime(time);
		assertTrue(event.airsOnDateTime(time));
		time = time.plusHours(30);
		assertFalse(event.airsOnDateTime(time));
	}

	@Test
	public void testAddRemoveAuditoriums()
	{
		LocalDateTime time = event.getAirDates().first();

		assertTrue(event.getAuditoriums().isEmpty());

		event.assignAuditorium(time, new Auditorium());

		assertFalse(event.getAuditoriums().isEmpty());

		event.removeAuditoriumAssignment(time);

		assertTrue(event.getAuditoriums().isEmpty());
	}

	@Test
	public void testAddRemoveAuditoriumsWithAirDates()
	{
		LocalDateTime time = LocalDateTime.now().plusDays(10);

		assertTrue(event.getAuditoriums().isEmpty());

		event.addAirDateTime(time, new Auditorium());

		assertFalse(event.getAuditoriums().isEmpty());

		event.removeAirDateTime(time);

		assertTrue(event.getAuditoriums().isEmpty());
	}

	@Test
	public void testNotAddAuditoriumWithoutCorrectDate()
	{
		LocalDateTime time = LocalDateTime.now().plusDays(10);

		boolean result = event.assignAuditorium(time, new Auditorium());

		assertFalse(result);
		assertTrue(event.getAuditoriums().isEmpty());

		result = event.removeAirDateTime(time);
		assertFalse(result);

		assertTrue(event.getAuditoriums().isEmpty());
	}

	@Test
	public void testGrudMethods() throws UnknownIdentifierException, AmbiguousIdentifierException
	{

		eventMap.put(event.getId(), event);

		final Event eventById = service.getById(event.getId());

		Assert.assertNotNull(eventById);
		Assert.assertEquals(event.getId(), eventById.getId());
		Assert.assertEquals(event.getName(), eventById.getName());
		Assert.assertEquals(event.getBasePrice(), eventById.getBasePrice(), 0.0);
		Assert.assertEquals(event.getRating(), eventById.getRating());
		Assert.assertEquals(event.getAirDates(), eventById.getAirDates());

		final List<Event> eventsByName = service.getByName(event.getName()).stream().collect(toCollection(ArrayList::new));

		Assert.assertNotNull(eventsByName);
		Assert.assertTrue(eventsByName.size() == 1);
		Assert.assertEquals(eventsByName.get(0).getId(), eventById.getId());
		Assert.assertEquals(eventsByName.get(0).getName(), eventById.getName());
		Assert.assertEquals(eventsByName.get(0).getBasePrice(), eventById.getBasePrice(), 0.0);
		Assert.assertEquals(eventsByName.get(0).getRating(), eventById.getRating());
		Assert.assertEquals(eventsByName.get(0).getAirDates(), eventById.getAirDates());

		final List<Event> events = service.getAll().stream().collect(toCollection(ArrayList::new));

		Assert.assertNotNull(events);
		Assert.assertTrue(events.size() == 1);
		Assert.assertTrue(events.contains(event));

		service.remove(event);
		Assert.assertTrue(service.getAll().stream().collect(toCollection(ArrayList::new)).isEmpty());
	}

	@Test(expected = UnknownIdentifierException.class)
	public void failSearchById() throws UnknownIdentifierException, AmbiguousIdentifierException{
		final Event event = service.getById(FAIL_ID);
	}

	@Test(expected = UnknownIdentifierException.class)
	public void failSearchByName() throws UnknownIdentifierException, AmbiguousIdentifierException{
		final Collection<Event> events = service.getByName(FAIL_NAME);
	}

	@After
	public void cleaning(){
		if (service.getAll().stream().collect(toCollection(ArrayList::new)).contains(event)){
			service.remove(event);
		}
	}
}
