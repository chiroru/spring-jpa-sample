package jp.ddo.chiroru.sample;

import java.util.List;

public interface BookshelfDao {

    public Bookshelf findOne(long id);

    public List<Bookshelf> findAll();

    long count();

    List<Bookshelf> findRange(int maxResults, int firstResult);
    
    public void create(Bookshelf bookshelf);

    public Bookshelf update(Bookshelf bookshelf);

    public void delete(Bookshelf bookshelf);

    public void deleteById(long id);
}
