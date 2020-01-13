package cn.edu.jxust.sort.runner;

import cn.edu.jxust.sort.entity.po.Account;
import cn.edu.jxust.sort.entity.po.Admin;
import cn.edu.jxust.sort.service.AccountService;
import cn.edu.jxust.sort.service.AdminService;
import cn.edu.jxust.sort.util.EncryptionUtil;
import cn.edu.jxust.sort.util.UUIDUtil;
import cn.edu.jxust.sort.util.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author: ddh
 * @data: 2020/1/7 17:02
 * @description
 **/
@Component
public class InitAdmin implements CommandLineRunner {
    private final static String ADMIN = "admin";
    private final static String PASS = "123456";
    private final static String PHONE = "15311111111";
    private final static String EMAIL = "123456@123.com";

    private final AdminService adminService;
    private final AccountService accountService;

    @Autowired
    public InitAdmin(AdminService adminService, AccountService accountService) {
        this.adminService = adminService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminId = UUIDUtil.getUUID();
        Admin admin = adminService.createAdmin(Admin.builder()
                .adminId(adminId)
                .adminName(ADMIN)
                .adminEmail(EMAIL)
                .adminPhone(PHONE)
                .createTime(System.currentTimeMillis())
                .updateTime(System.currentTimeMillis())
                .build());
        if (admin != null) {
            accountService.createAccount(Account.builder()
                    .userId(adminId)
                    .userName(ADMIN)
                    .userPwd(EncryptionUtil.encrypt(PASS))
                    .userRole(Role.ADMIN)
                    .createTime(System.currentTimeMillis())
                    .updateTime(System.currentTimeMillis())
                    .build());
        }

    }
}
