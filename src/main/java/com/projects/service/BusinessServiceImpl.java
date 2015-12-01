package com.projects.service;

import com.projects.model.Business;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.dao.BusinessDAO;

/**
 * Created by Lazarenko on 10.11.2015.
 */
@Service
public class BusinessServiceImpl implements BusinessService
{
    private BusinessDAO businessDAO;

    public void setBusinessDAO(BusinessDAO businessDAO)
    {
        this.businessDAO = businessDAO;
    }

    @Transactional
    public List<Business> getAll()
    {

        return this.businessDAO.getAll();
    }

    @Transactional
    public List<Business> getDone()
    {
        return this.businessDAO.getDone();
    }

    @Transactional
    public List<Business> getNotDone()
    {
        return this.businessDAO.getNotDone();
    }

    @Transactional
    public Business getById(Integer id)
    {
        return this.businessDAO.getById(id);
    }

    @Transactional
    public void add(Business business)
    {
        this.businessDAO.add(business);
    }

    @Transactional
    public void delete(Integer id)
    {
        this.businessDAO.delete(id);
    }

    @Transactional
    public void edit(Business business)
    {
        this.businessDAO.edit(business);
    }
}
