package test.solidityunit;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import solidityunit.compiler.CompilerResult;
import solidityunit.compiler.SolidityCompiler;

@Ignore
public class TestSolidityCompiler {
	
	@Test
	public void tentar_compilar_um_contrato() throws IOException {
		
		final String dir = "src/main/resources";
        String path = System.getProperty("user.dir") + File.separator + dir;
		
		Path source = Paths.get(path);
        Files.walk(source).filter(Files::isRegularFile).forEach(System.out::println);
		
		
        Path configFilePath = Paths.get(path);   
        List<Path> files = 
        Files.walk(configFilePath)
	        .filter(s -> s.toString().endsWith(".sol"))
	        .collect(toList());
        
        List<String> includedFiles = new ArrayList<>();
        files.forEach( f -> includedFiles.add(f.toAbsolutePath().toString()));
		
        System.out.println(includedFiles);
        
		CompilerResult result = SolidityCompiler.getInstance().compileSrc(
                "src/main/resources",
                includedFiles,
                SolidityCompiler.Options.ABI,
                SolidityCompiler.Options.BIN,
                SolidityCompiler.Options.INTERFACE,
                SolidityCompiler.Options.METADATA
        );
		
		System.out.println(result.errors);
		
	}

}
