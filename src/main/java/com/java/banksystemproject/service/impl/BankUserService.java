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
    public void register(String userName, String passWord) {
        Optional<BankUser> entity = bankUserDao.get(userName);

        if(entity.isPresent())
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_USER_NAME_ALREADY_EXIST);

        String hashedPassword = BCrypt.hashpw(passWord, BCrypt.gensalt());
        BankUser user = BankUser.builder().userName(userName).passWord(hashedPassword).build();
        bankUserDao.save(user);
    }

    @Override
    public BankUser authenticate(String userName, String passWord) {
        Optional<BankUser> user = bankUserDao.get(userName);

        if(user.isEmpty())
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_USER_NOT_EXIST);

        if(BCrypt.checkpw(passWord, user.get().getPassWord()))
            return user.get();

        throw new IllegalArgumentException(ExceptionMessageCodes.BSS_USER_NOT_EXIST);
    }

}
