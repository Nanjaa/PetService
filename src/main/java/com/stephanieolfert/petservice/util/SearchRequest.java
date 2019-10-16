package com.stephanieolfert.petservice.util;

import com.stephanieolfert.petservice.pet.OptionalFieldsPet;

public class SearchRequest {

    private OptionalFieldsPet search;

    public OptionalFieldsPet getSearch() {
        return search;
    }

    public void setSearch(OptionalFieldsPet search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "SearchRequest [search=" + search + "]";
    }

}
