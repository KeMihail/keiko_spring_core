package ua.epam.spring.hometask.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test.xml")
public class TestDiscountService {

    @Resource(name = "testUser")
    private User user;

    @Resource(name = "testEvent")
    private Event event;

    @Resource(name = "birthdayStrategy")
    private IDiscountService birthdayStrategy;

    @Resource(name = "ticketsStrategy")
    private IDiscountService ticketsStrategy;

    @Resource(name = "birthdayAndTicket")
    private IDiscountService discountStrategy;

    private LocalDate birhday;

    private LocalDateTime eventDate;

    @Before
    public void setUp() {
        birhday = LocalDate.of(1984, 7, 28);
        eventDate = LocalDateTime.of(1984, 7, 28, 14, 00, 00);
        user.setBirthday(birhday);
    }

    @Test
    public void testBirthdayStrategy() {

        final Double discount = birthdayStrategy.getDiscount(user, event, eventDate, Long.valueOf(1));

        Assert.assertEquals(discount, Double.valueOf(0.5), 0.0);
    }

    @Test
    public void testTicketStrategy() {
        final Double discount = ticketsStrategy.getDiscount(user, event, eventDate, Long.valueOf(10));

        Assert.assertEquals(discount, Double.valueOf(5), 0.0);
    }

    @Test
    public void testBirthdayTicketStrategy() {

        Double discount = discountStrategy.getDiscount(user, event, eventDate, 10);
        Assert.assertEquals(discount, Double.valueOf(5), 0.0);

        discount = discountStrategy.getDiscount(user, event, eventDate, 12);
        Assert.assertEquals(discount, Double.valueOf(6), 0.0);
    }
}
