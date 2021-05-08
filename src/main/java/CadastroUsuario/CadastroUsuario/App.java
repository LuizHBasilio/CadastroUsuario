package CadastroUsuario.CadastroUsuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import dao.UsuarioDAOImpl;
import entidade.Telefone;
import entidade.Usuario;
import util.jpaUtil;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		//EntityManager ent = jpaUtil.getEntityManager();
		//System.out.println(ent.isOpen());
		
		UsuarioDAOImpl newuser = new UsuarioDAOImpl();
		
		Telefone t2 = new Telefone();
		t2.setDdd("81");
		t2.setNumero("99746-9874");
		t2.setTipo("Celular");
		
		List<Telefone> list2 = new ArrayList<Telefone>();
		list2.add(t2);
		
		Usuario us4 = new Usuario();
		us4.setEmail("marcos@gmail.com.br");
		us4.setNome("Marcos");
		us4.setSenha("123");
		us4.setListaTelefones(list2);
		
		
		newuser.inserirUsuario(us4);
		newuser.adiconarTelefoneCliente(us4);
	}
}
