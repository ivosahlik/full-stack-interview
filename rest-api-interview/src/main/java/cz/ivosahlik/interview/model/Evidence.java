package cz.ivosahlik.interview.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "evidence")
public class Evidence extends IdBasedEntity {

	private String name;
	private String description;

	@OneToMany(
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	@OrderBy("version asc")
	private Set<Version> version = new HashSet<>();

	private String hypeLevel;
	private LocalDate deprecationDate;

}