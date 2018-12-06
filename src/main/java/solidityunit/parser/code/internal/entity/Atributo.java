package solidityunit.parser.code.internal.entity;

/**
 * 
 * @author Hallan
 */
public class Atributo {
 
	private Boolean id;
	private String nome;
	private String tipo;
	private String tipoLista;
	public static final String LIST = "List";
 
	@Override
	public String toString() {
		return "\n\tAtributo{" + "id=" + id + ", nome=" + nome + ", tipo=" + tipo + ", tipoLista=" + tipoLista + '}';
	}
 
	public String getNome() {
		return nome;
	}
 
	public void setNome(String nome) {
		this.nome = nome;
	}
 
	public String getTipo() {
		return tipo;
	}
 
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
 
	public Boolean getId() {
		return id;
	}
 
	public void setId(Boolean id) {
		this.id = id;
	}
 
	public String getTipoLista() {
		return tipoLista;
	}
 
	public void setTipoLista(String tipoLista) {
		this.tipoLista = tipoLista;
	}
 
}
