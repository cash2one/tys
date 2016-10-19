package com.tys.studentcard.detector.service;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

public class BasePaginationList<T> extends AbstractList<T> {

    private Long totalCount;

    private List<T> list;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public int size() {
        return list != null ? list.size() : 0;
    }

    @Override
    public T get(int index) {
        return (list != null) ? list.get(index) : null;
    }

    @Override
    public Iterator<T> iterator() {
        final Iterator<T> it = list.iterator();

        return new Iterator<T>() {

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public T next() {
                return it.next();
            }

            @Override
            public void remove() {
                it.remove();
            }
        };
    }
}
