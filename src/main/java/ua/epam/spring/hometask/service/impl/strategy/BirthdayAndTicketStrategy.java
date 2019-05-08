package ua.epam.spring.hometask.service.impl.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.IDiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class BirthdayAndTicketStrategy implements IDiscountService {

    private IDiscountService birthdayStrategy;

    private IDiscountService ticketsStrategy;

    public void setBirthdayStrategy(IDiscountService birthdayStrategy) {
        this.birthdayStrategy = birthdayStrategy;
    }

    public void setTicketsStrategy(IDiscountService ticketsStrategy) {
        this.ticketsStrategy = ticketsStrategy;
    }

    @Override
    public Double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {

        final Double birthdayDiscount = birthdayStrategy.getDiscount(user, event, airDateTime, numberOfTickets);
        final Double ticketsDiscount = ticketsStrategy.getDiscount(user, event, airDateTime, numberOfTickets);

        if (birthdayDiscount > ticketsDiscount)
        {
            return birthdayDiscount;
        }

        return ticketsDiscount;
    }
}
