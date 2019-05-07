package ua.epam.spring.hometask.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.exceptions.AmbiguousIdentifierException;
import ua.epam.spring.hometask.exceptions.UnknownIdentifierException;
import ua.epam.spring.hometask.service.impl.EventService;
import ua.epam.spring.hometask.util.DomainMap;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toCollection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test.xml")
public class TestEventService {

    @Resource(name = "testEvent")
    private Event event;
    @Autowired
    private IEventService service;

    private static final Long FAIL_ID = Long.valueOf(2);
    private static final String FAIL_NAME = "bbb";


    @Before
    public void setUp() {

        LocalDateTime now = LocalDateTime.now();

        event.addAirDateTime(now);
        event.addAirDateTime(now.plusDays(1));
        event.addAirDateTime(now.plusDays(2));

        service.save(event);
    }


        @Test
        public void testEventsFromDateRange() {

            Assert.assertEquals(service.getForDateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(3)).size(), Integer.valueOf(3), 0);
            Assert.assertEquals(service.getNextEvents(LocalDateTime.now().plusDays(3)).size(), Integer.valueOf(3), 0);
        }

        @Test
        public void testGrudMethods () throws UnknownIdentifierException, AmbiguousIdentifierException
        {

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
        public void failSearchById () throws UnknownIdentifierException, AmbiguousIdentifierException {
            final Event event = service.getById(FAIL_ID);
        }

        @Test(expected = UnknownIdentifierException.class)
        public void failSearchByName () throws UnknownIdentifierException, AmbiguousIdentifierException {
            final Collection<Event> events = service.getByName(FAIL_NAME);
        }

        @After
        public void cleaning () {
            if (service.getAll().stream().collect(toCollection(ArrayList::new)).contains(event)) {
                service.remove(event);
            }
        }
    }
