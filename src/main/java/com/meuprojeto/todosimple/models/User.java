package com.meuprojeto.todosimple.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {
	public static final String TABLE_NAME = "user";
}
