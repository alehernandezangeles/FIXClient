package cencor.meif.fix.client.jpa.entities.test;

import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by mhernandez on 5/29/15.
 */
public class CatEstatus {

    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeClass
    public static void init() {
         emf = Persistence.createEntityManagerFactory("jpaFixClientPU");
    }

    @AfterClass
    public static void free() {
        emf.close();
    }

    @Before
    public void initM() {
        em = emf.createEntityManager();
    }

    @After
    public void freeM() {
        em.close();
    }

    @Test
    public void findById() {
        CatEstatusEntity catEstatusEntity = em.find(CatEstatusEntity.class, 1);
        System.out.println(catEstatusEntity);
    }

}
