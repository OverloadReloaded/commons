package net.overload.commons.nms;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.Validate;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public final class ArrayWrapper<E> {
	private E[] _array;

	@SafeVarargs
	public ArrayWrapper(E... elements) {
		setArray(elements);
	}

	public E[] getArray() {
		return this._array;
	}

	public void setArray(E[] array) {
		Validate.notNull(array, "The array must not be null.");
		this._array = array;
	}

	public boolean equals(Object other) {
		return (other instanceof ArrayWrapper
				&& Arrays.equals((Object[]) this._array, (Object[]) ((ArrayWrapper) other)._array));
	}

	public int hashCode() {
		return Arrays.hashCode((Object[]) this._array);
	}

	public static <T> T[] toArray(Iterable<? extends T> list, Class<T> c) {
		int size = -1;
		if (list instanceof Collection) {
			Collection coll = (Collection) list;
			size = coll.size();
		}
		if (size < 0) {
			size = 0;
			for (T element : list)
				size++;
		}
		T[] result = (T[]) Array.newInstance(c, size);
		int i = 0;
		for (T element : list)
			result[i++] = element;
		return result;
	}
}
