package com.tys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the md_tcp_server database table.
 * 
 */
@Entity
@Table(name="md_tcp_server")
@NamedQuery(name="MdTcpServer.findAll", query="SELECT m FROM MdTcpServer m")
public class MdTcpServer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(nullable=false, length=64)
	private String domain;

	@Column(nullable=false, length=20)
	private String ip;

	@Column(name="online_count", nullable=false)
	private Integer onlineCount;

	@Column(nullable=false)
	private Integer port;

	@Column(nullable=false)
	private Integer status;

	@Column(nullable=false)
	private Integer weight;

	public MdTcpServer() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getOnlineCount() {
		return this.onlineCount;
	}

	public void setOnlineCount(Integer onlineCount) {
		this.onlineCount = onlineCount;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}