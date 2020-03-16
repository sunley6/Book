package com.java456.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 
 * ## 后吧 用户  管理 网站的用户    
 * 
 */
@Entity
@Table(name = "t_b_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull(message="用户名不能为空！")
	@Column(length=30)
	private  String name;//用户名

	@Column(length=200)
	private  String pwd;
	@NotNull(message="真实姓名不能为空！")
	@Column(length=200)
	private  String trueName;

	@Column(length=200)
	private  String sex;

	@NotNull(message="电话")
	@Column(length=200)
	private  String phone;

    @NotNull(message="身份证号")
    @Column(length=200)
    private  String cardID;

	@Column(length=200)
	private  String mail;

	@Column(length=200)
	private  String remark;

	@Temporal(TemporalType.TIMESTAMP) 
	private Date createDateTime;//创建时间

	@Column(length=200,columnDefinition = "int default 100")
	private  Integer credit;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", pwd='" + pwd + '\'' +
				", trueName='" + trueName + '\'' +
				", sex='" + sex + '\'' +
				", phone='" + phone + '\'' +
				", cardID='" + cardID + '\'' +
				", mail='" + mail + '\'' +
				", remark='" + remark + '\'' +

				", createDateTime=" + createDateTime +
				", credit=" + credit +
				'}';
	}
}
