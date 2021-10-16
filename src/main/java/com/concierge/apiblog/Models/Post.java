package com.concierge.apiblog.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="posts")
public class Post implements Serializable {

    private static  final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@NotEmpty
    private String title;

	@NotEmpty
	private String resume;

	@NotEmpty
	@Column(columnDefinition="TEXT")
	private String content;

	@Column(columnDefinition="TEXT")
	private String image;

	@NotEmpty
	private String published_date;

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

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPublished_date() {
		return published_date;
	}

	public void setPublished_date(String published_date) {
		this.published_date = published_date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Post post = (Post) o;
		return id == post.id && Objects.equals(title, post.title) && Objects.equals(resume, post.resume) && Objects.equals(content, post.content) && Objects.equals(image, post.image) && Objects.equals(published_date, post.published_date);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, resume, content, image, published_date);
	}

	@Override
	public String toString() {
		return "Post{" +
				"id=" + id +
				", title='" + title + '\'' +
				'}';
	}
}
