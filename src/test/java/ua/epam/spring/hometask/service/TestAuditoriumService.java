package ua.epam.spring.hometask.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context.xml")
public class TestAuditoriumService {

    @Autowired
    private IAuditoriumService service;

    @Resource(name = "redAuditorium")
    private Auditorium redAuditorium;

    @Resource(name = "greenAuditorium")
    private Auditorium greenAuditorium;

    @Resource(name = "blackAuditorium")
    private Auditorium blackAuditorium;

    @Test
    public void test(){

        Assert.assertTrue(service.getAll().size()==3);
        Assert.assertTrue(service.getAll().contains(redAuditorium));
        Assert.assertTrue(service.getAll().contains(greenAuditorium));
        Assert.assertTrue(service.getAll().contains(blackAuditorium));

        Assert.assertEquals(redAuditorium,service.getByName("Red"));
        Assert.assertEquals(greenAuditorium,service.getByName("Green"));
        Assert.assertEquals(blackAuditorium,service.getByName("Black"));
    }
}
