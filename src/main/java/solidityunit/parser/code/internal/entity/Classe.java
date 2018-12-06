package solidityunit.parser.code.internal.entity;

import java.util.List;

public class Classe {
	 
	private String classType;
	private String nome;
	private String pacote;
	private List<Atributo> atributos;
	private List<Metodo> metodos;
 
 
	public String getNome() {
		return nome;
	}
 
	public void setNome(String nome) {
		this.nome = nome;
	}
 
	public String getPacote() {
		return pacote;
	}
 
	public void setPacote(String pacote) {
		this.pacote = pacote;
	}
 
	public List<Atributo> getAtributos() {
		return atributos;
	}
 
	public void setAtributos(List<Atributo> atributos) {
		this.atributos = atributos;
	}
 
	public List<Metodo> getMetodos() {
		return metodos;
	}
 
	public void setMetodos(List<Metodo> metodos) {
		this.metodos = metodos;
	}
 
	public String getClassType() {
		return classType;
	}
 
	public void setClassType(String classType) {
		this.classType = classType;
	}
 
}