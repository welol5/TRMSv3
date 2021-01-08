package dev.elliman.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Grading {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="presentation")
	private Boolean hasPresentation;
	@Column(name="passing_percentage")
	private Double passingPercentage;
	@Column(name="passing_letter")
	private String passingLetter;
	
	public Grading() {
		id = null;
		hasPresentation = null;
		passingLetter = null;
		passingPercentage = null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getHasPresentation() {
		return hasPresentation;
	}

	public void setHasPresentation(Boolean hasPresentation) {
		this.hasPresentation = hasPresentation;
	}

	public Double getPassingPercentage() {
		return passingPercentage;
	}

	public void setPassingPercentage(Double passingPercentage) {
		this.passingPercentage = passingPercentage;
	}

	public String getPassingLetter() {
		return passingLetter;
	}

	public void setPassingLetter(String passingLetter) {
		this.passingLetter = passingLetter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hasPresentation == null) ? 0 : hasPresentation.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((passingLetter == null) ? 0 : passingLetter.hashCode());
		result = prime * result + ((passingPercentage == null) ? 0 : passingPercentage.hashCode());
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
		Grading other = (Grading) obj;
		if (hasPresentation == null) {
			if (other.hasPresentation != null)
				return false;
		} else if (!hasPresentation.equals(other.hasPresentation))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (passingLetter == null) {
			if (other.passingLetter != null)
				return false;
		} else if (!passingLetter.equals(other.passingLetter))
			return false;
		if (passingPercentage == null) {
			if (other.passingPercentage != null)
				return false;
		} else if (!passingPercentage.equals(other.passingPercentage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Grading [id=" + id + ", hasPresentation=" + hasPresentation + ", passingPercentage=" + passingPercentage
				+ ", passingLetter=" + passingLetter + "]";
	}
	
	
}
