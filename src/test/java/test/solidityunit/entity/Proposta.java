package test.solidityunit.entity;

import java.math.BigInteger;

import org.web3j.tuples.generated.Tuple9;

public class Proposta {
	/**
	 * string titulo;
        string descricao;
        address criador;
        uint dataFinal;
        uint totalVotos;
        address[] votosFavor;
        address[] votosContra;
        uint status;
	 */
	
	private int index;
	private String titulo;
	private String descricao;
	private String criador;
	private long dataFinal;
	private long totalVotos;
	
	private long votosFavor;
	private long votosContra;
	
	private int status;
	
	public Proposta() {
		super();
	}
	
	public Proposta(Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> tuple) {
		super();
		this.index = tuple.getValue1().intValue();
		this.titulo = tuple.getValue2();
		this.descricao = tuple.getValue3();
		this.criador = tuple.getValue4();
		this.dataFinal = tuple.getValue5().longValue();
		this.totalVotos = tuple.getValue6().longValue();
		this.votosFavor = tuple.getValue7().longValue();
		this.votosContra = tuple.getValue8().longValue();
		this.status = tuple.getValue9().intValue();
	}

	@Override
	public String toString() {
		return "Proposta [index=" + index + ", titulo=" + titulo + ", descricao=" + descricao + ", criador=" + criador + ", dataFinal=" + dataFinal + ", totalVotos=" + totalVotos + ", votosFavor="
				+ votosFavor + ", votosContra=" + votosContra + ", status=" + status + "]";
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCriador() {
		return criador;
	}
	public void setCriador(String criador) {
		this.criador = criador;
	}
	public long getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(long dataFinal) {
		this.dataFinal = dataFinal;
	}
	public long getTotalVotos() {
		return totalVotos;
	}
	public void setTotalVotos(long totalVotos) {
		this.totalVotos = totalVotos;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public long getVotosFavor() {
		return votosFavor;
	}
	public void setVotosFavor(long votosFavor) {
		this.votosFavor = votosFavor;
	}
	public long getVotosContra() {
		return votosContra;
	}
	public void setVotosContra(long votosContra) {
		this.votosContra = votosContra;
	}
	

}
