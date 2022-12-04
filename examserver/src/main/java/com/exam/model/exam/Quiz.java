package com.exam.model.exam;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "quiz")
public class Quiz {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long qid;
	private String title;
	private String description;
	private String maxMarks;
	private String noOfQuestions;
	private boolean active = false;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Category category; 
	
	@OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	private Set<Question> questions = new LinkedHashSet<>();
	
	public Quiz() {
	}

	public Quiz(Long qid, String title, String description, String maxMarks, String noOfQuestions, boolean active) {
		super();
		this.qid = qid;
		this.title = title;
		this.description = description;
		this.maxMarks = maxMarks;
		this.noOfQuestions = noOfQuestions;
		this.active = active;
	}

	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}

	public String getNoOfQuestions() {
		return noOfQuestions;
	}

	public void setNoOfQuestions(String noOfQuestions) {
		this.noOfQuestions = noOfQuestions;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	

}
