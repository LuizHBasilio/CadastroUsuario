package dao;

import java.util.List;

import entidade.Telefone;
import entidade.Usuario;

public interface UsuarioDAO {

	public boolean inserirUsuario(Usuario usuario);

	public List<Usuario> pesquisarUsuario(Usuario usuario, Telefone telefone);

	public boolean removerUsuario(Usuario usuario);

	public Usuario existeUsuario(Usuario usuario);

	public boolean adiconarTelefoneCliente(Usuario usuario);

	public boolean removerTelefoneCliente(Usuario usuario);

	public boolean removerTelefoneCliente(Telefone telefone);
}
