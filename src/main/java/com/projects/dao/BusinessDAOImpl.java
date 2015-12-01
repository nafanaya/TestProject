package com.projects.dao;

import com.projects.model.Business;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Lazarenko on 10.11.2015.
 */

@Repository
public class BusinessDAOImpl implements BusinessDAO {
    private static final Logger logger = LoggerFactory.getLogger(BusinessDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public List<Business> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Business");
        List<Business> businesses = query.list();

        for (Business business : businesses) {
            logger.info(": " + business);
        }
        return businesses;
    }

    @SuppressWarnings("unchecked")
    public List<Business> getDone() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Business> businesses = session.createQuery("from Business where isDo = true").list();


        for (Business business : businesses) {
            logger.info(": " + business);
        }
        return businesses;
    }

    @SuppressWarnings("unchecked")
    public List<Business> getNotDone() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Business> businesses = session.createQuery("from Business where isDo = false").list();

        for (Business business : businesses) {
            logger.info(": " + business);
        }
        return businesses;
    }

    @SuppressWarnings("unchecked")
    public Business getById(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        Business business = (Business) session.load(Business.class, new Integer(id));
        logger.info("Business get successfully, Details: " + business);
        return business;
    }

    public void add(Business business) {
        Session session = this.sessionFactory.getCurrentSession();

        session.persist(business);
        logger.info("Business saved successfully, Details: " + business);
    }

    public void delete(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        Business business = (Business) session.load(Business.class, new Integer(id));
        if (business != null) {
            session.delete(business);
        }
        logger.info("Business deleted successfully, Details: " + business);
    }

    public void edit(Business business) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(business);
        logger.info("Business edited successfully, Details: " + business);
    }
}
