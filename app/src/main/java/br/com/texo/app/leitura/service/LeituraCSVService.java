package br.com.texo.app.leitura.service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import br.com.texo.app.leitura.Row;
import br.com.texo.app.leitura.exception.LeituraException;

@Service
public class LeituraCSVService implements LeituraService {

	private static final int LINHA_1_DA_PLANILHA = 1;
	private static final int ANO = 0;
	private static final int TITULO = 1;
	private static final int ESTUDIO = 2;
	private static final int PRODUTORA = 3;
	private static final int VENCEDOR = 4;

	private List<Row> linhas;

	public LeituraCSVService() {
		linhas = new LinkedList<>();
	}

	public List<Row> getLinhas(String caminhoDoCsv) {
		lerArquivo(caminhoDoCsv);
		return linhas;
	}

	private void lerArquivo(String caminhoDoCsv) {
		Reader reader;
		try {
			reader = Files.newBufferedReader(Paths.get(caminhoDoCsv));
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(LINHA_1_DA_PLANILHA).build();
			for (String[] linhaDoArquivo : csvReader.readAll()) {
				linhas.add(linha(juntaArrays(linhaDoArquivo).split(";", -1)));
			}
		} catch (IOException e) {
			throw new LeituraException("Houve um erro ao tentar ler o arquivo", e);
		}
	}

	private Row linha(String[] dados) {
		Row linha = new Row();
		linha.setYear(Integer.valueOf(dados[ANO]));
		linha.setTitle(dados[TITULO]);
		linha.setStudios(dados[ESTUDIO]);
		linha.setProducers(dados[PRODUTORA]);
		linha.setWinner(dados[VENCEDOR]);
		return linha;
	}

	private String juntaArrays(String[]... arrays) {
		StringBuilder juntos = new StringBuilder();
		for (String[] array : arrays) {
			for (String element : array) {
				juntos.append(element).append(",");
			}
		}
		juntos.setLength(juntos.length() - 1);
		return juntos.toString();
	}

}
