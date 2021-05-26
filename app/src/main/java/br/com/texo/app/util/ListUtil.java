package br.com.texo.app.util;

import java.util.HashSet;
import java.util.List;

public final class ListUtil {

	public static <T> TexoLinkedList<T> removeDuplicates(List<T> listData) {
		TexoLinkedList<T> listResult = new TexoLinkedList<T>();
		HashSet<T> setRetornoNaoOrdenado = new HashSet<T>(0);
		if (listData != null) {
			for (T data : listData) {
				if (setRetornoNaoOrdenado.add(data)) {
					listResult.add(data);
				}
			}
			return listResult;
		} else {
			return null;
		}
	}

}
