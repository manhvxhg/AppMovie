package fsoft.training.movieapplication.listener;

import java.util.List;

import fsoft.training.movieapplication.domain.model.dto.moviedetail.CastDto;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.CrewDto;

/**
 * Created by mac on 10/26/17.
 */

public interface CastAndCrewPresenterListener {
    void resultCastCrewPresenter(List<CastDto> castDtos, List<CrewDto> crewDtos);
}
