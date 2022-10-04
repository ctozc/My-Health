package com.zc.dao;

import com.zc.entity.Record;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordDao {
    void insert(Record record);
    void delete(Long id);
    void update(@Param("record") Record record, @Param("id") Long id);
    List<Record> batchQueryByUserId(Long userId);
}
