package com.java.banksystemproject.service.impl;


import com.java.banksystemproject.dao.IBankUserDao;
import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.service.IBankUserService;
import com.java.banksystemproject.service.exception.ExceptionMessageCodes;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@RequiredArgsConstructor
public class BankUserService implements IBankUserService {

    private final IBankUserDao bankUserDao;

    @Override
    public void register(BankUser user) {
        Optional<BankUser> entity = bankUserDao.get(user.getUsername());

        if(entity.isPresent())
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_USER_NAME_ALREADY_EXIST);

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        bankUserDao.save(user);
    }

    @Override
    public BankUser authenticate(BankUser user){
        Optional<BankUser> entity = bankUserDao.get(user.getUsername());

        if(entity.isEmpty())
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_USER_NOT_EXIST);

        if(BCrypt.checkpw(user.getPassword(), entity.get().getPassword()))
            return entity.get();

        throw new IllegalArgumentException(ExceptionMessageCodes.BSS_USER_NOT_EXIST);
    }

}
