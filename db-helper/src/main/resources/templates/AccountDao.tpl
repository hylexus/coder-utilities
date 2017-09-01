package templates;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface AccountDao {
	E findByPrimaryKey(E key);
}