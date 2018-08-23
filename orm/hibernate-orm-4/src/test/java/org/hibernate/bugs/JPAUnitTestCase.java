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
		
		Image image = new Image();
	    image.setId(1L);
	    image.setContent(new byte[] {1, 2, 3});
	    entityManager.persist(image);
	    
		entityManager.getTransaction().commit();
		entityManager.close();
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.clear();
		entityManager.getTransaction().begin();
		
		//connectionProvider.clear();
		Image image2 = entityManager.find(Image.class, 1L);
		PersistenceUnitUtil.
					Assert.assertEquals(1L, image2.getId().longValue());
		entityManager.getTransaction().commit();
		entityManager.close();
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		//connectionProvider.clear();
		Image image3 = entityManager.find(Image.class, 1L);
	//	+			List<PreparedStatement> preparedStatements = connectionProvider.getPreparedStatements();
		//+			assertEquals( 1, preparedStatements.size() );
		//+			assertNotNull( connectionProvider.getPreparedStatement( "select image0_.id as id1_0_0_ from Image image0_ where image0_.id=?" ) );
					Assert.assertArrayEquals(new byte[] {1, 2, 3}, image3.getContent());
		//+			preparedStatements = connectionProvider.getPreparedStatements();
		//+			assertEquals( 2, preparedStatements.size() );
	//	+			assertNotNull( connectionProvider.getPreparedStatement( "select image_.content as content2_0_ from Image image_ where image_.id=?" ) );
		//+		});
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
