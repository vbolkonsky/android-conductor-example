package com.magorasystems.conductor.example.model;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

/**
 * Developed 2016.
 *
 * @author Valentin S.Bolkonsky
 */
@ToString
public class CatalogEvent {

    private List<Integer> catalogIds;
    private int offset;
    private int limit;

    public CatalogEvent(List<Integer> catalogIds, int offset, int limit) {
        this.catalogIds = catalogIds;
        this.offset = offset;
        this.limit = limit;
    }

    public List<Integer> getCatalogIds() {
        if(catalogIds == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(catalogIds);
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
