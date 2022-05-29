package com.contract_debt.repository;

public interface ExtraRepository {

    /**
     * Get next value of sequence in database
     *
     *
     * @param sequenceName
     * @return no number
     */
    Long getNextValueSeq(String sequenceName);

    /**
     * Refresh Sequence
     *
     * move sequence value to 1
     * @param sequenceName
     * @return
     */
    boolean refreshOrderCode(String sequenceName);

}
