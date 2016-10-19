package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.entity.slim.ChatClass;

/**
 * The persistent class for the rel_chat_class_user database table.
 * 
 */
@Entity
@Table(name = "rel_chat_class_user")
@NamedQuery(name = "RelChatClassUser.findAll", query = "SELECT r FROM RelChatClassUser r")
public class RelChatClassUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "chat_class_id", nullable = false)
	private Integer chatClassId;

	@Column(nullable = false)
	private Integer type;

	@Column(name = "user_id", nullable = false)
	private Integer userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_class_id", insertable = false, updatable = false)
	private ChatClass chatClass;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private MdUser MdUser;

	public RelChatClassUser() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChatClassId() {
		return this.chatClassId;
	}

	public void setChatClassId(Integer chatClassId) {
		this.chatClassId = chatClassId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public MdUser getMdUser() {
		return MdUser;
	}

	public void setMdUser(MdUser mdUser) {
		MdUser = mdUser;
	}

	public ChatClass getChatClass() {
		return chatClass;
	}

	public void setChatClass(ChatClass chatClass) {
		this.chatClass = chatClass;
	}

}