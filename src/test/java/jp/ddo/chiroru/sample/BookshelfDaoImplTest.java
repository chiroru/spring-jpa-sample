package jp.ddo.chiroru.sample;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.List;

import jp.ddo.chiroru.utils.test.unit.H2DatabaseServerResource;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/root-Context.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class BookshelfDaoImplTest {

    @Autowired
    private BookshelfDao dao;

    @ClassRule
    public static H2DatabaseServerResource r = new H2DatabaseServerResource();

    @Test
    public void Bookshelfの情報がDBに登録される()
            throws Exception {
        Bookshelf b = new Bookshelf();
        b.setName("名前");
        b.setDescription("説明");
        b.setCreatedAt(Timestamp.valueOf("2013-07-16 13:31:00"));
        b.setCreatedUser("作成者");
        b.setUpdatedAt(Timestamp.valueOf("2013-07-16 13:3:01"));
        b.setUpdatedUser("更新者");
        dao.create(b);

        List<Bookshelf> l = dao.findAll();
        assertThat(l.size(), is(1));
    }
}
