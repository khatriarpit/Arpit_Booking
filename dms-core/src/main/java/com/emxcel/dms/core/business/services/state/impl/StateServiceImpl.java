package com.emxcel.dms.core.business.services.state.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.state.StateRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.state.StateService;
import com.emxcel.dms.core.model.state.State;

/**
 * @author emxcelsolution
 */
@Service("stateService")
public class StateServiceImpl extends DMSEntityServiceImpl<Long, State> implements StateService {

    private StateRepository stateRepository;

    @Inject
    public StateServiceImpl(StateRepository stateRepository) {
        super(stateRepository);
        this.stateRepository = stateRepository;
    }

    @Override
    public List<State> getstate(Long countryId) {
        return stateRepository.getstate(countryId);
    }
}