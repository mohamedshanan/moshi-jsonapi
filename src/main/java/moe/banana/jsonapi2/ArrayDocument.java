package moe.banana.jsonapi2;

import java.io.Serializable;
import java.util.*;

public class ArrayDocument<DATA extends ResourceIdentifier> extends Document<DATA> implements Serializable, List<DATA> {

    List<DATA> data = new ArrayList<>();

    public ArrayDocument() {
    }

    public ArrayDocument(Document<DATA> document) {
        super(document);
    }

    public boolean add(DATA element) {
        if (data.add(element)) {
            element.setContext(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof ResourceIdentifier && data.remove(o)) {
            ((ResourceIdentifier) o).setContext(null);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends DATA> c) {
        if (data.addAll(c)) {
            updateContext(this, c);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends DATA> c) {
        if (data.addAll(index, c)) {
            updateContext(this, c);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (data.removeAll(c)) {
            updateContext(null, c);
            return true;
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        updateContext(null, data);
        boolean result = data.retainAll(c);
        updateContext(this, data);
        return result;
    }

    @Override
    public void clear() {
        updateContext(null, data);
        data.clear();
    }

    public DATA get(int position) {
        return data.get(position);
    }

    @Override
    public DATA set(int index, DATA element) {
        DATA original = data.set(index, element);
        if (original != null) {
            original.setContext(null);
        }
        if (element != null) {
            element.setContext(this);
        }
        return original;
    }

    @Override
    public void add(int index, DATA element) {
        data.add(index, element);
        element.setContext(this);
    }

    public DATA remove(int position) {
        DATA element = data.remove(position);
        element.setContext(null);
        return element;
    }

    @Override
    public int indexOf(Object o) {
        return data.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return data.lastIndexOf(o);
    }

    @Override
    public ListIterator<DATA> listIterator() {
        return data.listIterator();
    }

    @Override
    public ListIterator<DATA> listIterator(int index) {
        return data.listIterator(index);
    }

    @Override
    public ArrayDocument<DATA> subList(int fromIndex, int toIndex) {
        ArrayDocument<DATA> copy = new ArrayDocument<>(this);
        copy.addAll(data.subList(fromIndex, toIndex));
        return copy;
    }

    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    public Iterator<DATA> iterator() {
        return data.iterator();
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return data.toArray(a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        return data.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    private static void updateContext(Document<?> context, Collection<?> resources) {
        for (Object i : resources) {
            if (i instanceof ResourceIdentifier) {
                ((ResourceIdentifier) i).setContext(context);
            }
        }
    }
}
