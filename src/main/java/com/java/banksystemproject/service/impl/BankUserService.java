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
        Optional<BankUser> entity = bankUserDao.get(user.getUserName());

        if(entity.isPresent())
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_USER_NAME_ALREADY_EXIST);

        user.setPassWord(BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt()));
        bankUserDao.save(user);
    }

    @Override
    public BankUser authenticate(BankUser user){
        Optional<BankUser> entity = bankUserDao.get(user.getUserName());

        if(entity.isEmpty())
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_USER_NOT_EXIST);

        if(BCrypt.checkpw(user.getPassWord(), entity.get().getPassWord()))
            return entity.get();

        throw new IllegalArgumentException(ExceptionMessageCodes.BSS_USER_NOT_EXIST);
    }

}
