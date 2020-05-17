package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="t_block_info")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="f_key",nullable=false)
	private String key;
	@Column(name="f_value",nullable=false)
	private String value;

}