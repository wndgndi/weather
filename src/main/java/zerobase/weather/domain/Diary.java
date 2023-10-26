package zerobase.weather.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Diary {

    @Id
    @GeneratedValue
    int id;
}
