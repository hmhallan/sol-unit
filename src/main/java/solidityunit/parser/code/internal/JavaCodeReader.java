package solidityunit.parser.code.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.RunWith;

import solidityunit.parser.code.internal.entity.Atributo;
import solidityunit.parser.code.internal.entity.Classe;
import solidityunit.parser.code.internal.entity.Metodo;
import solidityunit.runner.SolidityUnitRunner;
 
/**
 * 
 * @author Hallan
 */
public class JavaCodeReader {
 
	private String code;
	
	public JavaCodeReader(String code) {
		this.code = code;
		// remove espacos duplicados entre palavras
		if (this.code != null) {
			this.code = this.code.replaceAll("\\s+", " ");
		}
	}
 
	public Classe getClasse() {
 
		Classe c = new Classe();
		c.setClassType(this.getClassType());
		c.setAtributos(this.getAttributes());
		c.setNome(this.getNomeClasse());
		c.setPacote(this.getNomePacote());
		if (c.getClassType().equalsIgnoreCase("interface")) {
			c.setMetodos(this.getMetodosInterface());
		}
		else {
			c.setMetodos(this.getMetodos());
		}
 
		return c;
	}
 
	public String validate(Classe c) {
		String errors = "";
		if (c.getPacote() == null) {
			errors += "//não foi encontrado pacote desta classe\n";
		}
		if (c.getNome() == null) {
			errors += "//não foi encontrado o nome desta classe\n";
		}
		return errors;
	}
 
	//
	public List<Metodo> getMetodos() {
		List<Metodo> metodos = new ArrayList();
		Pattern src = Pattern.compile("(public|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\(([^\\)]*)\\) *(\\{?|[^;])");
		Matcher matcher = src.matcher(this.code);
		while (matcher.find()) {
			String declaracao = matcher.group();
			Metodo m = new Metodo();
 
			int indexAbreParentesis = declaracao.indexOf("(");
			int indexFechaParentesis = declaracao.indexOf(")");
 
			// pega a parte antes do primeiro parentesis
			String[] vetParte1 = declaracao.substring(0, indexAbreParentesis).split(" ");
 
			// pega a parte entre os parentesis
			// String[] vetParte2 = declaracao.substring(indexAbreParentesis + 1, indexFechaParentesis).split(",");
 
			m.setModificadorAcesso(vetParte1[0]);
			m.setTipoRetorno(vetParte1[1]);
			// m.setNome(vetParte1[2]);
 
			m.setNome(matcher.group(2).trim());
 
			// HashMap<String,String> arg, Integer arg2
			String argumentos = matcher.group(3);
 
			// verificar se os argumentos sao generics (Map<?,?> etc)
			StringBuilder argumentosBuilder = new StringBuilder(argumentos);
			boolean foundGenerics = false;
			if (argumentos.contains("<") && argumentos.contains(",")) {
				// substitui as virgulas dentro do <> por @
				Pattern pattern = Pattern.compile("<[\\w\\s]+(,)[\\w\\s]+>");
				Matcher m2 = pattern.matcher(argumentos);
				while (m2.find()) {
					foundGenerics = true;
					argumentosBuilder = argumentosBuilder.replace(m2.start(1), m2.start(1) + 1, "@");
				}
				argumentos = argumentosBuilder.toString();
			}
 
			String[] vetArgumentos = argumentos.split(",");
			if (foundGenerics) {
				// depois do split trocar os "@" por virgula novamente
				for (int i = 0; i < vetArgumentos.length; i++) {
					// vetArgumentos[i] = vetArgumentos[i].replace("@", ",");
				}
			}
 
			if (vetArgumentos != null && vetArgumentos.length > 0) {
				for (String arg : vetArgumentos) {
					if (arg.length() > 0) {
 
						// split de foundGenerics
						if (foundGenerics) {
							// retira possiveis espacos entre a virgula
							arg = arg.replaceAll("@ ", ",").replaceAll(" @ ", ",").replaceAll("@", ",").replaceAll(" @", ",");
						}
						String[] vetArg = arg.trim().split(" ");
						Atributo attr = new Atributo();
						attr.setNome(vetArg[1]);
						attr.setTipo(vetArg[0]);
						if (attr.getTipo().contains("List") || attr.getTipo().contains("Map") || attr.getTipo().contains("Set")) {
							Pattern srcList = Pattern.compile("<(.*?)>");
							Matcher matcherList = srcList.matcher(attr.getTipo());
							if (matcherList.find()) {
								attr.setTipo(attr.getTipo().substring(0, matcherList.start()));
								attr.setTipoLista(matcherList.group().replace("<", "").replace(">", ""));
							}
						}
						m.addArgumento(attr);
					}
				}
			}
 
			metodos.add(m);
		}
		return metodos;
	}
 
	public List<Metodo> getMetodosInterface() {
		List<Metodo> metodos = new ArrayList();
		Pattern src = Pattern.compile("[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\(([^\\)]*)\\) *(\\{?|[^;])");
		Matcher matcher = src.matcher(this.code);
		while (matcher.find()) {
			String declaracao = matcher.group();
			Metodo m = new Metodo();
 
			int indexAbreParentesis = declaracao.indexOf("(");
			int indexFechaParentesis = declaracao.indexOf(")");
 
			// pega a parte antes do primeiro parentesis
			String[] vetParte1 = declaracao.substring(0, indexAbreParentesis).split(" ");
 
			// pega a parte entre os parentesis
			// String[] vetParte2 = declaracao.substring(indexAbreParentesis + 1, indexFechaParentesis).split(",");
 
			m.setTipoRetorno(vetParte1[0]);
 
			m.setNome(matcher.group(1).trim());
 
			// HashMap<String,String> arg, Integer arg2
			String argumentos = matcher.group(2);
 
			// verificar se os argumentos sao generics (Map<?,?> etc)
			StringBuilder argumentosBuilder = new StringBuilder(argumentos);
			boolean foundGenerics = false;
			if (argumentos.contains("<") && argumentos.contains(",")) {
				// substitui as virgulas dentro do <> por @
				Pattern pattern = Pattern.compile("<[\\w\\s]+(,)[\\w\\s]+>");
				Matcher m2 = pattern.matcher(argumentos);
				while (m2.find()) {
					foundGenerics = true;
					argumentosBuilder = argumentosBuilder.replace(m2.start(1), m2.start(1) + 1, "@");
				}
				argumentos = argumentosBuilder.toString();
			}
 
			String[] vetArgumentos = argumentos.split(",");
			if (foundGenerics) {
				// depois do split trocar os "@" por virgula novamente
				for (int i = 0; i < vetArgumentos.length; i++) {
					// vetArgumentos[i] = vetArgumentos[i].replace("@", ",");
				}
			}
 
			if (vetArgumentos != null && vetArgumentos.length > 0) {
				for (String arg : vetArgumentos) {
					if (arg.length() > 0) {
 
						// split de foundGenerics
						if (foundGenerics) {
							// retira possiveis espacos entre a virgula
							arg = arg.replaceAll("@ ", ",").replaceAll(" @ ", ",").replaceAll("@", ",").replaceAll(" @", ",");
						}
						String[] vetArg = arg.trim().split(" ");
						Atributo attr = new Atributo();
						attr.setNome(vetArg[1]);
						attr.setTipo(vetArg[0]);
						if (attr.getTipo().contains("List") || attr.getTipo().contains("Map") || attr.getTipo().contains("Set")) {
							Pattern srcList = Pattern.compile("<(.*?)>");
							Matcher matcherList = srcList.matcher(attr.getTipo());
							if (matcherList.find()) {
								attr.setTipo(attr.getTipo().substring(0, matcherList.start()));
								attr.setTipoLista(matcherList.group().replace("<", "").replace(">", ""));
							}
						}
						m.addArgumento(attr);
					}
				}
			}
 
			metodos.add(m);
		}
		return metodos;
	}
 
	public List<Atributo> getAttributes() {
		List<Atributo> atributos = new ArrayList();
		Pattern src = Pattern.compile("(private|protected)\\s(.*?)\\s(.*?)(;|=)");
		Matcher matcher = src.matcher(this.code);
		while (matcher.find()) {
			String declaracao = matcher.group();
			if (declaracao.contains("static") || declaracao.contains(" final ")) {
				continue;
			}
 
			// generics tipo Map<String, String>
			if (declaracao.contains("<") && declaracao.contains(",")) {
				// retira o modificador de acesso
				String temp = declaracao.substring(declaracao.indexOf(" ") + 1, declaracao.length() - 1);
				Atributo attr = new Atributo();
				attr.setTipo(temp.substring(0, temp.indexOf("<")));
				attr.setTipoLista(temp.substring(temp.indexOf("<") + 1, temp.indexOf(">")).replace(" ", ""));
				attr.setNome(temp.substring(temp.lastIndexOf(" "), temp.length()).trim());
				atributos.add(attr);
			}
			else {
				Atributo attr = new Atributo();
				attr.setTipo(matcher.group(2).trim());
				attr.setNome(matcher.group(3).trim());
				if (attr.getTipo().contains("List")) {
					Pattern srcList = Pattern.compile("<(.*?)>");
					Matcher matcherList = srcList.matcher(attr.getTipo());
					if (matcherList.find()) {
						attr.setTipo("List");
						attr.setTipoLista(matcherList.group().trim().replace("<", "").replace(">", ""));
					}
				}
				atributos.add(attr);
			}
		}
		return atributos;
	}
 
	public String getNomeClasse() {
 
		// TODO tratar abstract
		Pattern src = Pattern.compile("public (class|interface|enum)\\s(.*?)\\{");
		Matcher matcher = src.matcher(this.code);
		while (matcher.find()) {
			String nome = matcher.group(2).trim();
			if (nome.contains("implements")) {
				int index = nome.indexOf("implements");
				nome = nome.substring(0, index);
			}
			if (nome.contains("extends")) {
				int index = nome.indexOf("extends");
				nome = nome.substring(0, index);
			}
			// nome da classe ou interface
			return nome.trim();
		}
		return null;
	}
 
	public String getClassType() {
 
		// TODO tratar abstract
		Pattern src = Pattern.compile("public (class|interface|enum)\\s(.*?)\\{");
		Matcher matcher = src.matcher(this.code);
		while (matcher.find()) {
			return matcher.group(1).trim();
		}
		return null;
	}
 
	public String getNomePacote() {
		Pattern src = Pattern.compile("package\\s(.*?);");
		Matcher matcher = src.matcher(this.code);
		while (matcher.find()) {
			String nome = matcher.group();
			nome = nome.replace("package", "").replace(";", "");
			return nome.trim();
		}
		return null;
	}
 
	public boolean isSolidityUnitRunner() {
		Pattern src = Pattern.compile("@RunWith\\(SolidityUnitRunner.class\\)");
		Matcher matcher = src.matcher(this.code);
		while (matcher.find()) {
			return true;
		}
		return false;
	}
 
}
