package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.Whuser;
import org.linlinjava.litemall.db.domain.WhuserExample;

public interface WhuserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    long countByExample(WhuserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    int deleteByExample(WhuserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    int insert(Whuser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    int insertSelective(Whuser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    Whuser selectOneByExample(WhuserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    Whuser selectOneByExampleSelective(@Param("example") WhuserExample example, @Param("selective") Whuser.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    List<Whuser> selectByExampleSelective(@Param("example") WhuserExample example, @Param("selective") Whuser.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    List<Whuser> selectByExample(WhuserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    Whuser selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Whuser.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    Whuser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Whuser record, @Param("example") WhuserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Whuser record, @Param("example") WhuserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Whuser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     * 部分值可以设置为空值
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelectiveWithNull(Whuser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table whuser
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Whuser record);
}