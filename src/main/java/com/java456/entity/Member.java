package com.java456.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 这是我们的会员表。  会员登录  注册 用
 * @author Administrator
 *
 */
@Entity
@Table(name="t_member")
public class Member {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=20)
	@NotEmpty(message="帐号不能为空！")
	private String name; //帐号
	
	@Column(length=20)
	@NotEmpty(message="密码不能为空！")
	private String pwd; //密码
	
	@Column(length=20)
	@NotEmpty(message="姓名不能为空！")
	private String trueName; //姓名
	
	@Column(length=20)
	@NotEmpty(message="电话 不能为空！")
	private String phone; //电话 
	
	
	
	
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createDateTime;//创建时间


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


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateDateTime() {
		return createDateTime;
	}


	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	
	
	
	
	
	
}
