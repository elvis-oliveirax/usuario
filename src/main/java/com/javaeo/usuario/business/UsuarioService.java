package com.javaeo.usuario.business;

import com.javaeo.usuario.business.converter.UsuarioConveter;
import com.javaeo.usuario.business.dto.EnderecoDTO;
import com.javaeo.usuario.business.dto.TelefoneDTO;
import com.javaeo.usuario.business.dto.UsuarioDTO;
import com.javaeo.usuario.infrastructure.entity.Endereco;
import com.javaeo.usuario.infrastructure.entity.Telefone;
import com.javaeo.usuario.infrastructure.entity.Usuario;
import com.javaeo.usuario.infrastructure.exeptions.ConflictException;
import com.javaeo.usuario.infrastructure.exeptions.RescoucerNotFoundException;
import com.javaeo.usuario.infrastructure.exeptions.UnauthorizedException;
import com.javaeo.usuario.infrastructure.repository.EnderecoRepository;
import com.javaeo.usuario.infrastructure.repository.TelefoneRepository;
import com.javaeo.usuario.infrastructure.repository.UsuarioRepository;
import com.javaeo.usuario.infrastructure.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioConveter usuarioConveter;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final EnderecoRepository enderecoRepository;
	private final TelefoneRepository telefoneRepository;
	private final AuthenticationManager authenticationManager;

	public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
		emailExiste(usuarioDTO.getEmail());
		usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
		Usuario usuario = usuarioConveter.paraUsuario(usuarioDTO);
		return usuarioConveter.paraUsuarioDTO(
				usuarioRepository.save(usuario)
		);
	}
	public String autenticarUsuario(UsuarioDTO usuarioDTO) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
							usuarioDTO.getSenha())
			);
			return "Bearer " + jwtUtil.generateToken(authentication.getName());
		} catch (BadCredentialsException | UsernameNotFoundException | AuthorizationDeniedException e) {
			throw new UnauthorizedException("Usuário ou senha inválidos: ", e.getCause());
		}
	}

	public void emailExiste(String email){
		try{
			boolean existe = verificaEmailExistente(email);
			if (existe){
				throw new ConflictException("Email já cadastrado" + email);
			}
		}catch (ConflictException e){
			throw new ConflictException("Email já cadastrado" + e.getCause());
		}
	}

	public boolean verificaEmailExistente(String email){
		return usuarioRepository.existsByEmail(email);
	}

	public UsuarioDTO buscarUsuarioPorEmail(String email){
		try {
			return usuarioConveter.paraUsuarioDTO(
					usuarioRepository.findByEmail(email)
							.orElseThrow(
									() -> new RescoucerNotFoundException("Email não encontrado " + email)
							)
			);
		}catch (RescoucerNotFoundException e){
			throw new RescoucerNotFoundException("Email não encontrado " + email);
		}

	}

	public void deletaUsuarioPorEmail(String email){
		usuarioRepository.deleteByEmail(email);
	}


	public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
		//Aqui buscamos o email do usuário através do token (tirar a obrigatoriedade do email)
		String email = jwtUtil.extrairEmailToken(token.substring(7));

		//Criptografia de senha
		dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

		//Busca os dados do usuário no banco de dados
		Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
				new RescoucerNotFoundException("Email não localizado"));

		//Mesclou os dados que recebemos na requisição DTo com os dados do banco de dados
		Usuario usuario = usuarioConveter.updateUsuario(dto, usuarioEntity);

		//salvou os dados do usuário e depois pegou o retorno e converteu para  UsuarioDTO
		return usuarioConveter.paraUsuarioDTO(usuarioRepository.save(usuario));
	}

	public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){

		Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
				new RescoucerNotFoundException("Id não encontrado " + idEndereco));

		Endereco endereco = usuarioConveter.updateEndereco(enderecoDTO,entity);

		return usuarioConveter.paraEnderecoDTO(enderecoRepository.save(endereco));

	}

	public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto){

		Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(()->
				new RescoucerNotFoundException("Id não encontrado " + idTelefone));

		Telefone telefone = usuarioConveter.updateTelefone(dto, entity);

		return usuarioConveter.paraTelefoneDTO(telefoneRepository.save(telefone));
	}

	public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto){
		String email = jwtUtil.extrairEmailToken(token.substring(7));
		Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
				new RescoucerNotFoundException("Email não localizado"));

		Endereco endereco = usuarioConveter.paraEnderecoEntity(dto, usuario.getId());
		Endereco enderecoEntity = enderecoRepository.save(endereco);
		return usuarioConveter.paraEnderecoDTO(enderecoEntity);

	}

	public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto) {
		String email = jwtUtil.extrairEmailToken(token.substring(7));
		Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
				new RescoucerNotFoundException("Email não localizado"));

		Telefone telefone = usuarioConveter.paraTelefoneEntity(dto, usuario.getId());
		return usuarioConveter.paraTelefoneDTO(
				telefoneRepository.save(telefone)
		);
	}
}
