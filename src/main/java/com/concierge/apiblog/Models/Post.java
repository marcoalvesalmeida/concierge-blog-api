package com.concierge.apiblog.Models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="posts")
public class Post implements Serializable {

    private static  final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
