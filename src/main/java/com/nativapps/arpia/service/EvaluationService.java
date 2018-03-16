package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.EvaluationRequest;
import com.nativapps.arpia.model.dto.EvaluationResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
public interface EvaluationService extends Service {

    /**
     * Get all evaluations from the given parameters.
     * <p>
     * The response is paginated.
     *
     * @param messengerId the messenger ID
     * @param start       index of first element to get
     * @param size        list size
     * @return evaluations
     */
    ListResponse<EvaluationResponse> getAll(Long messengerId, int start, int size);

    /**
     * Get evaluation from the given ID.
     *
     * @param id the evaluation ID
     * @return evaluation
     */
    EvaluationResponse get(Long id);

    /**
     * Create a new evaluation to messenger.
     *
     * @param data     evaluation data
     * @param userInfo user information
     * @return evaluation created
     */
    EvaluationResponse add(EvaluationRequest data, UserInfo userInfo);

}
