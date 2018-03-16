package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Evaluation;

import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface EvaluationDao extends DataAccessObject<Evaluation, Long> {

    /**
     * Return all the messenger evaluations from the given parameters.
     *
     * @param start       index of first element to get
     * @param size        list size
     * @param messengerId the messenger ID
     * @return messenger evaluation list
     */
    List<Evaluation> findAll(int start, int size, Long messengerId);

    /**
     * Return find all result count
     *
     * @param messengerId the messenger ID
     * @return count of result
     */
    long findAllCount(Long messengerId);
}
