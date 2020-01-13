package cn.edu.jxust.sort.service.impl;

import cn.edu.jxust.sort.entity.po.Account;
import cn.edu.jxust.sort.repository.AccountRepository;
import cn.edu.jxust.sort.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * @author: ddh
 * @data: 2020/1/3 11:30
 * @description
 **/
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getLogin(String userName, String userPwd) {
        return accountRepository.findByUserNameAndUserPwd(userName, userPwd);
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Integer deleteAccountByUserId(String userId) {
        return accountRepository.deleteByUserId(userId);
    }
}
