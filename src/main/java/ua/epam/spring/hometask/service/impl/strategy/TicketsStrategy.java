package ua.epam.spring.hometask.service.impl.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.IDiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class TicketsStrategy implements IDiscountService {

    private static final Integer DISCOUNT = Integer.valueOf(50);
    private static  final  Integer NUMBER_TICKET_TO_DISCOUNT = Integer.valueOf(9);

    @Override
    public Double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {

        Double discount = Double.valueOf(0);

        if (numberOfTickets > NUMBER_TICKET_TO_DISCOUNT){
            Long count = numberOfTickets / NUMBER_TICKET_TO_DISCOUNT;
            discount = ((event.getBasePrice() / 100) * DISCOUNT) * count;
        }
        return discount;
    }
}
