package com.circle.dao;

import com.circle.vo.AccountModel;
import com.circle.vo.AddressModel;
import com.circle.vo.StaticsDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDAO extends BaseDAO<AddressModel> {

    void insertBatch(List<AddressModel> lists);

    int findAddressPage(@Param("search") String search, @Param("state") int state, @Param("type") int type);

    List<AddressModel> findAddressListByPage(@Param("search") String search, int i, int defaultLine, @Param("state") int state, @Param("type") int type);

    List<AddressModel> findAddressList(@Param("search") String search, int i, int defaultLine, @Param("state") String state, @Param("type") int type);

    List<AddressModel> findByName(@Param("address") String address, @Param("state") String state);

    AddressModel findById(@Param("id") int id, @Param("state") String state);

    List<AddressModel> findByLikeName(@Param("address") String address, @Param("state") String state);

    List<AddressModel> findByUnParse(@Param("type") int type, @Param("state") String state);

    List<StaticsDto> getAllRegions(int type);

    List<StaticsDto> getNumberByRegion(int type, String region);

    int getRegionPrecision(@Param("type") int type, @Param("state") int state, @Param("time") String end);

    int getFunctionPrecision(@Param("type") int type, @Param("state") int state, @Param("time") String end);

    List<AddressModel> getDataByTypeAndTime(@Param("type") int type, @Param("time") String end);

    


}
