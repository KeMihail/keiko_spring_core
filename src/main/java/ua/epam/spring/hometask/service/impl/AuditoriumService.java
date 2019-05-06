package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.IAuditoriumService;
import ua.epam.spring.hometask.util.DomainMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

public class AuditoriumService implements IAuditoriumService {

    @Autowired
    private DomainMap domainMap;

    private Map<String, Auditorium> auditoriumMap = domainMap.getauditoriumMap();

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {

        return auditoriumMap.values().stream().collect(toCollection(HashSet::new));
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriumMap.get(name);
    }
}
