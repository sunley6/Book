package com.java456.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 
 * ## 后吧 用户  管理 网站的用户    
 * 
 */
@Entity
@Table(name = "t_a_manager")
public class Manager {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull(message="用户名不能为空！")
	@Column(length=30)
	private  String name;//用户名

	@NotNull(message="电话")
	@Column(length=200)
	private  String phone;

	@NotNull(message="身份证号")
	@Column(length=200)
	private  String cardID;
	@ManyToOne
	@JoinColumn(name="roleId")
	private Role role; // 角色  
	@Column(length=200)
	private  String pwd;
	@NotNull(message="真实姓名不能为空！")
	@Column(length=200)
	private  String trueName;
	
	
	@Column(length=200)
	private  String remark;
	@NotNull(message="排序号不能为空！")
	@Column(length=10)
	private Integer orderNo;
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createDateTime;//创建时间
	@Temporal(TemporalType.TIMESTAMP) 
	private Date updateDateTime;//修改时间
	
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

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

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	@Override
	public String toString() {
		return "Manager{" +
				"id=" + id +
				", name='" + name + '\'' +
				", phone='" + phone + '\'' +
				", cardID='" + cardID + '\'' +
				", role=" + role +
				", pwd='" + pwd + '\'' +
				", trueName='" + trueName + '\'' +
				", remark='" + remark + '\'' +
				", orderNo=" + orderNo +
				", createDateTime=" + createDateTime +
				", updateDateTime=" + updateDateTime +
				'}';
	}
}
