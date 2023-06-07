package pojos;

import java.time.LocalDate;
import java.util.List;

public class Books {
    private String title;
    private Integer pages;
    private float rating;
    private String ISBN;
    private String publisher;
    private List<String> authors;
    private List<String> genres;

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Books(String title, Integer pages, float rating, String ISBN, String publisher, List<String> authors, List<String> genres) {
        this.title = title;
        this.pages = pages;
        this.rating = rating;
        this.ISBN = ISBN;
        this.publisher = publisher;
        this.authors = authors;
        this.genres = genres;
    }
}
