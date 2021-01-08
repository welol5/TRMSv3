package dev.elliman.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class RFC {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="claim_id")
	private Claim claim;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="commenter")
	private Person commenter;
	private String description;
	private String answer;
	
	public RFC() {
		id = null;
		claim = null;
		commenter = null;
		description = null;
		answer = null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Claim getClaim() {
		return claim;
	}

	public void setClaim(Claim claim) {
		this.claim = claim;
	}

	public Person getCommenter() {
		return commenter;
	}

	public void setCommenter(Person commenter) {
		this.commenter = commenter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((claim == null) ? 0 : claim.hashCode());
		result = prime * result + ((commenter == null) ? 0 : commenter.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RFC other = (RFC) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (claim == null) {
			if (other.claim != null)
				return false;
		} else if (!claim.equals(other.claim))
			return false;
		if (commenter == null) {
			if (other.commenter != null)
				return false;
		} else if (!commenter.equals(other.commenter))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RFC [id=" + id + ", claim=" + claim + ", commenter=" + commenter + ", description=" + description
				+ ", answer=" + answer + "]";
	}
	
	
}
