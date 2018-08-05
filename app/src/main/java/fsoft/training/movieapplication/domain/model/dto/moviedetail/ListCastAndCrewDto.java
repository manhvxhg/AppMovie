
package fsoft.training.movieapplication.domain.model.dto.moviedetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListCastAndCrewDto {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("cast")
    @Expose
    public List<CastDto> castDto = null;
    @SerializedName("crew")
    @Expose
    public List<CrewDto> crewDto = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ListCastAndCrewDto() {
    }

    /**
     * 
     * @param id
     * @param castDto
     * @param crew
     */
    public ListCastAndCrewDto(Integer id, List<CastDto> castDto, List<CrewDto> crew) {
        super();
        this.id = id;
        this.castDto = castDto;
        this.crewDto = crew;
    }


}
