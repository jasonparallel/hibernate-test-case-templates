package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.PersistenceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		final Image image = new Image();
	    image.setId(1L);
	    image.setContent(new byte[] {1, 2, 3});
	    entityManager.persist(image);
	    
		entityManager.getTransaction().commit();
		entityManager.close();
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.clear();
		entityManager.getTransaction().begin();
		
		final Image image2 = entityManager.find(Image.class, 1L);
		Assert.assertArrayEquals(new byte[] {1, 2, 3}, image2.getContent());
		image2.setContent(new byte[] {1, 2, 3, 4});
					
		entityManager.getTransaction().commit();
		entityManager.close();
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Image image3 = entityManager.find(Image.class, 1L);
		Assert.assertArrayEquals(new byte[] {1, 2, 3, 4}, image3.getContent());
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
