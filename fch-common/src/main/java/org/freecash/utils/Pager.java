package org.freecash.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pager {
    private int pageNum;
    private int pageSize;
    private long total;

    public Pager(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
