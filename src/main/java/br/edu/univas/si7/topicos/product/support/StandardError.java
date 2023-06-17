package br.edu.univas.si7.topicos.product.support;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

//imutável - imutable
@Getter
@AllArgsConstructor
public class StandardError {
	private String message;
	private Integer status;
	private Date date;
}
