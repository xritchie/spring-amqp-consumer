package spittr;

import spittr.Spitter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by shawnritchie on 28/04/15.
 */

public class Spittle implements Serializable{

    private Long id;

    private Spitter spitter;

    private String message;

    private Date postedTime;

    private Spittle() {}

    public Spittle(Long id, Spitter spitter, String message, Date postedTime) {
        this.id = id;
        this.spitter = spitter;
        this.message = message;
        this.postedTime = postedTime;
    }

    public Long getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    public Date getPostedTime() {
        return this.postedTime;
    }


    public Spitter getSpitter() {
        return this.spitter;
    }

    public void validateState()
    {

    }

    private static final long serialVersionUID = 7526471155622776147L;

    private void readObject(
            ObjectInputStream aInputStream
    ) throws ClassNotFoundException, IOException {
        //always perform the default de-serialization first
        aInputStream.defaultReadObject();

        //ensure that object state has not been corrupted or tampered with maliciously
        validateState();
    }

    private void writeObject(
            ObjectOutputStream aOutputStream
    ) throws IOException {
        //perform the default serialization for all non-transient, non-static fields
        aOutputStream.defaultWriteObject();
    }


}
