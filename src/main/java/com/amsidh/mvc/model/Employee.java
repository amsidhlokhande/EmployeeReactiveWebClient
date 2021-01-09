package com.amsidh.mvc.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
	private static final long serialVersionUID = 2401291741647022968L;

	private Integer id;
	private String name;
	private Double salary;

}
