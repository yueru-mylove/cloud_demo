package com.voiceai.cloud.config.security;

import com.voiceai.cloud.bean.DevUser;
import com.voiceai.cloud.constant.TableFieldConstant;
import com.voiceai.cloud.constant.TableNameConstant;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月15日 14:53:00
 */
public class CustomizeUserDetailServiceImpl implements UserDetailsService {

    private final MongoTemplate mongoTemplate;

    public CustomizeUserDetailServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<DevUser> users = mongoTemplate.find(Query.query(Criteria.where(TableFieldConstant.UID).is(s)), DevUser.class, TableNameConstant.TBL_DEV_USER);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("user name not found");
        }
        for (DevUser user : users) {
            List<String> roles = user.getRoles();
            RoledUserDetail detail = new RoledUserDetail();
            detail.setUsername(user.getUid());
            detail.setPassword(user.getPwd());
            detail.setRoles(roles);
            detail.setId(user.getId());
            detail.setLocked(user.getLocked());
            detail.setExpireTime(user.getExpireTime());
            return detail;
        }
        throw new UsernameNotFoundException("user name not found");
    }
}
