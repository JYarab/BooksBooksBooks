package com.jy.booksbooksbooks.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="books")
public class Book {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

	private String title;
	@Column
	@ElementCollection
	private List<String> isbn13;
	@Column
	@ElementCollection
	private List<String> isbn10;
	@Column
	@ElementCollection
	private List<String> openLibraryID;
	
    @SuppressWarnings("unchecked")
   	@JsonProperty("identifiers")
    private void unpackNestedIdentifiers(Map<String,Object> identifiers) {
        this.isbn13 = (List<String>)identifiers.get("isbn_13");
        this.isbn10 = (List<String>)identifiers.get("isbn_10");
        this.openLibraryID = (List<String>)identifiers.get("openlibrary");
    }
	
	@Transient
	@JsonProperty("authors")
	private ArrayList<HashMap<String,String>> searchAuthors;
	
	@JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "authors_books", 
        joinColumns = @JoinColumn(name = "book_id"), 
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;
		
	private String cover_sm;
	private String cover_md;
	private String cover_lg;
	
	@JsonProperty("cover")
    private void unpackNestedCovers(HashMap<String,String> cover) {
        this.cover_sm = (cover.get("small"));
        this.cover_md = (cover.get("medium"));
        this.cover_lg = (cover.get("large"));
    }
	
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
       
    @OneToMany(mappedBy="book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserBook> usersBooks;
        
    public Book(){
    	
    }

    
	public ArrayList<HashMap<String, String>> getSearchAuthors() {
		return searchAuthors;
	}



	public void setSearchAuthors(ArrayList<HashMap<String, String>> searchAuthors) {
		this.searchAuthors = searchAuthors;
	}



	public List<UserBook> getUsersBooks() {
		return usersBooks;
	}



	public void setUsersBooks(List<UserBook> usersBooks) {
		this.usersBooks = usersBooks;
	}

	
	
	public String getCover_sm() {
		return cover_sm;
	}



	public void setCover_sm(String cover_sm) {
		this.cover_sm = cover_sm;
	}



	public String getCover_md() {
		return cover_md;
	}



	public void setCover_md(String cover_md) {
		this.cover_md = cover_md;
	}



	public String getCover_lg() {
		return cover_lg;
	}



	public void setCover_lg(String cover_lg) {
		this.cover_lg = cover_lg;
	}


	public List<String> getIsbn13() {
		return isbn13;
	}



	public void setIsbn13(List<String> isbn13) {
		this.isbn13 = isbn13;
	}



	public List<String> getIsbn10() {
		return isbn10;
	}



	public void setIsbn10(List<String> isbn10) {
		this.isbn10 = isbn10;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}


	public List<Author> getAuthors() {
		return authors;
	}



	public void setAuthors(List<Author> authors) {
		this.authors = authors;
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
