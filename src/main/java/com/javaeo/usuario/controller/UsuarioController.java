package com.javaeo.usuario.controller;

import com.javaeo.usuario.business.UsuarioService;
import com.javaeo.usuario.business.dto.EnderecoDTO;
import com.javaeo.usuario.business.dto.TelefoneDTO;
import com.javaeo.usuario.business.dto.UsuarioDTO;
import com.javaeo.usuario.infrastructure.security.JwtUtil;
import com.javaeo.usuario.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Cadastro e login de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UsuarioController {

	private  final UsuarioService usuarioService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@PostMapping
	@Operation(summary = "Salvar Usuário", description = "Cria um novo usuário")
	@ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso")
	@ApiResponse(responseCode = "409", description = "Usuário já cadastrado")
	@ApiResponse(responseCode = "500", description = "Erro de servidor")
	public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
	       return ResponseEntity.ok (usuarioService.salvaUsuario(usuarioDTO));
	}

	@PostMapping("/login")
	@Operation(summary = "Login Usuário", description = "Login do usuário")
	@ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
	@ApiResponse(responseCode = "401", description = "Credenciais inválidas")
	@ApiResponse(responseCode = "500", description = "Erro de servidor")
	public String login(@RequestBody UsuarioDTO usuarioDTO){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
						usuarioDTO.getSenha())
		);
		return "Bearer " + jwtUtil.generateToken(authentication.getName());
	}

	@GetMapping
	@Operation(summary = "Buscar dados de Usuário por e-mail",
			description = "Busca dados do usuário")
	@ApiResponse(responseCode = "200", description = "Usuário encontrado")
	@ApiResponse(responseCode = "404", description = "Usuário não cadastrado")
	@ApiResponse(responseCode = "401", description = "Credenciais inválidas")
	@ApiResponse(responseCode = "500", description = "Erro de servidor")
	public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email){
		return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
	}

	@DeleteMapping("/{email}")
	@Operation(summary = "Deleta usuário por e-mail", description = "Deleta usuário")
	@ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso")
	@ApiResponse(responseCode = "404", description = "Usuário não cadastrado")
	@ApiResponse(responseCode = "401", description = "Credenciais inválidas")
	@ApiResponse(responseCode = "500", description = "Erro de servidor")
	public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email){
		usuarioService.deletaUsuarioPorEmail(email);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	@Operation(summary = "Atualiza Dados de Usuário",
			description = "Atualiza dados de usuário")
	@ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
	@ApiResponse(responseCode = "404", description = "Usuário não cadastrado")
	@ApiResponse(responseCode = "401", description = "Credenciais inválidas")
	@ApiResponse(responseCode = "500", description = "Erro de servidor")
	public ResponseEntity<UsuarioDTO> atualizaDadoUsuario(@RequestBody UsuarioDTO dto,
	                                                      @RequestHeader("Authorization") String token){
		return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
	}

	@PutMapping("/endereco")
	@Operation(summary = "Atualiza Endereço de Usuário",
			description = "Atualiza Endereço de usuário")
	@ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
	@ApiResponse(responseCode = "404", description = "Usuário não cadastrado")
	@ApiResponse(responseCode = "401", description = "Credenciais inválidas")
	@ApiResponse(responseCode = "500", description = "Erro de servidor")
	public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO dto,
														@RequestParam("id") Long id){
		return  ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
	}

	@PutMapping("/telefone")
	@Operation(summary = "Atualiza Telefone de Usuário",
			description = "Atualiza Telefone de usuário")
	@ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso")
	@ApiResponse(responseCode = "404", description = "Usuário não cadastrado")
	@ApiResponse(responseCode = "401", description = "Credenciais inválidas")
	@ApiResponse(responseCode = "500", description = "Erro de servidor")
	public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO dto,
														@RequestParam("id") Long id) {
		return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
	}

	@PostMapping("/endereco")
	@Operation(summary = "Salva Endereço de Usuário",
			description = "Salva Endereço de usuário")
	@ApiResponse(responseCode = "200", description = "Endereço salvo com sucesso")
	@ApiResponse(responseCode = "404", description = "Endereço não cadastrado")
	@ApiResponse(responseCode = "401", description = "Credenciais inválidas")
	@ApiResponse(responseCode = "500", description = "Erro de servidor")
	public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO dto,
														@RequestHeader("Authorization") String token){
		return  ResponseEntity.ok(usuarioService.cadastraEndereco(token, dto));
	}

	@PostMapping("/telefone")
	@Operation(summary = "Salva Telefone de Usuário",
			description = "Salva Telefone de usuário")
	@ApiResponse(responseCode = "200", description = "Telefone salvo com sucesso")
	@ApiResponse(responseCode = "404", description = "Usuário não cadastrado")
	@ApiResponse(responseCode = "401", description = "Credenciais inválidas")
	@ApiResponse(responseCode = "500", description = "Erro de servidor")
	public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestBody TelefoneDTO dto,
														@RequestHeader("Authorization") String token) {
		return ResponseEntity.ok(usuarioService.cadastraTelefone(token, dto));
	}
}
