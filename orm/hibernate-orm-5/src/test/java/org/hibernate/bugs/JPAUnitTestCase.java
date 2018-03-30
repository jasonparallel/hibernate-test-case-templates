package org.hibernate.bugs;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.collection.AbstractCollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
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
		
		Image image = new Image();
	    image.setId(1L);
	    image.setContent(new byte[] {1, 2, 3});
	    entityManager.persist(image);
	    
		entityManager.getTransaction().commit();
		entityManager.close();
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.clear();
		
		this.clearObjectCache(entityManager);
		
		entityManager.getTransaction().begin();
		
		PersistenceUnitUtil unitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
		Image image2 = entityManager.find(Image.class, 1L);
		Assert.assertFalse(unitUtil.isLoaded(image2, "content"));
		Assert.assertEquals(1L, image2.getId().longValue());
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.clear();
		entityManager.getTransaction().begin();
		
		Image image3 = entityManager.find(Image.class, 1L);
		Assert.assertFalse(unitUtil.isLoaded(image3, "content"));
		Assert.assertArrayEquals(new byte[] {1, 2, 3}, image3.getContent());
		Assert.assertTrue(unitUtil.isLoaded(image3, "content"));
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	private void clearObjectCache(EntityManager em){
		Session s = (Session)em.getDelegate();
		SessionFactory sf = s.getSessionFactory();
		org.hibernate.Cache cache = sf.getCache();

		if (cache != null) {
		    cache.evictAllRegions(); 
		}
	}
}
