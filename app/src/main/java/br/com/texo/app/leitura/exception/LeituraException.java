package br.com.texo.app.leitura.exception;

public class LeituraException extends RuntimeException {

	private static final long serialVersionUID = -1623884951568218350L;

	public LeituraException(String mensagem, Throwable throwable) {
		super(mensagem, throwable);
	}

}
