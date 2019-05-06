package ua.epam.spring.hometask.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

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
import ua.epam.spring.hometask.service.IUserService;


public class BookingService implements IBookingService
{

	private static Double RAITING_HIGN = Double.valueOf(1.2);
	private static Double VIP_SEATS = Double.valueOf(1.5);

	private IUserService userService = new UserService();

	@Override
	public double getTicketsPrice(@Nonnull final Event event, @Nonnull final LocalDateTime dateTime, @Nullable final User user,
			@Nonnull final Set<Long> seats)
	{
		Double result = event.getBasePrice() * seats.size();
		if (event.getRating().equals(EventRating.HIGH))
		{
			result *= seats.size() * RAITING_HIGN;
		}

		final Map<LocalDateTime, Auditorium> auditoriums = event.getAuditoriums();
		final Auditorium auditorium = auditoriums.get(dateTime);
		final Long countVipSeats = auditorium.countVipSeats(seats);

		if (countVipSeats != null)
		{
			result += (event.getBasePrice() * (VIP_SEATS - 1)) * countVipSeats;
		}

		return result;
	}

	@Override
	public void bookTickets(@Nonnull final Set<Ticket> tickets) throws UnknownIdentifierException, AmbiguousIdentifierException
	{

		for (final Ticket ticket : tickets)
		{
			if (ticket.getUser().getId() != null)
			{
				final User user = userService.getById(ticket.getUser().getId());
				NavigableSet<Ticket> tick = new TreeSet<>();
				tick.add(ticket);
				user.setTickets(tick);
			}
		}
	}

	@Nonnull
	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull final Event event, @Nonnull final LocalDateTime dateTime)
	{
		// ??

		/*Set<Ticket> result = new TreeSet<>();
		if (event.getAirDates().contains(dateTime)){
			result.add(event)
		}*/
		return null;
	}
}
