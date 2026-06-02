package com.javaeo.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "telefone")
@Builder
public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "numero", length = 10)
	private String numero;

	@Column(name = "ddd", length = 3)
	private String ddd;

	@Column(name = "usuario_id")
	private long usuarioid;
}
