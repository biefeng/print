package com.example.print.dao;

import com.example.print.dto.TemplateProp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/*
 *@Author BieFeNg
 *@Date 2019/7/5 10:37
 *@DESC
 */
public interface PrintTemplateDao {

    List<Map<String, String>> listTemplate(@Param("start") int start, @Param("limit") int limit);

    void saveTemplate(@Param("templateName") String templateName, @Param("prop") String prop);
}
