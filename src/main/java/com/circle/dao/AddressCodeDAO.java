package com.circle.dao;

import com.circle.vo.AddressCodeModel;
import com.circle.vo.AddressModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressCodeDAO extends BaseDAO<AddressCodeModel> {

    void insertBatch(List<AddressCodeModel> lists);

    List<AddressCodeModel> findAll();

    List<AddressCodeModel> findByParentCode(@Param("region_code") String region_code);

    List<AddressCodeModel> findByOnlyName(String name);

    AddressCodeModel findByName(@Param("name") String name, @Param("level") String level, @Param("description") String description, @Param("type") int type);

    List<AddressCodeModel> findByState(@Param("name") String name, @Param("level") String level, @Param("description") String description, @Param("type") int type, @Param("state") int state);

    AddressCodeModel findByCode(String parent_code);

    void insert(String parent_name, String name, String region_code, String level);

    void insertRoad(AddressCodeModel addressCodeModel);

    void setDesc(String desc, int id);

    List<AddressCodeModel> findByAreaAndName(@Param("name") String name, @Param("description") String description);

    List<AddressCodeModel> findByAreaAndNameAndLevel(@Param("name") String name, @Param("description") String description, @Param("level") String level);

    /**
     * 将xxx村委会转换成xxx
     */
    List<AddressCodeModel> selectByLike();

    List<AddressCodeModel> selectByLevel(int level);

    List<AddressCodeModel> selectByLevelAndType(@Param("level") int level, @Param("type") int type);


}
