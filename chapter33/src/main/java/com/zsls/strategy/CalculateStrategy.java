/**
 * Company
 * Copyright (C) 2004-2020 All Rights Reserved.
 */
package com.zsls.strategy;

import com.zsls.model.InputModel;

/**
 * @author zsls
 * @version $Id CalculateStrategy.java, v 0.1 2020-06-10 16:43  Exp $$
 */
public interface CalculateStrategy {
    double doOperation(InputModel inputModel);
}
