package com.nativapps.arpia.model.dto;

import java.util.List;

/**
 * The <code>ListResponse</code> class has the functionality of standardizing
 * the responses of a request where a list of elements has to be answered.
 *
 * @param <T> Type of list
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class ListResponse<T> {

    private long total;

    private List<T> items;

    public ListResponse() {
    }

    public ListResponse(long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

}
