package com.infoshare.DTO;

import java.time.LocalDate;

public class SearchCountDTO {

    Long count;
    LocalDate searchDate;

    public SearchCountDTO() {

    }

    public SearchCountDTO(Long count, LocalDate searchDate) {
        this.count = count;
        this.searchDate = searchDate;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public LocalDate getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(LocalDate searchDate) {
        this.searchDate = searchDate;
    }
}
