package com.jy.booksbooksbooks.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;





@Entity
@Table(name="authors")
public class Author {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
		
	private String name;
	
	private String url;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "authors_books", 
        joinColumns = @JoinColumn(name = "author_id"), 
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
	private List<Book> books;
        	
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;

    
    public Author () {
    	
    }
    
    
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public List<Book> getBooks() {
		return books;
	}



	public void setBooks(List<Book> books) {
		this.books = books;
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
