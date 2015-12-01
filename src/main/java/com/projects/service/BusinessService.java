package com.projects.service;

import com.projects.model.Business;

import java.util.List;

/**
 * Created by Lazarenko on 10.11.2015.
 */
public interface BusinessService
{
    public List<Business> getAll();
    public List<Business> getDone();
    public List<Business> getNotDone();
    public Business getById(Integer id);
    public void add(Business business);
    public void delete(Integer id);
    public void edit(Business business);
}
