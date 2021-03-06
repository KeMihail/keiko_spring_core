package ua.epam.spring.hometask.service.impl;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.AmbiguousIdentifierException;
import ua.epam.spring.hometask.exceptions.UnknownIdentifierException;
import ua.epam.spring.hometask.service.IBookingService;
import ua.epam.spring.hometask.service.IDiscountService;
import ua.epam.spring.hometask.service.IUserService;


public class BookingService implements IBookingService {

    private final static Double RAITING_HIGN = Double.valueOf(1.2);
    private static final Double VIP_SEATS = Double.valueOf(1.5);
    private static final String BIRHDAY_DISCOUNT_STRATEGY = "Birthday";
    private static final String TICKET_DISCOUNT_STRATEGY = "Ticket";
    private static final String DISCOUNT_STRATEGY = "Discount";


    private IUserService userService;

    private IDiscountService discountService;

    private Map<String, IDiscountService> strategies;

    public void setStrategies(Map<String, IDiscountService> strategies) {
        this.strategies = strategies;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public double getTicketsPrice(@Nonnull final Event event, @Nonnull final LocalDateTime dateTime, @Nullable final User user,
                                  @Nonnull final Set<Long> seats) {
        Double result = event.getBasePrice() * seats.size();
        if (event.getRating().equals(EventRating.HIGH)) {
            result *= seats.size() * RAITING_HIGN;
        }

        final Map<LocalDateTime, Auditorium> auditoriums = event.getAuditoriums();
        final Auditorium auditorium = auditoriums.get(dateTime);
        final Long countVipSeats = auditorium.countVipSeats(seats);

        if (isBirthday(dateTime,user) && (seats.size() < Long.valueOf(10))){
            discountService = strategies.get(BIRHDAY_DISCOUNT_STRATEGY);
        }

        if (seats.size() > Long.valueOf(9) && !isBirthday(dateTime,user)){
            discountService = strategies.get(TICKET_DISCOUNT_STRATEGY);
        }

        if (isBirthday(dateTime,user) && (seats.size() > Long.valueOf(9))){

            discountService = strategies.get(DISCOUNT_STRATEGY);
        }

        if (discountService != null){
            result -= discountService.getDiscount(user,event,dateTime,seats.size());
        }

        if (countVipSeats != null) {
            result += (event.getBasePrice() * (VIP_SEATS - 1)) * countVipSeats;
        }
        return result;
    }

    @Override
    public void bookTickets(@Nonnull final Set<Ticket> tickets) throws UnknownIdentifierException, AmbiguousIdentifierException {
        for (final Ticket ticket : tickets) {
            if (ticket.getUser().getId() != null) {
                final User user = userService.getById(ticket.getUser().getId());
                NavigableSet<Ticket> tick = user.getTickets();
                tick.add(ticket);
                user.setTickets(tick);
                userService.save(user);
            }
        }
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull final Event event, @Nonnull final LocalDateTime dateTime) {
        // ??

		/*Set<Ticket> result = new TreeSet<>();
		if (event.getAirDates().contains(dateTime)){
			result.add(event)
		}*/
        return null;
    }

    private Boolean isBirthday(@Nonnull final LocalDateTime dateTime, @Nullable final User user) {

        final Month monthBirthday = user.getBirthday().getMonth();
        final Integer dayBirthday = user.getBirthday().getDayOfMonth();

        final Month monthEvent = dateTime.getMonth();
        final Integer dayEvent = dateTime.getDayOfMonth();

        if (monthBirthday.equals(monthEvent)) {
            if (dayBirthday.equals(dayEvent) || (dayBirthday + 5) < dayEvent) {

                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
