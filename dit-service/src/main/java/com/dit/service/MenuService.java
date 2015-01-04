package com.dit.service;

import com.dit.Menu;

import java.util.List;

public interface MenuService {

    public Menu save(Menu menu);

    public Menu findById(String id);

    public List<Menu> findAll();

}
