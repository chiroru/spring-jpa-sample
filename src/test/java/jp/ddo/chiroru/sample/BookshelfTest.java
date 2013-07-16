package jp.ddo.chiroru.sample;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jp.ddo.chiroru.sample.test.unit.H2DatabaseServerResource;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/root-Context.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class BookshelfTest {

    @PersistenceContext
    private EntityManager em;

    @ClassRule
    public static H2DatabaseServerResource r = new H2DatabaseServerResource();

    @Test
    public void データを登録()
            throws Exception {
        Bookshelf b = new Bookshelf();
        b.setName("名前");
        b.setDescription("説明");
        b.setCreatedAt(Timestamp.valueOf("2013-07-16 13:31:00"));
        b.setCreatedUser("作成者");
        b.setUpdatedAt(Timestamp.valueOf("2013-07-16 13:3:01"));
        b.setUpdatedUser("更新者");
        em.persist(b);

        Query query = em.createQuery("from Bookshelf");
        @SuppressWarnings("unchecked")
        List<Bookshelf> l = query.getResultList();
        assertThat(l.size(), is(1));
    }
}
