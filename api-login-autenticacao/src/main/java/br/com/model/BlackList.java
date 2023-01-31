package br.com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity(name = "tb_blacklist")
@SequenceGenerator(name = "blacklist_seq", sequenceName = "blacklist_seq", initialValue = 1, allocationSize = 1)
public class BlackList {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "blacklist_seq")
    private Long id;

    private String token;
}
