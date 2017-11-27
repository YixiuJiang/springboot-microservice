package spaceshuttle.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Kid {

    @EmbeddedId
    private KidPK id;
    @Column
    private String name;

    public KidPK getId() {
        return id;
    }

    public void setId(KidPK id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

