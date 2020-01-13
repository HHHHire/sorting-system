package cn.edu.jxust.sort.service.impl;

import cn.edu.jxust.sort.entity.po.Admin;
import cn.edu.jxust.sort.repository.AdminRepository;
import cn.edu.jxust.sort.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: ddh
 * @data: 2020/1/3 11:21
 * @description
 **/
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin createAdmin(Admin admin) {
        Optional<Admin> adminOptional = adminRepository.findByAdminName(admin.getAdminName());
        if (adminOptional.isPresent()) {
            return null;
        } else {
            return adminRepository.save(admin);
        }
    }

    @Override
    public Admin getAdmin(String adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }

    @Override
    public List<Admin> getAllAdmin() {
        return adminRepository.findAll();
    }

    @Override
    public Integer deleteAdminById(String adminId) {
        return adminRepository.deleteByAdminId(adminId);
    }
}
