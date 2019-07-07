package com.voiceai.cloud.service;

import com.google.common.base.Strings;
import com.voiceai.cloud.bean.User;
import com.voiceai.cloud.mapper.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月04日 00:06:00
 */
@Component
public class CustomizeUserServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomizeUserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> users = userMapper.selectByUsername(s);
        if (null == users || users.isEmpty()) {
            throw new UsernameNotFoundException("user name not found");
        }
        for (User user : users) {
            String roleString = user.getRoles();
            Set<GrantedAuthority> roles = new HashSet<>();
            if (!Strings.isNullOrEmpty(roleString)) {
                Arrays.asList(roleString.split(",")).forEach(role -> roles.add(new SimpleGrantedAuthority(role)));
            }
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("user name not found");
    }


/*    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.getActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().stream().forEach(role -> {
            grantedAuthorities.addAll(role.getMenus().stream().filter(menu -> StringUtils.isNotBlank(menu.getPermission())).map(menu -> new SimpleGrantedAuthority(menu.getPermission()))
                    .collect(Collectors.toList()));
        });
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                grantedAuthorities);
    }*/
}
