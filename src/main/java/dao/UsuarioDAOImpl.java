package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entidade.Telefone;
import entidade.Usuario;
import util.jpaUtil;

public class UsuarioDAOImpl implements UsuarioDAO {

	@Override
	public boolean inserirUsuario(Usuario usuario) {

		EntityManager ent = jpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();

		tx.begin();

		ent.merge(usuario);

		tx.commit();
		ent.close();

		return true;
	}

	private String montarWhere(Usuario usuario, Telefone telefone) {

		String where = "";

		if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
			where += " and us.email = '" + usuario.getEmail() + "'";
		} else {
			if (usuario.getNome() != null && usuario.getNome().isEmpty()) {
				where += " and upper(us.nome) like '%" + usuario.getNome().toUpperCase() + "%'";
			}

			if (usuario.getSenha() != null && usuario.getSenha().isEmpty()) {
				where += " and upper(us.senha) like '%" + usuario.getNome().toUpperCase() + "%'";
			}

			if (telefone.getNumero() != null && !telefone.getNumero().isEmpty()) {
				where += " and tel.numero like '%" + telefone.getNumero() + "%'";
			} else {

				if (telefone.getDdd() != null && !telefone.getDdd().isEmpty()) {
					where += " and tel.ddd like '%" + telefone.getDdd() + "%'";
				}

				if (telefone.getTipo() != null && !telefone.getTipo().isEmpty()) {
					where += " and tel.tipo like '%" + telefone.getTipo() + "%'";
				}
			}
		}

		return where;
	}

	@Override
	public List<Usuario> pesquisarUsuario(Usuario usuario, Telefone telefone) {

		String sql = "SELECT DISTINCT us FROM Usuario us";

		EntityManager ent = jpaUtil.getEntityManager();

		Query query = ent.createQuery(sql);

		List<Usuario> listaUsuarios = query.getResultList();

		ent.close();

		return listaUsuarios;
	}

	@Override
	public boolean removerUsuario(Usuario usuario) {

		EntityManager ent = jpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();

		Usuario existe = ent.find(Usuario.class, usuario.getEmail());

		tx.begin();

		ent.remove(existe);

		tx.commit();
		ent.close();
		return true;
	}

	@Override
	public Usuario existeUsuario(Usuario usuario) {
		EntityManager ent = jpaUtil.getEntityManager();

		Usuario existe = ent.find(Usuario.class, usuario.getEmail());

		ent.close();
		return existe;
	}

	@Override
	public boolean adiconarTelefoneCliente(Usuario usuario) {

		EntityManager ent = jpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();

		tx.begin();

		for (Telefone telefone : usuario.getListaTelefones()) {
			ent.merge(telefone);
		}

		tx.commit();
		ent.close();

		return true;
	}

	@Override
	public boolean removerTelefoneCliente(Usuario usuario) {

		EntityManager ent = jpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();

		tx.begin();

		for (Telefone telefone : usuario.getListaTelefones()) {
			Telefone existe = ent.find(Telefone.class, telefone.getNumero());
			if (existe != null) {
				ent.remove(existe);
			}
		}

		tx.commit();
		ent.close();
		return true;
	}

	@Override
	public boolean removerTelefoneCliente(Telefone telefone) {
		EntityManager ent = jpaUtil.getEntityManager();
		Telefone existe = ent.find(Telefone.class, telefone.getNumero());

		EntityTransaction tx = ent.getTransaction();

		tx.begin();

		ent.remove(existe);

		tx.commit();
		ent.close();
		return true;
	}

}
