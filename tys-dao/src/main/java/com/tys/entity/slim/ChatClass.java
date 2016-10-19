package com.tys.entity.slim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the md_chat_class database table.
 * 
 */
@Entity
@Table(name="md_chat_class")
public class ChatClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name="chat_name", nullable=false, length=30)
	private String chatName;

	@Column(name="class_id", nullable=false)
	private Integer classId;



	public ChatClass() {
	}


	public String getChatName() {
		return this.chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public Integer getClassId() {
		return this.classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}



}