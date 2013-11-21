package ca.ubc.ece.eece210.mp3;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Container class for all the albums and genres. Its main responsibility is to
 * save and restore the collection from a file.
 * 
 * @author Sathish Gopalakrishnan
 * 
 */
public final class Catalogue {
    ArrayList<Genre> myGenres;

    /**
     * Builds a new, empty catalogue.
     */
    public Catalogue() {
        myGenres = new ArrayList<>();
    }

    /**
     * Builds a new catalogue and restores its contents from the given file.
     * 
     * @param fileName
     *            the file from where to restore the library.
     */
    public Catalogue(String fileName) {
        XMLDecoder d;
        try {
            d = new XMLDecoder(new BufferedInputStream(new FileInputStream(
                    fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Object result = d.readObject();
        d.close();

        myGenres = ((Catalogue) result).myGenres;
    }

    public ArrayList<Genre> getMyGenres() {
        return myGenres;
    }

    public void setMyGenres(ArrayList<Genre> genres) {
        myGenres = genres;
    }

    /**
     * Saved the contents of the catalogue to the given file.
     * 
     * @param fileName
     *            the file where to save the library
     */
    public void saveCatalogueToFile(String fileName) {
        XMLEncoder e;
        try {
            e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(
                    fileName)));
        } catch (FileNotFoundException e1) {
            System.out.println("Error creating XMLEncoder for " + fileName);
            e1.printStackTrace();
            return;
        }
        e.writeObject(this);
        e.close();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((myGenres == null) ? 0 : myGenres.hashCode());
        return result;
    }

    /**
     * Two catalogues are equal iff each catalogue's myGenres are equal.
     * 
     * This tests for equality recursively, since ArrayList tests for equality
     * on each element, and Genre tests for equality by checking if its children
     * are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Catalogue other = (Catalogue) obj;
        if (myGenres == null) {
            if (other.myGenres != null)
                return false;
        } else if (!myGenres.equals(other.myGenres))
            return false;
        return true;
    }

}