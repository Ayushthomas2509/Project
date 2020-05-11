package ayushproject.ayushecommerce.entities;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

@Entity
public class CategoryFieldValues implements Serializable {
    @EmbeddedId
    private CompositeKeyFieldValues id;
    @ElementCollection
    private List<String> possibleValues;

    public CategoryFieldValues(CompositeKeyFieldValues id, List<String> possibleValues) {
        this.id = id;
        this.possibleValues = possibleValues;
    }

    public CategoryFieldValues() {
    }

    public CompositeKeyFieldValues getId() {
        return id;
    }

    public void setId(CompositeKeyFieldValues id) {
        this.id = id;
    }

    public List<String> getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(List<String> possibleValues) {
        this.possibleValues = possibleValues;
    }
}
