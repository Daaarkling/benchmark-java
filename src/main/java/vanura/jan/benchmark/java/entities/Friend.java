package vanura.jan.benchmark.java.entities;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.dslplatform.json.CompiledJson;
import java.io.Serializable;

@CompiledJson
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Friend implements Serializable {
    
	private int id;
    private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}