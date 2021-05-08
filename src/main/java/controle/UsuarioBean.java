package controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import entidade.Telefone;
import entidade.Usuario;

@ManagedBean(name = "UsuarioBean")
@SessionScoped
public class UsuarioBean {

	private Usuario usuario;
	private Telefone telefone;
	private List<Usuario> listaUsuarios;

	private UsuarioDAO usuarioDAO;

	private String numeroSel, dddSel, tipoSel;

	private boolean origemPesquisa = false;

	private boolean modoEdicao = false;

	private String emailSelecionado;

	public UsuarioBean() {
		this.usuarioDAO = new UsuarioDAOImpl();
		this.inicializarCampos();
	}

	public void inicializarCampos() {
		if (!origemPesquisa) {
			this.usuario = new Usuario();
			this.usuario.setListaTelefones(new ArrayList<Telefone>());
			modoEdicao = false;
		}

		origemPesquisa = false;

		this.telefone = new Telefone();

		this.listaUsuarios = new ArrayList<Usuario>();
	}

	public void salvar() {

		if (!modoEdicao) {
			if (usuarioDAO.existeUsuario(usuario) == null) {
				this.usuarioDAO.inserirUsuario(usuario);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, usuario.getNome(), "cadastrado com sucesso"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Cliente já cadastrado"));
			}
		} else {
			this.usuarioDAO.inserirUsuario(usuario);
			this.usuario = this.usuarioDAO.existeUsuario(usuario);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO", "Cliente alterado"));
		}
	}

	public void adicionarTelefone() {

		telefone.setUsuario(usuario);
		this.usuario.getListaTelefones().add(telefone);

		this.telefone = new Telefone();
	}

	public void removerTelefone() {
		Telefone achou = null;
		for (Telefone cont : usuario.getListaTelefones()) {
			if (numeroSel != null && !numeroSel.isEmpty() && dddSel != null && !dddSel.isEmpty() && tipoSel != null
					&& !tipoSel.isEmpty()) {

				if (cont.getNumero().equals(numeroSel) && cont.getNumero().equals(numeroSel)) {
					achou = cont;
				}

			} else if (numeroSel != null && !numeroSel.isEmpty()) {
				if (cont.getNumero().equals(numeroSel)) {
					achou = cont;
				}
			} else {
				if (cont.getDdd().equals(dddSel)) {
					achou = cont;
				} else {
					if (cont.getTipo().equals(tipoSel)) {
						achou = cont;
					}

				}
			}

			if (achou != null) {
				if (achou.getId() > 0) {
					this.usuarioDAO.removerTelefoneCliente(achou);
				}

				usuario.getListaTelefones().remove(achou);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Contato removido"));
			}
		}

	}

	public void pesquisarUsuario() {
		this.listaUsuarios = usuarioDAO.pesquisarUsuario(usuario, telefone);
	}

	public String editarUsuario() {
		this.origemPesquisa = true;
		this.modoEdicao = true;

		Usuario usuarioPesquisa = new Usuario();
		usuarioPesquisa.setEmail(emailSelecionado);

		this.usuario = this.usuarioDAO.existeUsuario(usuarioPesquisa);

		return "/cadastroUsuario.xhtml?faces-redirect=true&amp;includeViewParams=true";
	}

	public void removerUsuario() {

		Usuario usuarioPesquisa = new Usuario();
		usuarioPesquisa.setEmail(emailSelecionado);

		this.usuarioDAO.removerUsuario(usuarioPesquisa);

		this.pesquisarUsuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public String getNumeroSel() {
		return numeroSel;
	}

	public void setNumeroSel(String numeroSel) {
		this.numeroSel = numeroSel;
	}

	public String getDddSel() {
		return dddSel;
	}

	public void setDddSel(String dddSel) {
		this.dddSel = dddSel;
	}

	public String getTipoSel() {
		return tipoSel;
	}

	public void setTipoSEl(String tipoSel) {
		this.tipoSel = tipoSel;
	}

	public boolean isOrigemPesquisa() {
		return origemPesquisa;
	}

	public void setOrigemPesquisa(boolean origemPesquisa) {
		this.origemPesquisa = origemPesquisa;
	}

	public boolean isModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

	public String getEmailSelecionado() {
		return emailSelecionado;
	}

	public void setEmailSelecionado(String emailSelecionado) {
		this.emailSelecionado = emailSelecionado;
	}

}
