package com.ujiuye.mis.pojo;

import java.util.List;

public class Role {

    private List<Sources> sourcesList;


    public List<Sources> getSourcesList() {
        return sourcesList;
    }

    public void setSourcesList(List<Sources> sourcesList) {
        this.sourcesList = sourcesList;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.roleid
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    private Integer roleid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.rolename
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    private String rolename;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.roledis
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    private String roledis;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.status
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.roleid
     *
     * @return the value of role.roleid
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    public Integer getRoleid() {
        return roleid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.roleid
     *
     * @param roleid the value for role.roleid
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.rolename
     *
     * @return the value of role.rolename
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.rolename
     *
     * @param rolename the value for role.rolename
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.roledis
     *
     * @return the value of role.roledis
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    public String getRoledis() {
        return roledis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.roledis
     *
     * @param roledis the value for role.roledis
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    public void setRoledis(String roledis) {
        this.roledis = roledis == null ? null : roledis.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.status
     *
     * @return the value of role.status
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.status
     *
     * @param status the value for role.status
     *
     * @mbg.generated Tue Sep 22 15:00:02 CST 2020
     */
    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Role{" +
                "sourcesList=" + sourcesList +
                ", roleid=" + roleid +
                ", rolename='" + rolename + '\'' +
                ", roledis='" + roledis + '\'' +
                ", status=" + status +
                '}';
    }
}