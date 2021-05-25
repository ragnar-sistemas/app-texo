package br.com.texo.app.util;

import java.util.Collection;
import java.util.LinkedList;

public class TexoLinkedList<E> extends LinkedList<E> {

	private static final long serialVersionUID = -8646792187901823387L;

	public TexoLinkedList() {
	}

	public TexoLinkedList(E e) {
		this.add(e);
	}

	@Override
	public boolean add(E e) {
		if (e == null) {
			return false;
		}
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c == null) {
			return false;
		}
		for (E e : c) {
			add(e);
		}
		return true;
	}

}
