package it.univr.di.testero.model.core;
import javax.persistence.*;
import java.util.List;

/*
CREATE TABLE Test (
    data TIMESTAMP NOT NULL ,
    nome VARCHAR NOT NULL ,
    ordineCasuale BOOLEAN DEFAULT FALSE , -- le domande devono essere presentate in ordine casuale
    domandeConNumero BOOLEAN DEFAULT FALSE , -- le domande devono essere numerate
    PRIMARY KEY ( data , nome )
);
*/

@Entity
@Table(name="test", schema = "testero_core")
public class Test {

    @Id
    Long data;
    @Id
    String nome;
    Boolean ordineCasuale;
    Boolean domandeConNumero;


    @ManyToMany
    @JoinTable(
            name="in_test", schema = "testero_core",
            joinColumns=@JoinColumn(name="person_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="country_id", referencedColumnName="id"))
    private List<Domanda> domande;

    /*
    @OneToMany(mappedBy = "mainDoctor", orphanRemoval = true, cascade = CascadeType.ALL)
    Collection<Patient> patients;

    @OneToMany(mappedBy = "doctor", orphanRemoval = true, cascade = CascadeType.ALL)
    Collection<Prescription> prescriptions;
    */

    public Test() {}

    public Test(Long data, String nome, Boolean ordineCasuale, Boolean domandeConNumero ){
        this.data=data;
        this.nome=nome;
        this.ordineCasuale=ordineCasuale;
        this.domandeConNumero=domandeConNumero;
    }


}
