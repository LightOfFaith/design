package ${packageName}.repository;

import ${packageName}.entity.${className};
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ${className}Repository extends BaseRepository<${className}, Long> {

}