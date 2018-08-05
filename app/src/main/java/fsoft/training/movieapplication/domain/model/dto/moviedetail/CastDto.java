
package fsoft.training.movieapplication.domain.model.dto.moviedetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CastDto {

    @SerializedName("cast_id")
    @Expose
    public Integer castId;
    @SerializedName("character")
    @Expose
    public String character;
    @SerializedName("credit_id")
    @Expose
    public String creditId;
    @SerializedName("gender")
    @Expose
    public Integer gender;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("order")
    @Expose
    public Integer order;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CastDto() {
    }

    /**
     * 
     * @param id
     * @param profilePath
     * @param order
     * @param castId
     * @param name
     * @param gender
     * @param creditId
     * @param character
     */
    public CastDto(Integer castId, String character, String creditId, Integer gender, Integer id, String name, Integer order, String profilePath) {
        super();
        this.castId = castId;
        this.character = character;
        this.creditId = creditId;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.order = order;
        this.profilePath = profilePath;
    }

}
