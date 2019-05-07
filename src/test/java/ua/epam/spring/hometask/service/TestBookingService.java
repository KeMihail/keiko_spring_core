package ua.epam.spring.hometask.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.exceptions.AmbiguousIdentifierException;
import ua.epam.spring.hometask.exceptions.UnknownIdentifierException;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.util.stream.Collectors.toCollection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test.xml")
public class TestBookingService {

    @Autowired
    private IBookingService service;

    @Autowired
    private IUserService userService;

    @Resource(name = "testEvent")
    private Event event;

    @Resource(name = "redAuditorium")
    private Auditorium auditorium;

    @Resource(name = "testTicket")
    private Ticket ticket;

    private LocalDateTime date;

    private Set<Long> seats;

    @Before
    public void setUp() {
        NavigableSet<LocalDateTime> dates = new TreeSet<>();
        dates.add(LocalDateTime.of(LocalDate.of(2019, 05, 8), LocalTime.of(14, 00, 00)));
        dates.add(LocalDateTime.of(LocalDate.of(2019, 05, 10), LocalTime.of(12, 00, 00)));
        dates.add(LocalDateTime.of(LocalDate.of(2019, 05, 12), LocalTime.of(18, 00, 00)));
        event.setAirDates(dates);

        seats = new HashSet<>();
        seats.add(Long.valueOf(1));


        date = LocalDateTime.of(LocalDate.of(2019, 05, 10), LocalTime.of(12, 00, 00));

        NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();

        auditoriums.put(date,auditorium);

        event.setAuditoriums(auditoriums);

        userService.save(ticket.getUser());
    }

    @Test
    public void testTicketsPrice() {
        final Double result = service.getTicketsPrice(event, date, null, seats);

        Assert.assertEquals(12.0, result, 0.0);
    }

    @Test
    public void testBookTickets()throws UnknownIdentifierException, AmbiguousIdentifierException {
        Set <Ticket> tickets = new HashSet<>();
        tickets.add(ticket);
        service.bookTickets(tickets);

        Assert.assertTrue(userService.getById(ticket.getUser().getId()).getTickets().contains(ticket));
    }

    @After
    public void cleaning () {
        if (userService.getAll().stream().collect(toCollection(ArrayList::new)).contains(ticket.getUser())) {
            userService.remove(ticket.getUser());
        }
    }
}
