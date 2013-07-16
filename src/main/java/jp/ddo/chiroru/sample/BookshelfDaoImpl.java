package jp.ddo.chiroru.sample;

import org.springframework.stereotype.Repository;

@Repository
public class BookshelfDaoImpl
        extends AbstractJpaDAO<Bookshelf>
        implements BookshelfDao {

    public BookshelfDaoImpl() {
        setClazz(Bookshelf.class);
    }
}
