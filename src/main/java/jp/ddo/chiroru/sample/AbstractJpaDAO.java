package jp.ddo.chiroru.sample;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public abstract class AbstractJpaDAO<T extends Serializable> {

    private Class< T > clazz;

    @PersistenceContext
    EntityManager entityManager;

    public final void setClazz( Class< T > clazzToSet ){
        this.clazz = clazzToSet;
    }

    public T findOne( long id ){
        return entityManager.find( clazz, id );
    }

    @SuppressWarnings("unchecked")
    public List< T > findAll(){
        return entityManager.createQuery( "from " + clazz.getName() )
                .getResultList();
    }

    public List<T> findRange(int maxResults, int firstResult) {
        CriteriaQuery<T> cq = entityManager.getCriteriaBuilder().createQuery(clazz);
        cq.select(cq.from(clazz));
        TypedQuery<T> q = entityManager.createQuery(cq);
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
        return q.getResultList();
    }

    public int count() {
        return ((Long) entityManager.createQuery("select count(o) from " + clazz.getSimpleName() + " as o").
                getSingleResult()).intValue();
    }

    public void create( T entity ){
        entityManager.persist( entity );
    }

    public T update( T entity ){
        return entityManager.merge( entity );
    }

    public void delete( T entity ){
        entityManager.remove( entity );
    }

    public void deleteById( long entityId ){
        T entity = findOne( entityId );
        delete( entity );
    }
}
