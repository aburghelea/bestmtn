package com.endava.service;

import com.endava.exception.InvaliCallBackException;
import com.endava.exception.RandomServerException;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/13/14
 */
public interface ReturningService {

    public void computeResult(String query, String callback) throws InvaliCallBackException, RandomServerException;


}
