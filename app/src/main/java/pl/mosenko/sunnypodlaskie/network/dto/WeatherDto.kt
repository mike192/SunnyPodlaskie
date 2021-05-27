package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherDto {
    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("main")
    @Expose
    private var main: String? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("icon")
    @Expose
    private var icon: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param id
     * @param icon
     * @param description
     * @param main
     */
    constructor(id: Int?, main: String?, description: String?, icon: String?) : super() {
        this.id = id
        this.main = main
        this.description = description
        this.icon = icon
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getMain(): String? {
        return main
    }

    fun setMain(main: String?) {
        this.main = main
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getIcon(): String? {
        return icon
    }

    fun setIcon(icon: String?) {
        this.icon = icon
    }

    override fun toString(): String {
        return "WeatherDto{" +
                "id=" + id +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}'
    }
}
