package com.jy.booksbooksbooks.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="users_books")
public class UserBook {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;
	
    private Boolean owned;
    
    private Boolean completed;
    
    private Long current_page;
    
    private String review;
    
    private Integer rating;
	
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    
    
    public UserBook () {
    	
    }
    
    
    
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Book getBook() {
		return book;
	}



	public void setBook(Book book) {
		this.book = book;
	}



	public Boolean getOwned() {
		return owned;
	}



	public void setOwned(Boolean owned) {
		this.owned = owned;
	}



	public Boolean getCompleted() {
		return completed;
	}



	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}



	public Long getCurrent_page() {
		return current_page;
	}



	public void setCurrent_page(Long current_page) {
		this.current_page = current_page;
	}



	public String getReview() {
		return review;
	}



	public void setReview(String review) {
		this.review = review;
	}



	public Integer getRating() {
		return rating;
	}



	public void setRating(Integer rating) {
		this.rating = rating;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}



	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    
}
