package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TELEFONE")
public class Telefone {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(generator = "S_TELEFONE")
	@SequenceGenerator(name = "S_TELEFONE", sequenceName = "S_TELEFONE", allocationSize = 1)
	private int id;
	@Column(name = "DDD", nullable = false)
	private String ddd;
	@Column(name = "NUMERO", nullable = false)
	private String numero;
	@Column(name = "TIPO", nullable = false)
	private String tipo;

	@ManyToOne
	@JoinColumn(name = "EMAIL_USUARIO", referencedColumnName = "EMAIL", nullable = false)
	private Usuario usuario;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public boolean equals(Object obj) {

		Telefone telefoneEntrada = (Telefone) obj;

		if (this.numero.equalsIgnoreCase(telefoneEntrada.getNumero())) {
			return true;
		} else {
			return false;
		}
	}

}
