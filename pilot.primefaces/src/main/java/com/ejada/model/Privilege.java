package com.ejada.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ejada.enums.PrivilegeEnum;

@Entity
@Table(name="PRIVILEGE")
public class Privilege {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "name", nullable = false)
	@Enumerated(EnumType.STRING)
	private PrivilegeEnum name;
	
	
}
