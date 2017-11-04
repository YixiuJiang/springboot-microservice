package spaceshuttle.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by seabookchen on 22/08/2017.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 30)
    private String firstName;
    @Column(name = "last_name", length = 30)
    private String lastName;
    @Transient
    private String fullName;

    private String linkedInProfileUrl;

    private String bio;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Course> courses;

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, String fullName, String linkedInProfileUrl, String bio, Set<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.linkedInProfileUrl = linkedInProfileUrl;
        this.bio = bio;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLinkedInProfileUrl() {
        return linkedInProfileUrl;
    }

    public void setLinkedInProfileUrl(String linkedInProfileUrl) {
        this.linkedInProfileUrl = linkedInProfileUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

}
