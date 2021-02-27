package com.seckill.service;

import com.seckill.dao.UserDao;
import com.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getById(int id)
    {
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User u1= new User();
        u1.setId(3);
        u1.setName("3333");
        userDao.insert(u1);

        User u2= new User();
        u1.setId(1);
        u1.setName("1111");
        userDao.insert(u1);
        String a= new String("12344");

        return true;

    }
}
