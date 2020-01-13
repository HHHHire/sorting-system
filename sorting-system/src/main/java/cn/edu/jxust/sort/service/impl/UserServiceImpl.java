package cn.edu.jxust.sort.service.impl;

import cn.edu.jxust.sort.entity.po.User;
import cn.edu.jxust.sort.repository.UserRepository;
import cn.edu.jxust.sort.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/4 20:12
 * @description
 **/
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Integer deleteUserByEnterpriseId(String enterpriseId) {
        return userRepository.deleteByEnterpriseId(enterpriseId);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<User> getAllUser(String enterpriseId, Pageable pageable) {
        if (enterpriseId == null) {
            return userRepository.findAll(pageable);
        } else {
            return userRepository.findAllByEnterpriseId(enterpriseId, pageable);
        }
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public Integer deleteUserByUserId(String userId) {
        return userRepository.deleteByUserId(userId);
    }

    @Override
    public List<User> getUserByEnterpriseId(String enterpriseId) {
        return userRepository.findAllByEnterpriseId(enterpriseId);
    }
}
