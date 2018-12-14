package solidityunit.parser.code.internal.entity;

import java.util.ArrayList;
import java.util.List;
 
/**
 * 
 * @author Hallan
 */
public class Metodo {
 
	private String modificadorAcesso;
	private String nome;
	private String tipoRetorno;
	private List<Atributo> argumentos;
	
	private boolean isSafe;
 
	public Metodo() {
		this.argumentos = new ArrayList<Atributo>();
	}
 
	@Override
	public String toString() {
		return "\n\tMetodo{" + "modificadorAcesso=" + modificadorAcesso + ", nome=" + nome + ", tipoRetorno=" + tipoRetorno + ", argumentos=" + argumentos + '}';
	}
 
	public String getTiposArgumentos() {
		StringBuilder sb = new StringBuilder();
		for (Atributo a : this.argumentos) {
			sb.append(a.getTipo());
			sb.append(",");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
 
	public String getModificadorAcesso() {
		return modificadorAcesso;
	}
 
	public void setModificadorAcesso(String modificadorAcesso) {
		this.modificadorAcesso = modificadorAcesso;
	}
 
	public String getNome() {
		return nome;
	}
 
	public void setNome(String nome) {
		this.nome = nome;
	}
 
	public String getTipoRetorno() {
		return tipoRetorno;
	}
 
	public void setTipoRetorno(String tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}
 
	public List<Atributo> getArgumentos() {
		return argumentos;
	}
 
	public void setArgumentos(List<Atributo> argumentos) {
		this.argumentos = argumentos;
	}
 
	public void addArgumento(Atributo attr) {
		this.argumentos.add(attr);
	}

	public boolean isSafe() {
		return isSafe;
	}

	public void setSafe(boolean isSafe) {
		this.isSafe = isSafe;
	}
 
}
