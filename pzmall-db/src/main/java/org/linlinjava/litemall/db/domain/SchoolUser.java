package org.linlinjava.litemall.db.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class SchoolUser {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.usertype
     *
     * @mbg.generated
     */
    private String usertype;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.school
     *
     * @mbg.generated
     */
    private String school;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.faculty
     *
     * @mbg.generated
     */
    private String faculty;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.department
     *
     * @mbg.generated
     */
    private String department;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.phone
     *
     * @mbg.generated
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.idcard
     *
     * @mbg.generated
     */
    private String idcard;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.sex
     *
     * @mbg.generated
     */
    private String sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.age
     *
     * @mbg.generated
     */
    private Integer age;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.birthday
     *
     * @mbg.generated
     */
    private String birthday;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.community
     *
     * @mbg.generated
     */
    private String community;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.liveaddress
     *
     * @mbg.generated
     */
    private String liveaddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.arrivedate
     *
     * @mbg.generated
     */
    private LocalDateTime arrivedate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.fromprovince
     *
     * @mbg.generated
     */
    private String fromprovince;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.fromcity
     *
     * @mbg.generated
     */
    private String fromcity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.fromdistrict
     *
     * @mbg.generated
     */
    private String fromdistrict;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.ifwh
     *
     * @mbg.generated
     */
    private String ifwh;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.whtime
     *
     * @mbg.generated
     */
    private String whtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.livetype
     *
     * @mbg.generated
     */
    private String livetype;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.addtime
     *
     * @mbg.generated
     */
    private LocalDateTime addtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.modifytime
     *
     * @mbg.generated
     */
    private LocalDateTime modifytime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.fromother
     *
     * @mbg.generated
     */
    private String fromother;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.iftouch
     *
     * @mbg.generated
     */
    private String iftouch;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.area
     *
     * @mbg.generated
     */
    private String area;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school_user.street
     *
     * @mbg.generated
     */
    private String street;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.id
     *
     * @return the value of school_user.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.id
     *
     * @param id the value for school_user.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.usertype
     *
     * @return the value of school_user.usertype
     *
     * @mbg.generated
     */
    public String getUsertype() {
        return usertype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.usertype
     *
     * @param usertype the value for school_user.usertype
     *
     * @mbg.generated
     */
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.school
     *
     * @return the value of school_user.school
     *
     * @mbg.generated
     */
    public String getSchool() {
        return school;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.school
     *
     * @param school the value for school_user.school
     *
     * @mbg.generated
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.faculty
     *
     * @return the value of school_user.faculty
     *
     * @mbg.generated
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.faculty
     *
     * @param faculty the value for school_user.faculty
     *
     * @mbg.generated
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.department
     *
     * @return the value of school_user.department
     *
     * @mbg.generated
     */
    public String getDepartment() {
        return department;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.department
     *
     * @param department the value for school_user.department
     *
     * @mbg.generated
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.name
     *
     * @return the value of school_user.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.name
     *
     * @param name the value for school_user.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.phone
     *
     * @return the value of school_user.phone
     *
     * @mbg.generated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.phone
     *
     * @param phone the value for school_user.phone
     *
     * @mbg.generated
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.idcard
     *
     * @return the value of school_user.idcard
     *
     * @mbg.generated
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.idcard
     *
     * @param idcard the value for school_user.idcard
     *
     * @mbg.generated
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.sex
     *
     * @return the value of school_user.sex
     *
     * @mbg.generated
     */
    public String getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.sex
     *
     * @param sex the value for school_user.sex
     *
     * @mbg.generated
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.age
     *
     * @return the value of school_user.age
     *
     * @mbg.generated
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.age
     *
     * @param age the value for school_user.age
     *
     * @mbg.generated
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.birthday
     *
     * @return the value of school_user.birthday
     *
     * @mbg.generated
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.birthday
     *
     * @param birthday the value for school_user.birthday
     *
     * @mbg.generated
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.community
     *
     * @return the value of school_user.community
     *
     * @mbg.generated
     */
    public String getCommunity() {
        return community;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.community
     *
     * @param community the value for school_user.community
     *
     * @mbg.generated
     */
    public void setCommunity(String community) {
        this.community = community;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.liveaddress
     *
     * @return the value of school_user.liveaddress
     *
     * @mbg.generated
     */
    public String getLiveaddress() {
        return liveaddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.liveaddress
     *
     * @param liveaddress the value for school_user.liveaddress
     *
     * @mbg.generated
     */
    public void setLiveaddress(String liveaddress) {
        this.liveaddress = liveaddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.arrivedate
     *
     * @return the value of school_user.arrivedate
     *
     * @mbg.generated
     */
    public LocalDateTime getArrivedate() {
        return arrivedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.arrivedate
     *
     * @param arrivedate the value for school_user.arrivedate
     *
     * @mbg.generated
     */
    public void setArrivedate(LocalDateTime arrivedate) {
        this.arrivedate = arrivedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.fromprovince
     *
     * @return the value of school_user.fromprovince
     *
     * @mbg.generated
     */
    public String getFromprovince() {
        return fromprovince;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.fromprovince
     *
     * @param fromprovince the value for school_user.fromprovince
     *
     * @mbg.generated
     */
    public void setFromprovince(String fromprovince) {
        this.fromprovince = fromprovince;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.fromcity
     *
     * @return the value of school_user.fromcity
     *
     * @mbg.generated
     */
    public String getFromcity() {
        return fromcity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.fromcity
     *
     * @param fromcity the value for school_user.fromcity
     *
     * @mbg.generated
     */
    public void setFromcity(String fromcity) {
        this.fromcity = fromcity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.fromdistrict
     *
     * @return the value of school_user.fromdistrict
     *
     * @mbg.generated
     */
    public String getFromdistrict() {
        return fromdistrict;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.fromdistrict
     *
     * @param fromdistrict the value for school_user.fromdistrict
     *
     * @mbg.generated
     */
    public void setFromdistrict(String fromdistrict) {
        this.fromdistrict = fromdistrict;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.ifwh
     *
     * @return the value of school_user.ifwh
     *
     * @mbg.generated
     */
    public String getIfwh() {
        return ifwh;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.ifwh
     *
     * @param ifwh the value for school_user.ifwh
     *
     * @mbg.generated
     */
    public void setIfwh(String ifwh) {
        this.ifwh = ifwh;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.whtime
     *
     * @return the value of school_user.whtime
     *
     * @mbg.generated
     */
    public String getWhtime() {
        return whtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.whtime
     *
     * @param whtime the value for school_user.whtime
     *
     * @mbg.generated
     */
    public void setWhtime(String whtime) {
        this.whtime = whtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.livetype
     *
     * @return the value of school_user.livetype
     *
     * @mbg.generated
     */
    public String getLivetype() {
        return livetype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.livetype
     *
     * @param livetype the value for school_user.livetype
     *
     * @mbg.generated
     */
    public void setLivetype(String livetype) {
        this.livetype = livetype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.remark
     *
     * @return the value of school_user.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.remark
     *
     * @param remark the value for school_user.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.addtime
     *
     * @return the value of school_user.addtime
     *
     * @mbg.generated
     */
    public LocalDateTime getAddtime() {
        return addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.addtime
     *
     * @param addtime the value for school_user.addtime
     *
     * @mbg.generated
     */
    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.modifytime
     *
     * @return the value of school_user.modifytime
     *
     * @mbg.generated
     */
    public LocalDateTime getModifytime() {
        return modifytime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.modifytime
     *
     * @param modifytime the value for school_user.modifytime
     *
     * @mbg.generated
     */
    public void setModifytime(LocalDateTime modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.fromother
     *
     * @return the value of school_user.fromother
     *
     * @mbg.generated
     */
    public String getFromother() {
        return fromother;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.fromother
     *
     * @param fromother the value for school_user.fromother
     *
     * @mbg.generated
     */
    public void setFromother(String fromother) {
        this.fromother = fromother;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.iftouch
     *
     * @return the value of school_user.iftouch
     *
     * @mbg.generated
     */
    public String getIftouch() {
        return iftouch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.iftouch
     *
     * @param iftouch the value for school_user.iftouch
     *
     * @mbg.generated
     */
    public void setIftouch(String iftouch) {
        this.iftouch = iftouch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.area
     *
     * @return the value of school_user.area
     *
     * @mbg.generated
     */
    public String getArea() {
        return area;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.area
     *
     * @param area the value for school_user.area
     *
     * @mbg.generated
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_user.street
     *
     * @return the value of school_user.street
     *
     * @mbg.generated
     */
    public String getStreet() {
        return street;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_user.street
     *
     * @param street the value for school_user.street
     *
     * @mbg.generated
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table school_user
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", usertype=").append(usertype);
        sb.append(", school=").append(school);
        sb.append(", faculty=").append(faculty);
        sb.append(", department=").append(department);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", idcard=").append(idcard);
        sb.append(", sex=").append(sex);
        sb.append(", age=").append(age);
        sb.append(", birthday=").append(birthday);
        sb.append(", community=").append(community);
        sb.append(", liveaddress=").append(liveaddress);
        sb.append(", arrivedate=").append(arrivedate);
        sb.append(", fromprovince=").append(fromprovince);
        sb.append(", fromcity=").append(fromcity);
        sb.append(", fromdistrict=").append(fromdistrict);
        sb.append(", ifwh=").append(ifwh);
        sb.append(", whtime=").append(whtime);
        sb.append(", livetype=").append(livetype);
        sb.append(", remark=").append(remark);
        sb.append(", addtime=").append(addtime);
        sb.append(", modifytime=").append(modifytime);
        sb.append(", fromother=").append(fromother);
        sb.append(", iftouch=").append(iftouch);
        sb.append(", area=").append(area);
        sb.append(", street=").append(street);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table school_user
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SchoolUser other = (SchoolUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsertype() == null ? other.getUsertype() == null : this.getUsertype().equals(other.getUsertype()))
            && (this.getSchool() == null ? other.getSchool() == null : this.getSchool().equals(other.getSchool()))
            && (this.getFaculty() == null ? other.getFaculty() == null : this.getFaculty().equals(other.getFaculty()))
            && (this.getDepartment() == null ? other.getDepartment() == null : this.getDepartment().equals(other.getDepartment()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getIdcard() == null ? other.getIdcard() == null : this.getIdcard().equals(other.getIdcard()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getAge() == null ? other.getAge() == null : this.getAge().equals(other.getAge()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getCommunity() == null ? other.getCommunity() == null : this.getCommunity().equals(other.getCommunity()))
            && (this.getLiveaddress() == null ? other.getLiveaddress() == null : this.getLiveaddress().equals(other.getLiveaddress()))
            && (this.getArrivedate() == null ? other.getArrivedate() == null : this.getArrivedate().equals(other.getArrivedate()))
            && (this.getFromprovince() == null ? other.getFromprovince() == null : this.getFromprovince().equals(other.getFromprovince()))
            && (this.getFromcity() == null ? other.getFromcity() == null : this.getFromcity().equals(other.getFromcity()))
            && (this.getFromdistrict() == null ? other.getFromdistrict() == null : this.getFromdistrict().equals(other.getFromdistrict()))
            && (this.getIfwh() == null ? other.getIfwh() == null : this.getIfwh().equals(other.getIfwh()))
            && (this.getWhtime() == null ? other.getWhtime() == null : this.getWhtime().equals(other.getWhtime()))
            && (this.getLivetype() == null ? other.getLivetype() == null : this.getLivetype().equals(other.getLivetype()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getAddtime() == null ? other.getAddtime() == null : this.getAddtime().equals(other.getAddtime()))
            && (this.getModifytime() == null ? other.getModifytime() == null : this.getModifytime().equals(other.getModifytime()))
            && (this.getFromother() == null ? other.getFromother() == null : this.getFromother().equals(other.getFromother()))
            && (this.getIftouch() == null ? other.getIftouch() == null : this.getIftouch().equals(other.getIftouch()))
            && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
            && (this.getStreet() == null ? other.getStreet() == null : this.getStreet().equals(other.getStreet()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table school_user
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsertype() == null) ? 0 : getUsertype().hashCode());
        result = prime * result + ((getSchool() == null) ? 0 : getSchool().hashCode());
        result = prime * result + ((getFaculty() == null) ? 0 : getFaculty().hashCode());
        result = prime * result + ((getDepartment() == null) ? 0 : getDepartment().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getIdcard() == null) ? 0 : getIdcard().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getAge() == null) ? 0 : getAge().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getCommunity() == null) ? 0 : getCommunity().hashCode());
        result = prime * result + ((getLiveaddress() == null) ? 0 : getLiveaddress().hashCode());
        result = prime * result + ((getArrivedate() == null) ? 0 : getArrivedate().hashCode());
        result = prime * result + ((getFromprovince() == null) ? 0 : getFromprovince().hashCode());
        result = prime * result + ((getFromcity() == null) ? 0 : getFromcity().hashCode());
        result = prime * result + ((getFromdistrict() == null) ? 0 : getFromdistrict().hashCode());
        result = prime * result + ((getIfwh() == null) ? 0 : getIfwh().hashCode());
        result = prime * result + ((getWhtime() == null) ? 0 : getWhtime().hashCode());
        result = prime * result + ((getLivetype() == null) ? 0 : getLivetype().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getAddtime() == null) ? 0 : getAddtime().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        result = prime * result + ((getFromother() == null) ? 0 : getFromother().hashCode());
        result = prime * result + ((getIftouch() == null) ? 0 : getIftouch().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
        result = prime * result + ((getStreet() == null) ? 0 : getStreet().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table school_user
     *
     * @mbg.generated
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        usertype("usertype", "usertype", "VARCHAR", false),
        school("school", "school", "VARCHAR", false),
        faculty("faculty", "faculty", "VARCHAR", false),
        department("department", "department", "VARCHAR", false),
        name("name", "name", "VARCHAR", true),
        phone("phone", "phone", "VARCHAR", false),
        idcard("idcard", "idcard", "VARCHAR", false),
        sex("sex", "sex", "VARCHAR", false),
        age("age", "age", "INTEGER", false),
        birthday("birthday", "birthday", "VARCHAR", false),
        community("community", "community", "VARCHAR", false),
        liveaddress("liveaddress", "liveaddress", "VARCHAR", false),
        arrivedate("arrivedate", "arrivedate", "TIMESTAMP", false),
        fromprovince("fromprovince", "fromprovince", "VARCHAR", false),
        fromcity("fromcity", "fromcity", "VARCHAR", false),
        fromdistrict("fromdistrict", "fromdistrict", "VARCHAR", false),
        ifwh("ifwh", "ifwh", "VARCHAR", false),
        whtime("whtime", "whtime", "VARCHAR", false),
        livetype("livetype", "livetype", "VARCHAR", false),
        remark("remark", "remark", "VARCHAR", false),
        addtime("addtime", "addtime", "TIMESTAMP", false),
        modifytime("modifytime", "modifytime", "TIMESTAMP", false),
        fromother("fromother", "fromother", "VARCHAR", false),
        iftouch("iftouch", "iftouch", "VARCHAR", false),
        area("area", "area", "VARCHAR", false),
        street("street", "street", "VARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table school_user
         *
         * @mbg.generated
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table school_user
         *
         * @mbg.generated
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table school_user
         *
         * @mbg.generated
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table school_user
         *
         * @mbg.generated
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table school_user
         *
         * @mbg.generated
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table school_user
         *
         * @mbg.generated
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table school_user
         *
         * @mbg.generated
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table school_user
         *
         * @mbg.generated
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table school_user
         *
         * @mbg.generated
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table school_user
         *
         * @mbg.generated
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table school_user
         *
         * @mbg.generated
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table school_user
         *
         * @mbg.generated
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table school_user
         *
         * @mbg.generated
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table school_user
         *
         * @mbg.generated
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table school_user
         *
         * @mbg.generated
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table school_user
         *
         * @mbg.generated
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}