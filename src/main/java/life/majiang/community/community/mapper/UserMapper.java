package life.majiang.community.community.mapper;

import life.majiang.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user(NAME,ACCOUNT_ID,TOKEN,GMT_MODIFIED,GMT_CREATE) values(#{name},#{accountId},#{token},#{gmtModified},#{gmtCreate})")
    void insert(User user);
}
