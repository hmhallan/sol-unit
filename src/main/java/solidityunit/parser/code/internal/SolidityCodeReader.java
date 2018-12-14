package solidityunit.parser.code.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import solidityunit.parser.code.internal.entity.Metodo;

public class SolidityCodeReader {
	
	private String code;
	
	public SolidityCodeReader(String code) {
		this.code = code;
		// remove espacos duplicados entre palavras
		if (this.code != null) {
			this.code = this.code.replaceAll("\\s+", " ");
		}
	}
 
	//
	public List<Metodo> getMetodosSafe() {
		System.out.println(code);
		List<Metodo> metodos = new ArrayList<>();
		Pattern src = Pattern.compile("(function|\\s) +[\\w]+\\s+(\\w+) *\\(([^\\)]*)\\) +(public view) +[\\w]{");
		Matcher matcher = src.matcher(this.code);
		while (matcher.find()) {
			Metodo m = new Metodo();
			m.setNome(matcher.group(2).trim());
			metodos.add(m);
		}
		return metodos;
	}
 
}
