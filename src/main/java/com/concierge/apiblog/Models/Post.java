package com.concierge.apiblog.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="posts")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
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

	private int views;

	@ManyToMany
	@JoinTable(
			name = "post_category",
			joinColumns = @JoinColumn(name = "post_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories;

	@ManyToOne
	private Author author;

	public Post() {
		this.views = 0;
	}

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

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public int getViews() {
		return views;
	}

	public void setViews() {
		this.views = this.views + 1;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
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
