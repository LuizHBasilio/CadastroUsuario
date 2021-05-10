package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entidade.Telefone;
import entidade.Usuario;
import util.JpaUtil;

public class UsuarioDAOImpl implements UsuarioDAO {

	@Override
	public boolean inserirUsuario(Usuario usuario) {

		EntityManager ent = JpaUtil.getEntityManager();
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
			if (usuario.getNome() != null && !usuario.getNome().isEmpty()) {
				where += " and upper(us.nome) like '%" + usuario.getNome().toUpperCase() + "%'";
			}

			if (telefone.getNumero() != null && !telefone.getNumero().isEmpty()) {
				where += " and upper(tel.numero) like '%" + telefone.getNumero().toUpperCase() + "%'";
			}
		}
		return where;

	}

	@Override
	public List<Usuario> pesquisarUsuario(Usuario usuario, Telefone telefone) {

		String sql = "Select distinct us from Usuario us, Telefone tel " + " where us.email = tel.usuario.email " + montarWhere(usuario, telefone);

		EntityManager ent = JpaUtil.getEntityManager();

		Query query = ent.createQuery(sql);

		List<Usuario> listaUsuarios = query.getResultList();

		ent.close();

		return listaUsuarios;
	}

	@Override
	public boolean adiconarTelefoneCliente(Usuario usuario) {

		EntityManager ent = JpaUtil.getEntityManager();
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

		EntityManager ent = JpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();

		tx.begin();

		for (Telefone telefone : usuario.getListaTelefones()) {
			Telefone existe = ent.find(Telefone.class, telefone.getId());
			if (existe != null) {
				ent.remove(existe);
			}
		}

		tx.commit();
		ent.close();
		return true;
	}

	@Override
	public Usuario existeUsuario(Usuario usuario) {
		EntityManager ent = JpaUtil.getEntityManager();

		Usuario existe = ent.find(Usuario.class, usuario.getEmail());

		ent.close();
		return existe;
	}

	@Override
	public boolean removerTelefoneCliente(Telefone telefone) {
		EntityManager ent = JpaUtil.getEntityManager();
		Telefone existe = ent.find(Telefone.class, telefone.getId());

		EntityTransaction tx = ent.getTransaction();

		tx.begin();

		ent.remove(existe);

		tx.commit();
		ent.close();
		return true;
	}

	@Override
	public boolean removerUsuario(Usuario usuario) {

		EntityManager ent = JpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();

		Usuario existe = ent.find(Usuario.class, usuario.getEmail());

		tx.begin();

		ent.remove(existe);

		tx.commit();
		ent.close();
		return true;
	}

}
