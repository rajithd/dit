package com.dit.service.impl;

import com.dit.Menu;
import com.dit.repository.MenuRepository;
import com.dit.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }


    @Override
    public Menu findById(String id) {
        return menuRepository.findOne(id);
    }


    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
