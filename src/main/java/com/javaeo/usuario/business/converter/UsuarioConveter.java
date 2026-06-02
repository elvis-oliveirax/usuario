package com.javaeo.usuario.business.converter;

import com.javaeo.usuario.business.dto.EnderecoDTO;
import com.javaeo.usuario.business.dto.TelefoneDTO;
import com.javaeo.usuario.business.dto.UsuarioDTO;
import com.javaeo.usuario.infrastructure.entity.Endereco;
import com.javaeo.usuario.infrastructure.entity.Telefone;
import com.javaeo.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConveter {

	public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
		return Usuario.builder()
				.nome(usuarioDTO.getNome())
				.email(usuarioDTO.getEmail())
				.senha(usuarioDTO.getSenha())
				.enderecos(usuarioDTO.getEnderecos() != null ?
						paraListaEndereco(usuarioDTO.getEnderecos()) : null)
				.telefones(usuarioDTO.getTelefones() != null ?
						paraListaTelefones(usuarioDTO.getTelefones())  : null)
				.build();



	}

	public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
     }

	 public Endereco paraEndereco(EnderecoDTO enderecoDTO){
		return Endereco.builder()
				.rua(enderecoDTO.getRua())
				.numero(enderecoDTO.getNumero())
				.cidade(enderecoDTO.getCidade())
				.complemento(enderecoDTO.getComplemento())
				.cep(enderecoDTO.getCep())
				.estado(enderecoDTO.getEstado())
				.build();
	 }

	public List<Telefone> paraListaTelefones (List<TelefoneDTO> telefonesDTO){
		return telefonesDTO.stream().map(this::paraTelefone).toList();
	    }

	 public Telefone paraTelefone (TelefoneDTO telefonesDTO){
		return Telefone.builder()
				.numero(telefonesDTO.getNumero())
				.ddd(telefonesDTO.getDdd())
				.build();
	 }

	public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO) {
		return UsuarioDTO.builder()
				.nome(usuarioDTO.getNome())
				.email(usuarioDTO.getEmail())
				.senha(usuarioDTO.getSenha())
				.enderecos(usuarioDTO.getEnderecos() != null ?
						paraListaEnderecoDTO(usuarioDTO.getEnderecos()) : null)
				.telefones(usuarioDTO.getTelefones() != null ?
						paraListaTelefonesDTO(usuarioDTO.getTelefones())  : null)
				.build();



	}

	public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTO){
		return enderecoDTO.stream().map(this::paraEnderecoDTO).toList();
	}

	public EnderecoDTO paraEnderecoDTO(Endereco endereco){
		return EnderecoDTO.builder()
				.id(endereco.getId())
				.rua(endereco.getRua())
				.numero(endereco.getNumero())
				.cidade(endereco.getCidade())
				.complemento(endereco.getComplemento())
				.cep(endereco.getCep())
				.estado(endereco.getEstado())
				.build();
	}

	public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefoneDTOS){
		return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
	}

	public TelefoneDTO paraTelefoneDTO (Telefone telefones){
		return TelefoneDTO.builder()
				.id(telefones.getId())
				.numero(telefones.getNumero())
				.ddd(telefones.getDdd())
				.build();
	}

	public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
		return Usuario.builder()
				.nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
				.id(entity.getId())
				.senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
				.email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
				.enderecos(entity.getEnderecos())
				.telefones(entity.getTelefones())
				.build();
	}

	public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
		return  Endereco.builder()
				.id(entity.getId())
				.rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
				.numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
				.cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
				.cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
				.complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
				.estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
				.build();
	}

	public Telefone updateTelefone(TelefoneDTO dto, Telefone entity){
		return Telefone.builder()
				.id(entity.getId())
				.ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
				.numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
				.build();
	}

	public  Endereco paraEnderecoEntity(EnderecoDTO dto, Long idUsuario){
		return Endereco.builder()
				.rua(dto.getRua())
				.numero(dto.getNumero())
				.cidade(dto.getCidade())
				.cep(dto.getCep())
				.complemento(dto.getComplemento())
				.estado(dto.getEstado())
				.usuarioId(idUsuario)
				.build();
	}

	public Telefone paraTelefoneEntity(TelefoneDTO dto, Long idUsuario){
		return Telefone.builder()
				.numero(dto.getNumero())
				.ddd(dto.getDdd())
				.usuarioId(idUsuario)
				.build();
	}
}
