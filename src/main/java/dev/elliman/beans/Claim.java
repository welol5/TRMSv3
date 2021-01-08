package dev.elliman.beans;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class Claim {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String title;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="person")
	private Person person;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="event_type")
	private Event event;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="grading")
	private Grading grading;
	@Column(name="event_timestamp")
	private LocalDateTime eventDate;
	@Column(name="event_location")
	private String eventLocation;
	private String description;
	private Double price;
	private String justification;
	@Column(name="time_missed")
	private Integer hoursMissed;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="approval_stage")
	private Stage approvalStage;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="dsa")
	private Person dsa;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="dha")
	private Person dha;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="bca")
	private Person bca;
	@Column(name="denial_reason")
	private String denialReason;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="passing_approval")
	private Person passingApproval;
	@Column(name="estimated_amount")
	private Double estimatedAmount;
	@Column(name="last_approved")
	private LocalDateTime lastApproved;
	@Transient
	private Boolean isUrgent;
	
	public Claim() {
		id = null;
		person = null;
		event = null;
		grading = null;
		eventDate = null;
		eventLocation = null;
		description = null;
		price = null;
		justification = null;
		hoursMissed = null;
		approvalStage = null;
		dsa = null;
		dha = null;
		bca = null;
		denialReason = null;
		passingApproval = null;
		estimatedAmount = null;
		lastApproved = null;
		isUrgent = null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Person getPerson() {
		return person;
	}

	public void setPersonID(Person person) {
		this.person = person;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Grading getGrading() {
		return grading;
	}

	public void setGrading(Grading grading) {
		this.grading = grading;
	}

	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public Integer getHoursMissed() {
		return hoursMissed;
	}

	public void setHoursMissed(Integer hoursMissed) {
		this.hoursMissed = hoursMissed;
	}

	public Stage getApprovalStage() {
		return approvalStage;
	}

	public void setApprovalStage(Stage approvalStage) {
		this.approvalStage = approvalStage;
	}

	public Person getDsa() {
		return dsa;
	}

	public void setDsa(Person dsa) {
		this.dsa = dsa;
	}

	public Person getDha() {
		return dha;
	}

	public void setDha(Person dha) {
		this.dha = dha;
	}

	public Person getBca() {
		return bca;
	}

	public void setBca(Person bca) {
		this.bca = bca;
	}

	public String getDenialReason() {
		return denialReason;
	}

	public void setDenialReason(String denialReason) {
		this.denialReason = denialReason;
	}

	public Person getPassingApproval() {
		return passingApproval;
	}

	public void setPassingApproval(Person passingApproval) {
		this.passingApproval = passingApproval;
	}

	public double getEstimatedAmount() {
		return estimatedAmount;
	}

	public void setEstimatedAmount(double estimatedAmount) {
		this.estimatedAmount = estimatedAmount;
	}

	public LocalDateTime getLastApproved() {
		return lastApproved;
	}

	public void setLastApproved(LocalDateTime lastApproved) {
		this.lastApproved = lastApproved;
	}

	public Boolean getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(Boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvalStage == null) ? 0 : approvalStage.hashCode());
		result = prime * result + ((bca == null) ? 0 : bca.hashCode());
		result = prime * result + ((denialReason == null) ? 0 : denialReason.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dha == null) ? 0 : dha.hashCode());
		result = prime * result + ((dsa == null) ? 0 : dsa.hashCode());
		result = prime * result + ((estimatedAmount == null) ? 0 : estimatedAmount.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result + ((eventLocation == null) ? 0 : eventLocation.hashCode());
		result = prime * result + ((grading == null) ? 0 : grading.hashCode());
		result = prime * result + ((hoursMissed == null) ? 0 : hoursMissed.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((justification == null) ? 0 : justification.hashCode());
		result = prime * result + ((lastApproved == null) ? 0 : lastApproved.hashCode());
		result = prime * result + ((passingApproval == null) ? 0 : passingApproval.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Claim other = (Claim) obj;
		if (approvalStage == null) {
			if (other.approvalStage != null)
				return false;
		} else if (!approvalStage.equals(other.approvalStage))
			return false;
		if (bca == null) {
			if (other.bca != null)
				return false;
		} else if (!bca.equals(other.bca))
			return false;
		if (denialReason == null) {
			if (other.denialReason != null)
				return false;
		} else if (!denialReason.equals(other.denialReason))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dha == null) {
			if (other.dha != null)
				return false;
		} else if (!dha.equals(other.dha))
			return false;
		if (dsa == null) {
			if (other.dsa != null)
				return false;
		} else if (!dsa.equals(other.dsa))
			return false;
		if (estimatedAmount == null) {
			if (other.estimatedAmount != null)
				return false;
		} else if (!estimatedAmount.equals(other.estimatedAmount))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (eventLocation == null) {
			if (other.eventLocation != null)
				return false;
		} else if (!eventLocation.equals(other.eventLocation))
			return false;
		if (grading == null) {
			if (other.grading != null)
				return false;
		} else if (!grading.equals(other.grading))
			return false;
		if (hoursMissed == null) {
			if (other.hoursMissed != null)
				return false;
		} else if (!hoursMissed.equals(other.hoursMissed))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (justification == null) {
			if (other.justification != null)
				return false;
		} else if (!justification.equals(other.justification))
			return false;
		if (lastApproved == null) {
			if (other.lastApproved != null)
				return false;
		} else if (!lastApproved.equals(other.lastApproved))
			return false;
		if (passingApproval == null) {
			if (other.passingApproval != null)
				return false;
		} else if (!passingApproval.equals(other.passingApproval))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Claim [id=" + id + ", title=" + title + ", person=" + person + ", event=" + event + ", grading="
				+ grading + ", eventDate=" + eventDate + ", eventLocation=" + eventLocation + ", description="
				+ description + ", price=" + price + ", justification=" + justification + ", hoursMissed=" + hoursMissed
				+ ", approvalStage=" + approvalStage + ", dsa=" + dsa + ", dha=" + dha + ", bca=" + bca
				+ ", denialReason=" + denialReason + ", passingApproval=" + passingApproval + ", estimatedAmount="
				+ estimatedAmount + ", lastApproved=" + lastApproved + "]";
	}
}
