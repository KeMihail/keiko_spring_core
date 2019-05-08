package ua.epam.spring.hometask.service.impl.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.IDiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class BirthdayStrategy implements IDiscountService {
    private static final Integer DISCOUNT = Integer.valueOf(5);

    @Override
    public Double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {

        final Double discount = ((event.getBasePrice() * numberOfTickets) / 100) * DISCOUNT;

        return discount;
    }
}
