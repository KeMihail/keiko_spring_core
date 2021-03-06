package ua.epam.spring.hometask.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.AmbiguousIdentifierException;
import ua.epam.spring.hometask.exceptions.UnknownIdentifierException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static java.util.stream.Collectors.toCollection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test.xml")
public class TestUserService
{
	private static final Long FAIL_ID = Long.valueOf(2);
	private static final String FAIL_EMAIL = "fail@epam.com";

	@Autowired
	private IUserService service;

	@Resource(name = "testUser")
	private User user;

	@Before
	public void setUp()
	{
		service.save(user);
	}

	@Test
	public void testCrudMethods() throws UnknownIdentifierException, AmbiguousIdentifierException
	{

		final User userById = service.getById(user.getId());
		Assert.assertNotNull(userById);
		Assert.assertEquals(user.getId(), userById.getId());
		Assert.assertEquals(user.getFirstName(), userById.getFirstName());
		Assert.assertEquals(user.getLastName(), userById.getLastName());
		Assert.assertEquals(user.getEmail(), userById.getEmail());

		final User userByEmail = service.getUserByEmail(user.getEmail());
		Assert.assertNotNull(userByEmail);
		Assert.assertEquals(user.getId(), userByEmail.getId());
		Assert.assertEquals(user.getFirstName(), userByEmail.getFirstName());
		Assert.assertEquals(user.getLastName(), userByEmail.getLastName());
		Assert.assertEquals(user.getEmail(), userByEmail.getEmail());

		final List<User> users = service.getAll().stream().collect(toCollection(ArrayList::new));
		Assert.assertNotNull(users);
		Assert.assertTrue(users.size() == 1);
		Assert.assertTrue(users.contains(user));

		service.remove(user);
		Assert.assertTrue(service.getAll().stream().collect(toCollection(ArrayList::new)).isEmpty());
	}

	@Test(expected = UnknownIdentifierException.class)
	public void failSearchById() throws UnknownIdentifierException, AmbiguousIdentifierException{
		final User user = service.getById(FAIL_ID);
	}

	@Test(expected = UnknownIdentifierException.class)
	public void failSearchByEmail() throws UnknownIdentifierException, AmbiguousIdentifierException{
		final User user = service.getUserByEmail(FAIL_EMAIL);
	}

	@After
	public void cleaning(){
		if (service.getAll().stream().collect(toCollection(ArrayList::new)).contains(user)){
			service.remove(user);
		}
	}
}
